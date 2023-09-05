package com.sopt.geonppang.util.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.DialogMiddleBinding

abstract class BindingDialogFragment<B : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    DialogFragment() {
    private var _binding: DialogMiddleBinding? = null
    val binding get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogMiddleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
        )
        dialog?.window?.setBackgroundDrawableResource(R.color.dialog_background)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(true)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
