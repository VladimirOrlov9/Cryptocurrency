package com.vladimirorlov9.cryptocurrency.ui.home.nfts

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
import com.vladimirorlov9.cryptocurrency.domain.models.NFTsInStock

// TODO if DiffUtil are useless here????
class HomeNFTsAdapter(
    private val onClickEvent: (Int) -> Unit
) : ListAdapter<NFTsInStock, HomeNFTsAdapter.OverviewViewHolder>(DiffCallback()) {

    inner class OverviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageId: TextView = itemView.findViewById(R.id.image_id)
        private val imageName: TextView = itemView.findViewById(R.id.image_name)
        private val image: ImageView = itemView.findViewById(R.id.image)

        fun bind(position: Int) {
            val data = currentList[position]

            val imageIdText = "#${data.id}"
            imageId.text = imageIdText
            imageName.text = data.name

            Glide.with(itemView)
                .load(data.imageUrl)
                .centerInside()
                .into(image)

            itemView.setOnClickListener {
                onClickEvent(position)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<NFTsInStock>() {
        override fun areItemsTheSame(oldItem: NFTsInStock, newItem: NFTsInStock): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NFTsInStock, newItem: NFTsInStock): Boolean =
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