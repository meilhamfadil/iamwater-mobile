package com.akuasih.app

import android.app.DatePickerDialog
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private var nodeId: Int = 0
    private var nodeName: String = ""

    private val historyList by lazy { MutableLiveData<List<HistoryModel>>() }
    private val selectedDate by lazy {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Calendar.getInstance().time)
        MutableLiveData(date)
    }

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

        binding.historyRefresh.setOnRefreshListener {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Calendar.getInstance().time)
            loadData(selectedDate.value ?: date)
        }

        binding.qos.setOnClickListener {
            val intent = Intent(applicationContext, QosActivity::class.java)
            intent.putExtras(
                bundleOf(
                    QosActivity.PARAMS_NODE_ID to nodeId,
                )
            )
            startActivity(intent)
        }

        binding.historyRecycler.layoutManager = LinearLayoutManager(applicationContext)
        binding.historyRecycler.adapter = HistoryListAdapter(emptyList(), ::onClickItem)

        binding.date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(
                this@HistoryActivity,
                { _, y, m, d -> selectedDate.value = "$y-${m + 1}-$d" },
                year, month, day
            )
            datePicker.show()
        }

        historyList.observe(this@HistoryActivity) {
            binding.historyRecycler.adapter = HistoryListAdapter(it, ::onClickItem)

            it.firstOrNull()?.let { cur ->
                binding.ph.text = cur.ph.toString()
                binding.metal.text = cur.metals.toString()
                binding.tds.text = cur.particles.toString()
                binding.oxygen.text = cur.oxygen.toString()
                val output = cur.output ?: 999.0
                binding.status.text = when {
                    output <= 30 -> "Sangat Buruk"
                    output <= 42.5 -> "Buruk"
                    output <= 60 -> "Sedang"
                    output <= 80 -> "Baik"
                    output <= 100 -> "Sangat Baik"
                    else -> "Belum di Kalkulasi"
                }
            }
        }

        selectedDate.observe(this@HistoryActivity) {
            binding.date.text = it
            loadData(it)
        }

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Calendar.getInstance().time)
        loadData(selectedDate.value ?: date)
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

    private fun loadData(date: String) {
        lifecycleScope.launch {
            try {
                val data = ServiceHelper.api.getHistories(nodeId, date)
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