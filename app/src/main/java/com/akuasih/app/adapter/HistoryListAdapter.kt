package com.akuasih.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akuasih.app.databinding.ItemHistoryBinding
import com.akuasih.app.model.HistoryModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class HistoryListAdapter(
    private val listItem: List<HistoryModel>,
    private val onClickItem: (HistoryModel) -> Unit,
) : RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListViewHolder {
        return HistoryListViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listItem.count()

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) {
        val history = listItem[position]
        holder.binding.apply {
            val phValue = ":${history.ph}"
            ph.text = phValue

            val metalValue = ":${history.metals}"
            metal.text = metalValue

            val oxygenValue = ":${history.oxygen}"
            oxygen.text = oxygenValue

            val tdsValue = ":${history.particles}"
            tds.text = tdsValue

            val dateString = if (history.updatedAt.isNullOrEmpty())
                history.createdAt
            else
                history.updatedAt

            try {
                val parseDate = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).parse(dateString)
                if (parseDate != null) {
                    val formatted = SimpleDateFormat(
                        "dd MMM yyyy HH:mm",
                        Locale.getDefault()
                    ).format(parseDate)
                    date.text = formatted
                }
            } catch (e: Exception) {
                date.visibility = View.GONE
            }


            container.setOnClickListener { onClickItem.invoke(history) }
        }
    }

    class HistoryListViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}