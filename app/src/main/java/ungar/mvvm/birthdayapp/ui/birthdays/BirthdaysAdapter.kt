package ungar.mvvm.birthdayapp.ui.birthdays

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_birthday.*
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.databinding.ItemBirthdayBinding
import ungar.mvvm.birthdayapp.model.Birthday
import java.util.*

class BirthdaysAdapter(private val listener: OnItemClickListener, private val context: Context) : ListAdapter<Birthday, BirthdaysAdapter.BirthdaysViewHolder>(DiffCallback()) {

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
            1 -> context.resources.getString(R.string.january_short)
            2 -> context.resources.getString(R.string.february_short)
            3 -> context.resources.getString(R.string.march_short)
            4 -> context.resources.getString(R.string.april_short)
            5 -> context.resources.getString(R.string.may_short)
            6 -> context.resources.getString(R.string.june_short)
            7 -> context.resources.getString(R.string.july_short)
            8 -> context.resources.getString(R.string.august_short)
            9 -> context.resources.getString(R.string.september_short)
            10 -> context.resources.getString(R.string.october_short)
            11 -> context.resources.getString(R.string.november_short)
            12 -> context.resources.getString(R.string.december_short)
            else -> "Err"
        }
    }
}