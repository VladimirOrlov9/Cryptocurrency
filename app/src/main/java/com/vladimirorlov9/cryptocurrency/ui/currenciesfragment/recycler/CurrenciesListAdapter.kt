package com.vladimirorlov9.cryptocurrency.ui.currenciesfragment.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.domain.models.CryptoPrice
import kotlin.math.roundToInt

const val PRICE_DECIMAL_SIGNS = 100.0

class CurrenciesListAdapter(
): ListAdapter<CryptoPrice, CurrenciesListAdapter.MyViewHolder>(DiffCallback()) {


    private class DiffCallback : DiffUtil.ItemCallback<CryptoPrice>() {

        override fun areItemsTheSame(oldItem: CryptoPrice, newItem: CryptoPrice): Boolean {
            val roundedOldElementPrice = (oldItem.price * PRICE_DECIMAL_SIGNS).roundToInt() / PRICE_DECIMAL_SIGNS
            val roundedNewElementPrice = (newItem.price * PRICE_DECIMAL_SIGNS).roundToInt() / PRICE_DECIMAL_SIGNS
            return roundedOldElementPrice == roundedNewElementPrice
        }

        override fun areContentsTheSame(oldItem: CryptoPrice, newItem: CryptoPrice) =
            oldItem == newItem
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.crypto_name)
        private val price = itemView.findViewById<TextView>(R.id.crypto_price)

        fun bind(position: Int) {
            val element = currentList[position]

            name.text = element.name

            val roundedPrice = (element.price * PRICE_DECIMAL_SIGNS).roundToInt() / PRICE_DECIMAL_SIGNS
            price.text = "$$roundedPrice"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =LayoutInflater.from(parent.context)
            .inflate(R.layout.crypto_line_element, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = currentList.size
}