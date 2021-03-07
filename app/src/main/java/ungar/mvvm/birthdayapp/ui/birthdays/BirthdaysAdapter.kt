package ungar.mvvm.birthdayapp.ui.birthdays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_birthday.*
import ungar.mvvm.birthdayapp.databinding.ItemBirthdayBinding
import ungar.mvvm.birthdayapp.model.Birthday
import java.util.*

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
                imgProfile.setImageResource(birthday.profilePicture)
                tvMonth.text = setMonth(birthday.month)
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

    private fun setMonth(month: Int): String {
        return when(month){
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> "Err"
        }
    }
}