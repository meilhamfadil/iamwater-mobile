package com.akuasih.app

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.akuasih.app.adapter.HistoryListAdapter
import com.akuasih.app.databinding.ActivityHistoryBinding
import com.akuasih.app.model.HistoryModel
import com.akuasih.app.service.ServiceHelper
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private var nodeId: Int = 0
    private var nodeName: String = ""

    private val historyList by lazy { MutableLiveData<List<HistoryModel>>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nodeName = intent.extras?.getString(PARAMS_NODE_NAME).orEmpty()
        nodeId = intent.extras?.getInt(PARAMS_NODE_ID) ?: 0
        if (nodeId == 0) finish()

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        binding.title.title = nodeName

        binding.historyRefresh.setOnRefreshListener { loadData() }

        binding.historyRecycler.layoutManager = LinearLayoutManager(applicationContext)
        binding.historyRecycler.adapter = HistoryListAdapter(emptyList(), ::onClickItem)

        historyList.observe(this@HistoryActivity) {
            binding.historyRecycler.adapter = HistoryListAdapter(it, ::onClickItem)
        }

        loadData()
    }

    private fun onClickItem(history: HistoryModel) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtras(
            bundleOf(
                DetailActivity.PARAMS_NODE_ID to history.refId,
                DetailActivity.PARAMS_NODE_PH to history.ph,
                DetailActivity.PARAMS_NODE_METAL to history.metals,
                DetailActivity.PARAMS_NODE_OXYGEN to history.oxygen,
                DetailActivity.PARAMS_NODE_TDS to history.particles,
            )
        )
        startActivity(intent)
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                val data = ServiceHelper.api.getHistories(nodeId)
                historyList.value = data
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", "loadData: " + e.message)
            }
        }
    }

    companion object {
        const val PARAMS_NODE_ID = "PARAMS_NODE_ID"
        const val PARAMS_NODE_NAME = "PARAMS_NODE_NAME"
    }

}