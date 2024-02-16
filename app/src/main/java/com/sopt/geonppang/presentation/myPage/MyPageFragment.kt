package com.sopt.geonppang.presentation.myPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.BuildConfig
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.databinding.FragmentMyPageBinding
import com.sopt.geonppang.presentation.common.LoginNeededDialog
import com.sopt.geonppang.presentation.common.WebViewActivity
import com.sopt.geonppang.presentation.filterSetting.FilterSettingActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.presentation.type.LoginNeededType
import com.sopt.geonppang.presentation.type.UserRoleType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingFragment
import com.sopt.geonppang.util.extension.setOnSingleClickListener
import com.sopt.geonppang.util.setInvisibility
import com.sopt.geonppang.util.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()
    private lateinit var gpDataSource: GPDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        if (!getUserRole())
            viewModel.fetchProfileInfo()
        binding.includeMyPageSpeechBubble.ivSpeechBubble.setBackgroundResource(R.drawable.background_left_speech_bubble)
        binding.tvMyPageAppVersion.text = getString(R.string.tv_my_page_app_version, APP_VERSION)
    }

    // 이렇게 함수를 만들어서 매번 호출하는게 좋은 것인지
    // BakeryListFragment 처럼 버튼을 누를 때마다 해당 뷰의 context를 전달하는게 맞는지 고민
    private fun getUserRole(): Boolean {
        gpDataSource = GPDataSource(requireContext())
        return gpDataSource.userRoleType == UserRoleType.NONE_MEMBER.name
    }

    private fun addListeners() {
        binding.layoutMyPageBookmark.setOnSingleClickListener {
            AmplitudeUtils.trackEvent(CLICK_MY_STORE)
            if (getUserRole())
                showLoginNeedDialog()
            else
                moveToStoreBakeryList()
        }

        binding.layoutMyPageReview.setOnSingleClickListener {
            AmplitudeUtils.trackEvent(CLICK_MY_REVIEW)
            if (getUserRole())
                showLoginNeedDialog()
            else moveToMyReview()
        }

        binding.ivMyPageProfileRightArrow.setOnClickListener {
            AmplitudeUtils.trackEvent(START_FILTER_MY)
            if (getUserRole())
                showLoginNeedDialog()
            else moveToFilter()
        }

        binding.tvMyPageLogout.setOnClickListener {
            if (getUserRole())
                showLoginNeedDialog()
            else showLogoutDialog()
        }

        binding.tvMyPageWithdraw.setOnClickListener {
            if (getUserRole())
                showLoginNeedDialog()
            else showWithdrawDialog()
        }

        binding.includeMyPageSpeechBubble.ivSpeechBubbleClose.setOnClickListener {
            binding.includeMyPageSpeechBubble.root.setInvisibility(false)
        }

        binding.tvMyPageTermsOfUse.setOnClickListener {
            if (getUserRole())
                showLoginNeedDialog()
            else moveToWebPage(TERMS_OF_USE)
        }

        binding.tvMyPageInquiry.setOnClickListener {
            if (getUserRole())
                showLoginNeedDialog()
            else moveToWebBrowser(INQUIRY)
        }
    }

    private fun collectData() {
        viewModel.profileInfo.flowWithLifecycle(lifecycle).onEach {
            binding.chipMyPageProfilePurpose.text =
                viewModel.setMainPurposeTitle()?.let { it1 -> this.context?.getString(it1) } ?: ""
        }.launchIn(lifecycleScope)

        viewModel.isFilterSelected.flowWithLifecycle(lifecycle).onEach { isFilterSelected ->
            binding.includeMyPageSpeechBubble.root.setVisibility(!isFilterSelected)
            binding.chipMyPageProfilePurpose.setInvisibility(isFilterSelected)
            binding.chipGroupMyPageProfileBread.setInvisibility(isFilterSelected)
        }.launchIn(lifecycleScope)
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
        intent.putExtra(FILTER_INFO, FilterInfoType.MY_PAGE.name)
        startActivity(intent)
    }

    private fun moveToWebPage(link: String) {
        Intent(requireContext(), WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.WEB_VIEW_LINK, link)
        }.also { startActivity(it) }
    }

    private fun moveToWebBrowser(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        LogoutDialog().show(childFragmentManager, DIALOG)
    }

    private fun showWithdrawDialog() {
        WithdrawDialog().show(childFragmentManager, DIALOG)
    }

    private fun showLoginNeedDialog() {
        LoginNeededDialog(LoginNeededType.LOGIN_NEEDED_MY_PAGE).show(
            parentFragmentManager,
            "myPageLoginNeedDialog"
        )
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
