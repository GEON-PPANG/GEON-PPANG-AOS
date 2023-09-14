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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityDetailBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.common.WebViewActivity
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import com.sopt.geonppang.presentation.report.ReportActivity
import com.sopt.geonppang.presentation.reviewWriting.ReviewWritingActivity
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.ChipFactory
import com.sopt.geonppang.util.CustomSnackbar
import com.sopt.geonppang.util.binding.BindingActivity
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

    private val String.toChip: Chip
        get() = ChipFactory.create(layoutInflater).also {
            it.text = this
        }

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
        viewModel.fetchDetailBakeryInfo(bakeryId)
        viewModel.fetchDetailReview(bakeryId)

        detailBakeryInfoAdapter = DetailBakeryInfoAdapter(::moveToWebPage)
        detailMenuAdapter = DetailMenuAdapter()
        detailReviewGraphAdapter = DetailReviewGraphAdapter()
        detailReviewAdapter = DetailReviewAdapter(::initChip, ::moveToReport)
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
        binding.ivDetailArrowLeft.setOnClickListener {
            val previousActivityName = intent.getStringExtra(ACTIVITY_NAME)

            when (previousActivityName) {
                MAIN -> {
                    moveToMain()
                }

                else -> {
                    finish()
                }
            }
        }

        binding.btnDetailCrateReview.setOnClickListener {
            moveToReviewWriting(bakeryId, viewModel.getBakeryInfo())
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

        binding.ivDetailBottomAppBarBookmark.setOnClickListener {
            viewModel.bookMarkInfo.value?.isBookMarked?.let { isBookMarked ->
                AmplitudeUtils.trackEvent(CLICK_MY_STORE)
                viewModel.doBookMark(bakeryId, !isBookMarked)

                if (!isBookMarked) {
                    CustomSnackbar.makeSnackbar(
                        binding.root,
                        getString(R.string.snackbar_save)
                    )
                }
            }
        }
    }

    private fun collectData() {
        viewModel.bakeryInfo.flowWithLifecycle(lifecycle).onEach {
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

        viewModel.bookMarkInfo.flowWithLifecycle(lifecycle).onEach {
            viewModel.fetchDetailBakeryInfo(bakeryId)
        }.launchIn(lifecycleScope)
    }

    private fun initChip(chipGroup: ChipGroup, position: Int) {
        viewModel.reviewData.value?.detailReviewList?.get(position)?.recommendKeywordList?.let { recommendKeywordList ->
            for (recommendKeyword in recommendKeywordList) {
                chipGroup.addView(
                    recommendKeyword.recommendKeywordName.toChip
                )
            }
        }
    }

    private fun moveToTop(recyclerView: RecyclerView) {
        recyclerView.smoothScrollToPosition(FIRST_POSITION)
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
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

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val BAKERY_INFO = "bakeryInfo"
        const val ACTIVITY_NAME = "activityName"
        const val MAIN = "mainActivity"
        const val REVIEW_ID = "reviewId"
        const val FIRST_POSITION = 0
        const val VIEW_DETAIL_PAGE_AT = "view_detailpage_at"
        const val SOURCE = "source"
        const val CLICK_NAVIGATION = "click_navigation"
        const val CLICK_MY_STORE = "click_mystore"
    }
}
