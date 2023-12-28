package ru.itmo.notes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.itmo.notes.callbacks.NoteCallback
import ru.itmo.notes.R
import ru.itmo.notes.entities.Note

class NoteListAdapter(private val callback: NoteCallback) : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteListAdapter.NotesComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { callback.onClick(item) }
        holder.bind(item.title, item.text)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTitle: TextView = itemView.findViewById(R.id.note_title)
        private val noteText: TextView = itemView.findViewById(R.id.note_text)

        fun bind(title: String?, text: String?) {
            noteTitle.text = title
            noteText.text = text
        }

        companion object {
            fun create(parent: ViewGroup): NoteViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
                return NoteViewHolder(view)
            }
        }
    }

    class NotesComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title == newItem.title && oldItem.text == newItem.text
        }
    }
}