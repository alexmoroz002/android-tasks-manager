package ru.itmo.notes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.itmo.notes.callbacks.FolderCallback
import ru.itmo.notes.R
import ru.itmo.notes.entities.Folder

class FolderListAdapter(private val callback: FolderCallback) : ListAdapter<Folder, FolderListAdapter.FolderViewHolder>(FoldersComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): FolderViewHolder {
        return FolderViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val item: Folder = getItem(position)
        holder.itemView.setOnClickListener { callback.onClick(item) }
        holder.bind(item.title)
    }

    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val folderItemView: TextView = itemView.findViewById(R.id.folder_title)

        fun bind(text: String?) {
            folderItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): FolderViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.folder_item, parent, false)
                return FolderViewHolder(view)
            }
        }
    }

    class FoldersComparator : DiffUtil.ItemCallback<Folder>() {
        override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem.title == newItem.title
        }
    }
}