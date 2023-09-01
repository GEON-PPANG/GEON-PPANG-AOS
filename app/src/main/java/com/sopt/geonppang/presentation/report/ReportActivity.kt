package com.sopt.geonppang.presentation.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReportBinding
import com.sopt.geonppang.util.binding.BindingActivity

class ReportActivity : BindingActivity<ActivityReportBinding>(R.layout.activity_report) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
    }
}
