package com.jaquelinebruzasco.onlynotes.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaquelinebruzasco.onlynotes.databinding.CategorizeListItemBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel

class CategorizeListAdapter(
    val actionCategorize: (CategoryModel) -> Unit
) : RecyclerView.Adapter<CategorizeListAdapter.CategorizeListViewHolder>() {

    var list: MutableList<CategoryModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategorizeListViewHolder(
        CategorizeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CategorizeListViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    override fun getItemCount() = list.size

    inner class CategorizeListViewHolder(private val binding: CategorizeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: CategoryModel) {
                binding.tvCategoryName.text = data.name
                binding.root.setOnClickListener { actionCategorize(data) }
            }
    }
}