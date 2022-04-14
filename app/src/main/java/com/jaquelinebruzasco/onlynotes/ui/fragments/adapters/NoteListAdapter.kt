package com.jaquelinebruzasco.onlynotes.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaquelinebruzasco.onlynotes.databinding.NoteListItemBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel

class NoteListAdapter(
    val action: (NotesModel) -> Unit
) : RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    var list: MutableList<NotesModel?> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteListViewHolder(
        NoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = list.size

    inner class NoteListViewHolder(private val binding: NoteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NotesModel) {
            binding.apply {
                tvNoteTitle.text = data.title
                tvNoteCategory.text = data.category
            }
            binding.root.setOnClickListener { action(data) }
        }
    }

}