package com.defconapplications.repobrowser.detailsfragment

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.defconapplications.repobrowser.R

class TextAdapter() : ListAdapter<String, TextViewHolder>(TextDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.update(getItem(position))
    }
}

class TextViewHolder private constructor(view: TextView) :
        RecyclerView.ViewHolder(view) {

    fun update(langText: String) {
        (itemView as TextView).text = langText
    }

    companion object {
        fun from(parent: ViewGroup) : TextViewHolder {
            val view = TextView(parent.context)
            if (Build.VERSION.SDK_INT >= 23)
                view.setTextAppearance(R.style.TextAppearance_MyTheme_Body2)
            return TextViewHolder(view)
        }
    }
}

class TextDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}