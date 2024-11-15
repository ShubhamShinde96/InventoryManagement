package com.im.dairyinventorymanagement.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.im.dairyinventorymanagement.data.model.ModuleData
import com.im.dairyinventorymanagement.databinding.ModuleListItemBinding

class ModulesListAdapter(val itemClickListener: ((ModuleData) -> Unit)? = null) :
    RecyclerView.Adapter<ModulesListAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<ModuleData>() {
        override fun areItemsTheSame(oldItem: ModuleData, newItem: ModuleData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData) =
            oldItem.id == newItem.id
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ModuleListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val module = differ.currentList[position]
        holder.bind(module)
    }

    inner class ViewHolder(private val binding: ModuleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ModuleData) {
            binding.apply {
                moduleCard.setCardBackgroundColor(binding.root.context.getColor(data.backgroundColor))
                imageCard.setCardBackgroundColor(binding.root.context.getColor(data.backgroundColor))
                title.text = data.title
                description.text = data.description
            }

            Glide.with(binding.moduleImage.context).load(data.image).into(binding.moduleImage)

            binding.root.setOnClickListener {
                itemClickListener?.let { it(data) }
            }
        }
    }
}
