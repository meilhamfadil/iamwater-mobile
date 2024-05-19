package com.akuasih.app.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.akuasih.app.databinding.FragmentGraphBinding
import com.akuasih.app.model.NodeModel
import com.akuasih.app.service.ServiceHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GraphFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentGraphBinding

    private val nodes = MutableLiveData<List<NodeModel>>()
    private val selectedType = MutableLiveData("ph")
    private val selectedNode = MutableLiveData(0)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGraphBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            spinner.onItemSelectedListener = this@GraphFragment
            ph.setOnClickListener { selectedType.value = "ph" }
            metal.setOnClickListener { selectedType.value = "metals" }
            oxygen.setOnClickListener { selectedType.value = "oxygen" }
            tds.setOnClickListener { selectedType.value = "particles" }
        }

        nodes.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it.map { node -> node.name + "(" + node.id + ")" }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
            selectedNode.value = it.first().id
        }

        selectedType.observe(viewLifecycleOwner) {
            if (selectedNode.value != 0)
                loadWeb(selectedNode.value ?: 0, it)
        }

        selectedNode.observe(viewLifecycleOwner) {
            loadWeb(it, selectedType.value ?: "ph")
        }

        loadNode()
    }

    private fun loadNode() {
        lifecycleScope.launch {
            val dataNodes = ServiceHelper.api.getNodes()
            nodes.value = dataNodes
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWeb(node: Int, type: String) {
        binding.webview.settings.javaScriptEnabled = true
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Calendar.getInstance().time)
        val url = "http://akuasih.my.id/condition/graph?node=${node}&types=${type}&date=${date}"
        binding.webview.loadUrl(url)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedNode = nodes.value?.get(position)?.id
        if (selectedNode != null)
            loadWeb(selectedNode, selectedType.value ?: "ph")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}