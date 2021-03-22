package ungar.mvvm.birthdayapp.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ungar.mvvm.birthdayapp.databinding.ItemNoteBinding
import ungar.mvvm.birthdayapp.model.Note

class NotesAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Note, NotesAdapter.NotesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class NotesViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
                checkboxNoteCompleted.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        listener.onCheckBoxClick(note, checkboxNoteCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(note: Note) {
            binding.apply {
                checkboxNoteCompleted.isChecked = note.completed
                //tvNoteRefersTo.text
                tvNoteContent.text = note.content
                tvNoteContent.paint.isStrikeThruText = note.completed
                labelNotePriority.isVisible = note.important
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
        fun onCheckBoxClick(note: Note, checked: Boolean)
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }

}