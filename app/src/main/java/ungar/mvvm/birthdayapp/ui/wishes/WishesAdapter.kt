package ungar.mvvm.birthdayapp.ui.wishes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ungar.mvvm.birthdayapp.databinding.ItemWishBinding
import ungar.mvvm.birthdayapp.model.Wish

class WishesAdapter(private val listener: OnItemClickListener) : ListAdapter<Wish, WishesAdapter.WishesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishesViewHolder {
        val binding = ItemWishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class WishesViewHolder(private val binding: ItemWishBinding) :
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
            }
        }

        fun bind(wish: Wish) {
            binding.apply {
                tvWish.text = wish.quote
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(wish: Wish)
    }

    class DiffCallback : DiffUtil.ItemCallback<Wish>(){
        override fun areItemsTheSame(oldItem: Wish, newItem: Wish) =
            oldItem.quote == newItem.quote

        override fun areContentsTheSame(oldItem: Wish, newItem: Wish) =
            oldItem == newItem
    }
}