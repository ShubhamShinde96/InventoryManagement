package com.im.dairyinventorymanagement.presentation.adapter

import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.im.dairyinventorymanagement.R
import com.im.dairyinventorymanagement.data.model.response.Product
import com.im.dairyinventorymanagement.databinding.ProductListItemBinding

class ProductListAdapter() : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val module = differ.currentList[position]
        holder.bind(module)
    }

    inner class ViewHolder(private val binding: ProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Product) {
            binding.apply {
                productName.text = data.title
                availableQuantity.text = binding.root.context.resources.getString(
                    R.string.available_quantity, data.availableqty.toString()
                )
                quantity.setText(data.orderQty)
                quantity.filters = arrayOf(MaxValueInputFilter(data.availableqty))

                binding.quantity.addTextChangedListener(object: TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        data.orderQty = s.toString().toInt()
                    }
                })
            }
        }
    }
}

class MaxValueInputFilter(private val maxValue: Int) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (input <= maxValue) {
                return null
            }
        } catch (e: NumberFormatException) {
            // Ignore invalid input
        }
        return ""
    }
}
