package com.defconapplications.repobrowser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.defconapplications.repobrowser.databinding.RecyclerViewItemBinding
import com.defconapplications.repobrowser.repository.ShortRepo

class RepoAdapter(val clickListener: RepoClickListener) : ListAdapter<ShortRepo, RepoViewHolder>(
        RepoDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.update(
            getItem(position),
            clickListener
        )
    }
}

class RepoViewHolder private constructor(val binding: RecyclerViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun update(repo: ShortRepo, clickListener: RepoClickListener) {
        binding.name.text = repo.name
        binding.description.text = repo.description ?: "-"
        binding.lang.text = repo.main_language ?: "-"
        binding.frame.setOnClickListener {
            clickListener.onClick(repo)
        }
    }

    companion object {
        fun from(parent: ViewGroup) : RepoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerViewItemBinding.inflate(inflater, parent, false)
            return RepoViewHolder(binding)
        }
    }
}

class RepoDiffCallback : DiffUtil.ItemCallback<ShortRepo>() {
    override fun areItemsTheSame(oldItem: ShortRepo, newItem: ShortRepo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShortRepo, newItem: ShortRepo): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.description == newItem.description &&
                oldItem.main_language == newItem.main_language
    }
}

class RepoClickListener(val clickListener: (repo: ShortRepo) -> Unit) {
    fun onClick(repo: ShortRepo) = clickListener(repo)
}