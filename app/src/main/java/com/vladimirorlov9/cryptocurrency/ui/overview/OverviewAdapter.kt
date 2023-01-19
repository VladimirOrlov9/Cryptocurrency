package com.vladimirorlov9.cryptocurrency.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.domain.models.CryptoPrice
import com.vladimirorlov9.cryptocurrency.utils.roundPrice


class OverviewAdapter(
    private val onClickEvent: (Int) -> Unit
) : ListAdapter<CryptoPrice, OverviewAdapter.OverviewViewHolder>(DiffCallback()) {

    inner class OverviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.name)
        private val priceTextView: TextView = itemView.findViewById(R.id.price)

        fun bind(position: Int) {
            val data = currentList[position]

            nameTextView.text = data.name
            val roundedPrice = data.price.roundPrice()
            priceTextView.text = "$$roundedPrice"

            itemView.setOnClickListener {
                onClickEvent(position)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<CryptoPrice>() {
        override fun areItemsTheSame(oldItem: CryptoPrice, newItem: CryptoPrice): Boolean {
            val roundedOldElementPrice = oldItem.price.roundPrice()
            val roundedNewElementPrice = newItem.price.roundPrice()
            return roundedOldElementPrice == roundedNewElementPrice
        }

        override fun areContentsTheSame(oldItem: CryptoPrice, newItem: CryptoPrice): Boolean =
            oldItem == newItem

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.overview_element, parent, false)

        return OverviewViewHolder(itemView)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.bind(position)
    }
}