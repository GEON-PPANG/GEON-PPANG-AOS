package com.sopt.geonppang.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentMyPageBinding
import com.sopt.geonppang.presentation.filter.FilterActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
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
    }

    private fun moveToStoreBakeryList() {
        startActivity(Intent(requireContext(), MyStoreListActivity::class.java))
    }

    private fun moveToMyReview() {
        startActivity(Intent(requireContext(), MyReviewActivity::class.java))
    }

    private fun moveToFilter() {
        val intent = Intent(requireContext(), FilterActivity::class.java)
        intent.putExtra("filterInfo", FilterInfoType.MYPAGE.activityName)
        startActivity(intent)
    }
}
