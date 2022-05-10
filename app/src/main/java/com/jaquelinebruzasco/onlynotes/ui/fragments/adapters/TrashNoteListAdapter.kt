package com.jaquelinebruzasco.onlynotes.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaquelinebruzasco.onlynotes.databinding.NoteListItemBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.NotesModel
import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel

class TrashNoteListAdapter(
    val action: (TrashNotesModel) -> Unit
) : RecyclerView.Adapter<TrashNoteListAdapter.TrashNoteListViewHolder>() {

    var list: MutableList<TrashNotesModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrashNoteListViewHolder(
        NoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TrashNoteListViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    override fun getItemCount() = list.size

    inner class TrashNoteListViewHolder(private val binding: NoteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: TrashNotesModel) {
            binding.apply {
                tvNoteTitle.text = note.title
                tvNoteCategory.text = note.category
            }
            binding.root.setOnClickListener { action(note) }
        }
    }
}