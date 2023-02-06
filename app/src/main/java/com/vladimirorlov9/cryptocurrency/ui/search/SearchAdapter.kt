package com.vladimirorlov9.cryptocurrency.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.domain.models.SearchCoin
import com.vladimirorlov9.cryptocurrency.utils.FilterableListAdapter
import java.util.*


class SearchAdapter(
    private val onClickEvent: (String, String) -> Unit
) : FilterableListAdapter<SearchCoin, SearchAdapter.OverviewViewHolder>(DiffCallback()) {
    inner class OverviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val coinName: TextView = itemView.findViewById(R.id.coin_name)
        private val coinPrice: TextView = itemView.findViewById(R.id.coin_price)
        private val coinNameShort: TextView = itemView.findViewById(R.id.coin_name_short)

        fun bind(position: Int) {
            val data = currentList[position]

            coinName.text = data.name
            coinNameShort.text = data.nameShort
            val coinPriceText = "$${data.price}"
            coinPrice.text = coinPriceText


            itemView.setOnClickListener {
                onClickEvent(data.id, data.name)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<SearchCoin>() {
        override fun areItemsTheSame(oldItem: SearchCoin, newItem: SearchCoin): Boolean {
            return oldItem.price == newItem.price
        }

        override fun areContentsTheSame(oldItem: SearchCoin, newItem: SearchCoin): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_coin_element, parent, false)

        return OverviewViewHolder(itemView)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onFilter(list: List<SearchCoin>, constraint: String): List<SearchCoin> {
        return list.filter {
            it.name.equals(constraint, ignoreCase = true) ||
                    it.nameShort.equals(constraint, ignoreCase = true)
        }
    }
}