package com.vladimirorlov9.cryptocurrency.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.vladimirorlov9.cryptocurrency.R
import com.vladimirorlov9.cryptocurrency.utils.models.UserOption

class UserOptionsAdapter(
    private val options: List<UserOption>,
    private val onClickEvent: (Int) -> Unit
) : RecyclerView.Adapter<UserOptionsAdapter.OptionViewHolder>() {

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val iconImageView = itemView.findViewById<ImageView>(R.id.icon)
        private val labelTextView = itemView.findViewById<TextView>(R.id.label)

        fun bind(position: Int) {
            val currentOption = options[position]

            iconImageView.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    currentOption.iconResId,
                    null
                )
            )
            labelTextView.text = itemView.resources.getString(currentOption.labelResId)

            itemView.setOnClickListener {
                onClickEvent(currentOption.navigationAction)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_user_options, parent, false)
        return OptionViewHolder(itemView)
    }

    override fun getItemCount(): Int = options.size

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(position)
    }
}