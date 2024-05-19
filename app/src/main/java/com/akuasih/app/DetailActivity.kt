package com.akuasih.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.akuasih.app.databinding.ActivityDetailBinding
import com.akuasih.app.databinding.ActivityHistoryBinding
import com.akuasih.app.model.ConditionDetailModel
import com.akuasih.app.model.HistoryModel
import com.akuasih.app.service.ServiceHelper
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var nodeId: Int = 0
    private var nodePh: Double = 0.0
    private var nodeMetal: Double = 0.0
    private var nodeOxygen: Double = 0.0
    private var nodeTds: Double = 0.0

    private val history by lazy { MutableLiveData<ConditionDetailModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nodeId = intent.extras?.getInt(PARAMS_NODE_ID) ?: 0
        nodePh = intent.extras?.getDouble(PARAMS_NODE_PH) ?: 0.0
        nodeMetal = intent.extras?.getDouble(PARAMS_NODE_METAL) ?: 0.0
        nodeOxygen = intent.extras?.getDouble(PARAMS_NODE_OXYGEN) ?: 0.0
        nodeTds = intent.extras?.getDouble(PARAMS_NODE_TDS) ?: 0.0
        if (nodeId == 0) finish()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initLayout() {
        history.observe(this@DetailActivity) {
            it.preProcess.apply {
                val nodePhLabel = ":${nodePh}"
                binding.ph.text = nodePhLabel
                val nodeMetalLabel = ":${nodeMetal}"
                binding.metal.text = nodeMetalLabel
                val nodeOxygenLabel = ":${nodeOxygen}"
                binding.oxygen.text = nodeOxygenLabel
                val nodeTdsLabel = ":${nodeTds}"
                binding.tds.text = nodeTdsLabel

                val areaLabel = ":${area}"
                binding.area.text = areaLabel
                val momentLabel = ":${momen}"
                binding.moment.text = momentLabel
                val outputLabel = ":${output}"
                binding.output.text = outputLabel
                val categoryLabel = when {
                    output <= 30 -> ": Sangat Buruk"
                    output <= 42.5 -> ": Buruk"
                    output <= 60 -> ": Sedang"
                    output <= 80 -> ": Baik"
                    else -> ": Sangat Baik"
                }
                binding.category.text = categoryLabel


                binding.webview.settings.javaScriptEnabled = true
                binding.webview.loadUrl("https://akuasih.my.id/condition?ref_id=${nodeId}")
            }

            binding.flipper.displayedChild = 1

            it.calculation.forEach {
//                val view = TextView(applicationContext)
//                binding.areas.addView(view)
            }
        }

        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                ServiceHelper.api.calculateGraph(nodeId)
                val detail = ServiceHelper.api.detailGraph(nodeId)
                history.value = detail
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", "loadData: " + e.message)
            }
        }
    }

    companion object {
        const val PARAMS_NODE_ID = "PARAMS_NODE_ID"
        const val PARAMS_NODE_PH = "PARAMS_NODE_PH"
        const val PARAMS_NODE_METAL = "PARAMS_NODE_METAL"
        const val PARAMS_NODE_OXYGEN = "PARAMS_NODE_OXYGEN"
        const val PARAMS_NODE_TDS = "PARAMS_NODE_TDS"
    }

}