package com.vladimirorlov9.cryptocurrency.ui.home.tokens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.domain.models.CoinInStock


class HomeTokensAdapter(
    private val onClickEvent: (String, String) -> Unit
) : ListAdapter<CoinInStock, HomeTokensAdapter.OverviewViewHolder>(DiffCallback()) {

    inner class OverviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val coinName: TextView = itemView.findViewById(R.id.coin_name)
        private val availableCoins: TextView = itemView.findViewById(R.id.available_coins)
        private val coinLogo: ImageView = itemView.findViewById(R.id.coin_logo)

        fun bind(position: Int) {
            val data = currentList[position]

            coinName.text = data.coinName

            val availableCoinsText = "${data.availableCoins} ${data.coinShortName}"
            availableCoins.text = availableCoinsText

            Glide.with(itemView)
                .load(data.logoUrl)
                .centerInside()
                .into(coinLogo)

            itemView.setOnClickListener {
                onClickEvent(data.id, data.coinName)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<CoinInStock>() {
        override fun areItemsTheSame(oldItem: CoinInStock, newItem: CoinInStock): Boolean {
            return oldItem.availableCoins == newItem.availableCoins
        }

        override fun areContentsTheSame(oldItem: CoinInStock, newItem: CoinInStock): Boolean =
            oldItem == newItem

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_tokens_element, parent, false)

        return OverviewViewHolder(itemView)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.bind(position)
    }
}