package com.akuasih.app.fragment

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akuasih.app.HistoryActivity
import com.akuasih.app.adapter.NodeListAdapter
import com.akuasih.app.databinding.FragmentInfoBinding
import com.akuasih.app.databinding.FragmentNodeBinding
import com.akuasih.app.model.NodeModel
import com.akuasih.app.service.ServiceHelper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NodeFragment : Fragment() {

    private lateinit var binding: FragmentNodeBinding

    private val nodeList by lazy { MutableLiveData<List<NodeModel>>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNodeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nodeRefresh.setOnRefreshListener { loadData() }

        binding.nodeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.nodeRecycler.adapter = NodeListAdapter(emptyList(), ::onClickItem)

        nodeList.observe(viewLifecycleOwner) {
            binding.nodeRecycler.adapter = NodeListAdapter(it, ::onClickItem)
        }

        loadData()
    }

    private fun onClickItem(refId: Int, name: String) {
        val intent = Intent(requireContext(), HistoryActivity::class.java)
        intent.putExtras(
            bundleOf(
                HistoryActivity.PARAMS_NODE_ID to refId,
                HistoryActivity.PARAMS_NODE_NAME to name
            )
        )
        startActivity(intent)
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                val data = ServiceHelper.api.getNodes()
                nodeList.value = data
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", "loadData: " + e.message)
            }
        }
    }
}