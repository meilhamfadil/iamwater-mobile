package com.akuasih.app.adapter

import android.util.Printer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akuasih.app.databinding.ItemNodeBinding
import com.akuasih.app.model.NodeModel
import java.security.PrivateKey

class NodeListAdapter(
    private val listItem: List<NodeModel>,
    private val onClickItem: (Int, String) -> Unit,
) : RecyclerView.Adapter<NodeListAdapter.NodeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeListViewHolder {
        return NodeListViewHolder(
            ItemNodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listItem.count()

    override fun onBindViewHolder(holder: NodeListViewHolder, position: Int) {
        val node = listItem[position]
        holder.binding.apply {
            name.text = node.name
            description.text = node.description

            container.setOnClickListener { onClickItem.invoke(node.id, node.name) }
        }
    }

    class NodeListViewHolder(val binding: ItemNodeBinding) : RecyclerView.ViewHolder(binding.root)
}