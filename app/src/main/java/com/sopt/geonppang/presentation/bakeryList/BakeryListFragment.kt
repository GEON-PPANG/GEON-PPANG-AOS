package com.sopt.geonppang.presentation.bakeryList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentBakeryListBinding
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.presentation.search.SearchActivity
import com.sopt.geonppang.presentation.type.BakerySortType
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BakeryListFragment :
    BindingFragment<FragmentBakeryListBinding>(R.layout.fragment_bakery_list),
    BakerySortTypeListener {
    private val viewModel: BakeryListViewModel by viewModels()

    lateinit var bakeryAdapter: BakeryListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModle = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        bakeryAdapter = BakeryListAdapter(::moveToDetail)
        binding.rvBakeryList.adapter = bakeryAdapter
    }

    private fun addListeners() {
        binding.chipBreadListFilter.setOnClickListener {
            showBakeryListSortDialog()
        }

        binding.ivSearch.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
    }

    private fun collectData() {
        viewModel.bakeryListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    bakeryAdapter.setGoalList(it.data.toMutableList())
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
        viewModel.bakeryCategoryType.flowWithLifecycle(lifecycle).onEach {
            viewModel.fetchBakeryList()
        }.launchIn(lifecycleScope)
    }

    private fun moveToDetail(bakeryId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(BAKERY_ID, bakeryId)
        startActivity(intent)
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
        viewModel.fetchBakeryList()
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
    }
}
