package com.sopt.geonppang.presentation.detail

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityDetailBinding
import com.sopt.geonppang.presentation.common.LoginNeededDialog
import com.sopt.geonppang.presentation.common.WebViewActivity
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import com.sopt.geonppang.presentation.report.ReportActivity
import com.sopt.geonppang.presentation.reviewWriting.ReviewWritingActivity
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.presentation.type.LoginNeededType
import com.sopt.geonppang.presentation.type.UserRoleType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.CustomSnackbar
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.breadTypeListToChips
import com.sopt.geonppang.util.extension.setOnSingleClickListener
import com.sopt.geonppang.util.extension.toBreadTypePointM1Chip
import com.sopt.geonppang.util.extension.toReviewRecommendedKeyWordChip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel by viewModels<DetailViewModel>()

    private lateinit var detailBakeryInfoAdapter: DetailBakeryInfoAdapter
    private lateinit var detailMenuAdapter: DetailMenuAdapter
    private lateinit var detailReviewGraphAdapter: DetailReviewGraphAdapter
    private lateinit var detailReviewAdapter: DetailReviewAdapter
    private lateinit var detailNoReviewAdapter: DetailNoReviewAdapter
    private lateinit var concatAdapter: ConcatAdapter
    private var bakeryId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        bakeryId = intent.getIntExtra(BAKERY_ID, -1)

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        viewModel.fetchDetailReview(bakeryId)

        detailBakeryInfoAdapter = DetailBakeryInfoAdapter(::moveToWebPage, ::initBreadTypeChips)
        detailMenuAdapter = DetailMenuAdapter()
        detailReviewGraphAdapter = DetailReviewGraphAdapter()
        detailReviewAdapter = DetailReviewAdapter(
            ::initRecommendKeyWordChip,
            ::moveToReport,
            ::showLoginNeedDialogReportReview
        )
        detailNoReviewAdapter = DetailNoReviewAdapter()

        concatAdapter = ConcatAdapter(
            detailBakeryInfoAdapter,
            detailMenuAdapter,
            detailReviewGraphAdapter,
            detailReviewAdapter
        )
        binding.rvDetail.adapter = concatAdapter
    }

    private fun addListeners() {
        val getUserRole = viewModel.userRoleType.value == UserRoleType.NONE_MEMBER.name
        binding.ivDetailArrowLeft.setOnClickListener {
            finish()
        }

        binding.btnDetailCrateReview.setOnClickListener {
            AmplitudeUtils.trackEvent(START_REVIEW_WRITING)
            if (getUserRole) {
                showLoginNeedDialogWriteReview()
            } else
                moveToReviewWriting(bakeryId, viewModel.getBakeryInfoForReview())
        }

        binding.fabDetail.setOnClickListener {
            moveToTop(binding.rvDetail)
        }

        binding.ivDetailMap.setOnClickListener {
            viewModel.bakeryInfo.value?.let { bakeryInfo ->
                AmplitudeUtils.trackEvent(CLICK_NAVIGATION)
                moveToWebBrowser(bakeryInfo.mapUrl)
            }
        }

        binding.ivDetailBottomAppBarBookmark.setOnSingleClickListener {
            if (getUserRole) {
                showLoginNeedDialogBookmark()
            } else {
                viewModel.bakeryInfo.value?.isBooked?.let { isBookMarked ->
                    viewModel.doBookMark(bakeryId, !isBookMarked)
                }
            }
        }
    }

    private fun collectData() {
        viewModel.bakeryInfo.flowWithLifecycle(lifecycle).onEach { it ->
            it?.let { bakeryInfo ->
                detailBakeryInfoAdapter.setBakeryInfo(bakeryInfo)
                detailMenuAdapter.submitList(bakeryInfo.menuList)
            }
        }.launchIn(lifecycleScope)

        viewModel.reviewData.flowWithLifecycle(lifecycle).onEach {
            it?.let { reviewData ->
                detailReviewGraphAdapter.setReviewData(reviewData)
                detailReviewAdapter.submitList(reviewData.detailReviewList)

                if (reviewData.totalReviewCount == 0) {
                    concatAdapter = ConcatAdapter(
                        detailBakeryInfoAdapter,
                        detailMenuAdapter,
                        detailReviewGraphAdapter,
                        detailNoReviewAdapter
                    )
                }

                binding.rvDetail.adapter = concatAdapter
            }
        }.launchIn(lifecycleScope)

        viewModel.bookMarkState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    AmplitudeUtils.trackEvent(CLICK_MY_STORE)
                    viewModel.fetchDetailBakeryInfo(bakeryId)

                    if (uiState.data.isBookMarked) {
                        CustomSnackbar.makeSnackbar(
                            binding.root,
                            getString(R.string.snackbar_save)
                        )
                    } else {
                        CustomSnackbar.makeSnackbar(
                            binding.root,
                            getString(R.string.snackbar_save_cancel)
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun initRecommendKeyWordChip(chipGroup: ChipGroup, position: Int) {
        viewModel.reviewData.value?.detailReviewList?.get(position)?.recommendKeywordList?.let { recommendKeywordList ->
            for (recommendKeyword in recommendKeywordList) {
                chipGroup.addView(
                    recommendKeyword.recommendKeywordName.toReviewRecommendedKeyWordChip(
                        layoutInflater
                    )
                )
            }
        }
    }

    private fun initBreadTypeChips(chipGroup: ChipGroup, breadFilterList: List<BreadFilterType>) {
        if (breadFilterList.isNotEmpty()) {
            chipGroup.breadTypeListToChips(
                breadTypeList = breadFilterList,
                toChip = {
                    this.toBreadTypePointM1Chip(layoutInflater)
                }
            )
        }
    }

    private fun moveToTop(recyclerView: RecyclerView) {
        recyclerView.smoothScrollToPosition(FIRST_POSITION)
    }

    private fun moveToWebPage(link: String) {
        Intent(this, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.WEB_VIEW_LINK, link)
        }.also { startActivity(it) }
    }

    private fun moveToWebBrowser(link: String) {
        val intent = Intent(ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    private fun moveToReport(id: Int) {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(REVIEW_ID, id)
        startActivity(intent)
    }

    private fun moveToReviewWriting(bakeryId: Int, bakeryInfo: BakeryReviewWritingInfo) {
        val intent = Intent(this, ReviewWritingActivity::class.java)
        intent.putExtra(BAKERY_ID, bakeryId)
        intent.putExtra(BAKERY_INFO, bakeryInfo)
        startActivity(intent)
        finish()
    }

    private fun showLoginNeedDialogBookmark() {
        LoginNeededDialog(LoginNeededType.LOGIN_NEEDED_BOOKMARK).show(
            supportFragmentManager, LOGIN_NEEDED_BOOKMARK
        )
    }

    private fun showLoginNeedDialogWriteReview() {
        LoginNeededDialog(LoginNeededType.LOGIN_NEEDED_WRITE_REVIEW).show(
            supportFragmentManager, LOGIN_NEEDED_WRITE_REVIEW
        )
    }

    private fun showLoginNeedDialogReportReview() {
        LoginNeededDialog(LoginNeededType.LOGIN_NEEDED_REPORT_REVIEW).show(
            supportFragmentManager, LOGIN_NEEDED_REPORT_REVEIW
        )
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val BAKERY_INFO = "bakeryInfo"
        const val REVIEW_ID = "reviewId"
        const val FIRST_POSITION = 0
        const val VIEW_DETAIL_PAGE_AT = "view_detailpage_at"
        const val SOURCE = "source"
        const val CLICK_NAVIGATION = "click_navigation"
        const val CLICK_MY_STORE = "click_mystore"
        const val START_REVIEW_WRITING = "start_reviewwriting"
        const val LOGIN_NEEDED_BOOKMARK = "loginNeededBookmark"
        const val LOGIN_NEEDED_WRITE_REVIEW = "loginNeededWriteReview"
        const val LOGIN_NEEDED_REPORT_REVEIW = "loginNeededReportReview"
    }
}
