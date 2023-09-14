package com.sopt.geonppang.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySearchBinding
import com.sopt.geonppang.presentation.common.BakeryAdapter
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.presentation.detail.DetailActivity.Companion.SOURCE
import com.sopt.geonppang.presentation.detail.DetailActivity.Companion.VIEW_DETAIL_PAGE_AT
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var bakeryAdapter: BakeryAdapter
    private lateinit var searchCountAdapter: SearchCountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        searchCountAdapter = SearchCountAdapter()
        bakeryAdapter = BakeryAdapter(::moveToDetail)
        binding.rvSearchBakeryList.adapter = ConcatAdapter(searchCountAdapter, bakeryAdapter)
    }

    private fun addListeners() {
        binding.ivSearchDelete.setOnClickListener {
            binding.etSearch.text = null
        }

        binding.ivSearchBack.setOnClickListener {
            finish()
        }

        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                completeFilteringInParticularView()
                viewModel.searchBakeryList()
                hideKeyboard(binding.root)
                true
            } else {
                false
            }
        }
    }

    private fun collectData() {
        viewModel.searchState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    viewModel.searchBakeryList()
                    searchCountAdapter.setSearchData(it.data)
                    bakeryAdapter.submitList(it.data.bakeryList)
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToDetail(bakeryId: Int) {
        AmplitudeUtils.trackEventWithProperties(VIEW_DETAIL_PAGE_AT, SOURCE, SEARCH)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(BAKERY_ID, bakeryId)
        startActivity(intent)
    }

    private fun completeFilteringInParticularView() {
        val viewToView = intent.getStringExtra(VIEW_TO_VIEW)
        when (viewToView) {
            HOME_TO_SEARCH -> {
                AmplitudeUtils.trackEventWithProperties(
                    COMPLETE_SEARCH_HOME,
                    KEYWORD,
                    viewModel.inputSearch.value
                )
            }

            BAKERY_LIST_TO_SEARCH -> {
                AmplitudeUtils.trackEventWithProperties(
                    COMPLETE_SEARCH_LIST,
                    KEYWORD,
                    viewModel.inputSearch.value
                )
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        hideKeyboard(binding.root)
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val VIEW_TO_VIEW = "viewToView"
        const val HOME_TO_SEARCH = "homeToSearch"
        const val BAKERY_LIST_TO_SEARCH = "bakeryListToSearch"
        const val COMPLETE_SEARCH_HOME = "complete_search_home"
        const val COMPLETE_SEARCH_LIST = "complete_search_list"
        const val KEYWORD = "keyword"
        const val SEARCH = "SEARCH"
    }
}
