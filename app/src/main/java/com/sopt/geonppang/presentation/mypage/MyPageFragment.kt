package com.sopt.geonppang.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.BuildConfig
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentMyPageBinding
import com.sopt.geonppang.presentation.common.WebViewActivity
import com.sopt.geonppang.presentation.filterSetting.FilterSettingActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingFragment
import com.sopt.geonppang.util.setInvisibility
import com.sopt.geonppang.util.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()
    private lateinit var logoutDialog: LogoutDialog
    private lateinit var withdrawDialog: WithdrawDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        viewModel.fetchMypageInfo()
        binding.includeMyPageSpeechBubble.ivSpeechBubble.setBackgroundResource(R.drawable.background_left_speech_bubble)
        binding.tvMyPageAppVersion.text = getString(R.string.tv_my_page_app_version, APP_VERSION)
    }

    private fun addListeners() {
        binding.layoutMyPageBookmark.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_MY_STORE)
            moveToStoreBakeryList()
        }

        binding.layoutMyPageReview.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_MY_REVIEW)
            moveToMyReview()
        }

        binding.ivMyPageProfileRightArrow.setOnClickListener {
            AmplitudeUtils.trackEvent(START_FILTER_MY)
            moveToFilter()
        }

        binding.tvMyPageLogout.setOnClickListener {
            showLogoutDialog()
        }

        binding.tvMyPageWithdraw.setOnClickListener {
            showWithdrawDialog()
        }

        binding.includeMyPageSpeechBubble.ivSpeechBubbleClose.setOnClickListener {
            binding.includeMyPageSpeechBubble.root.setInvisibility(false)
        }

        binding.tvMyPageTermsOfUse.setOnClickListener {
            moveToWebPage(TERMS_OF_USE)
        }

        binding.tvMyPageInquiry.setOnClickListener {
            moveToWebPage(INQUIRY)
        }
    }

    private fun collectData() {
        viewModel.mypageInfoState.flowWithLifecycle(lifecycle).onEach {
            binding.chipMyPageProfilePurpose.text =
                this.context?.getString(viewModel.toMainPurposeTitleRes()) ?: ""
        }.launchIn(lifecycleScope)

        viewModel.isFilterSelected.flowWithLifecycle(lifecycle).onEach { isFilterSelected ->
            binding.includeMyPageSpeechBubble.root.setVisibility(!isFilterSelected)
            binding.chipMyPageProfilePurpose.setInvisibility(isFilterSelected)
            binding.chipGroupMyPageProfileBread.setVisibility(isFilterSelected)
        }.launchIn(lifecycleScope)

        binding.tvMyPageAppVersion.text = getString(R.string.tv_my_page_app_version, APP_VERSION)
    }

    private fun moveToStoreBakeryList() {
        startActivity(Intent(requireContext(), MyBookMarksActivity::class.java))
    }

    private fun moveToMyReview() {
        startActivity(Intent(requireContext(), MyReviewActivity::class.java))
    }

    private fun moveToFilter() {
        val intent = Intent(requireContext(), FilterSettingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.putExtra(FILTER_INFO, FilterInfoType.MYPAGE.name)
        startActivity(intent)
    }

    private fun moveToWebPage(link: String) {
        Intent(requireContext(), WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.WEB_VIEW_LINK, link)
        }.also { startActivity(it) }
    }

    private fun showLogoutDialog() {
        logoutDialog = LogoutDialog()
        logoutDialog.show(childFragmentManager, DIALOG)
    }

    private fun showWithdrawDialog() {
        withdrawDialog = WithdrawDialog()
        withdrawDialog.show(childFragmentManager, DIALOG)
    }

    companion object {
        const val FILTER_INFO = "filterInfo"
        const val APP_VERSION = BuildConfig.VERSION_NAME
        const val DIALOG = "dialog"
        const val TERMS_OF_USE = "https://sungah.notion.site/60361cdfe76a4ff0862eaaf0bdf72ab1?pvs=4"
        const val INQUIRY = "https://sungah.notion.site/14fce5b6dffc4eee9eccad2d2e7c79ef?pvs=4"
        const val CLICK_MY_STORE = "click_mystore"
        const val CLICK_MY_REVIEW = "click_myreview"
        const val START_FILTER_MY = "start_filter_mypage"
    }
}
