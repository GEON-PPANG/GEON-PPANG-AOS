package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentStartGeonPpangBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.util.binding.BindingFragment

class StartGeonPpangFragment :
    BindingFragment<FragmentStartGeonPpangBinding>(R.layout.fragment_start_geon_ppang) {
    private lateinit var viewModel: FilterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(FilterViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        val fragmentManager = requireActivity().supportFragmentManager

        binding.ivStartGeonPpang.setOnClickListener {
            fragmentManager.popBackStack()
        }

        binding.btnStartGeonPpang.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
