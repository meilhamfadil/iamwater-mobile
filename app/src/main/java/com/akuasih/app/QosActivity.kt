package com.akuasih.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.akuasih.app.databinding.ActivityQosBinding
import com.akuasih.app.model.ConditionDetailModel

class QosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQosBinding
    private var nodeId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nodeId = intent.extras?.getInt(PARAMS_NODE_ID) ?: 0
        if (nodeId == 0) finish()

        binding = ActivityQosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initLayout() {
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl("https://akuasih.my.id/condition/result?node=${nodeId}")
    }

    companion object {
        const val PARAMS_NODE_ID = "PARAMS_NODE_ID"
    }

}