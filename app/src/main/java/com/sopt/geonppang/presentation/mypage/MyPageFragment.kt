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
import com.sopt.geonppang.presentation.filter.FilterActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
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
    private lateinit var deletionDialog: DeletionDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        viewModel.fetchMypageInfo()
        binding.includeMyPageSpeechBubble.ivSpeechBubble.setBackgroundResource(R.drawable.background_left_speech_bubble)
    }

    private fun addListeners() {
        binding.layoutMyPageBookmark.setOnClickListener {
            moveToStoreBakeryList()
        }

        binding.layoutMyPageReview.setOnClickListener {
            moveToMyReview()
        }

        binding.ivMyPageProfileRightArrow.setOnClickListener {
            moveToFilter()
        }

        binding.tvMyPageLogout.setOnClickListener {
            showLogoutDialog()
        }

        binding.tvMyPageDeletion.setOnClickListener {
            showDeletionDialog()
        }

        binding.includeMyPageSpeechBubble.ivSpeechBubbleClose.setOnClickListener {
            binding.includeMyPageSpeechBubble.root.setInvisibility(false)
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
        val intent = Intent(requireContext(), FilterActivity::class.java)
        intent.putExtra(FILTER_INFO, FilterInfoType.MYPAGE.activityName)
        intent.putExtra(MAX_PAGE, FilterInfoType.MYPAGE.maxPage)
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        logoutDialog = LogoutDialog(requireContext())
        logoutDialog.show()
    }

    private fun showDeletionDialog() {
        deletionDialog = DeletionDialog(requireContext())
        deletionDialog.show()
    }

    companion object {
        const val FILTER_INFO = "filterInfo"
        const val MAX_PAGE = "maxPage"
        const val APP_VERSION = BuildConfig.VERSION_NAME
    }
}
