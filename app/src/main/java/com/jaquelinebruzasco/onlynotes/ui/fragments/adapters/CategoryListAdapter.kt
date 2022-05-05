package com.jaquelinebruzasco.onlynotes.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaquelinebruzasco.onlynotes.databinding.CategoryListItemBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel

class CategoryListAdapter(
    val actionEdit: (CategoryModel) -> Unit,
    val actionDelete: (CategoryModel) -> Unit
) : RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {

    var list: MutableList<CategoryModel?> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryListViewHolder(
        CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = list.size

    inner class CategoryListViewHolder(private val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: CategoryModel) {
                binding.apply {
                    tvCategoryName.text = data.name
                    root.setOnClickListener { actionEdit(data) }
                    ivDeleteCategory.setOnClickListener { actionDelete(data) }
                }
            }
        }
}