package com.sopt.geonppang.presentation.bakeryList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentBakeryListBinding
import com.sopt.geonppang.presentation.search.BakeryAdapter
import com.sopt.geonppang.presentation.search.SearchActivity
import com.sopt.geonppang.presentation.search.SearchViewModel
import com.sopt.geonppang.presentation.type.BakerySortType
import com.sopt.geonppang.util.binding.BindingFragment

class BakeryListFragment :
    BindingFragment<FragmentBakeryListBinding>(R.layout.fragment_bakery_list),
    BakerySortTypeListener {
    // TODO 서버 붙일때 수정
    private val searchViewModel: SearchViewModel by viewModels()
    private val viewModel: BakeryListViewModel by viewModels()

    lateinit var bakeryAdapter: BakeryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModle = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        bakeryAdapter = BakeryAdapter()
        binding.rvBakeryList.adapter = bakeryAdapter
//        bakeryAdapter.submitList(searchViewModel.mockBakeryList)
    }

    private fun addListeners() {
        binding.chipBreadListFilter.setOnClickListener {
            showBakeryListSortDialog()
        }

        binding.ivSearch.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
    }

    private fun showBakeryListSortDialog() {
        val bakeryListSortBottomSheetDialog = BakeryListSortBottomSheetDialog()

        // BakeryListSortDialog로 현재 정렬 정보 전달
        bakeryListSortBottomSheetDialog.arguments = Bundle().apply {
            putString("bakerySortType", viewModel.bakerySort.value?.sortType)
        }
        bakeryListSortBottomSheetDialog.setDataListener(this)
        bakeryListSortBottomSheetDialog.show(parentFragmentManager, "bakeryListSortDialog")
    }

    // BakeryListDialog에서 부터 받아온 데이터를 처리
    override fun onBakerySortTypeSelected(bakerySortType: BakerySortType) {
        viewModel.setBakerySortType(bakerySortType)
        // TODO 건빵집 리스트 조회 서버 붙이기
    }
}
