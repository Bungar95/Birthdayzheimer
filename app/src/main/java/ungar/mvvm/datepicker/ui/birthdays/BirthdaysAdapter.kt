package ungar.mvvm.datepicker.ui.birthdays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ungar.mvvm.datepicker.databinding.ItemBirthdayBinding
import ungar.mvvm.datepicker.model.Birthday

class BirthdaysAdapter(private val listener: OnItemClickListener) : ListAdapter<Birthday, BirthdaysAdapter.BirthdaysViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdaysViewHolder {
        val binding = ItemBirthdayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BirthdaysViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BirthdaysViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class BirthdaysViewHolder(private val binding: ItemBirthdayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
            }
        }

        fun bind(birthday: Birthday) {
            binding.apply {
                tvName.text = birthday.name
                tvDate.text = "${birthday.day} ${birthday.month} ${birthday.year}"
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(birthday: Birthday)
    }

    class DiffCallback : DiffUtil.ItemCallback<Birthday>(){
        override fun areItemsTheSame(oldItem: Birthday, newItem: Birthday) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Birthday, newItem: Birthday) =
            oldItem == newItem
    }
}