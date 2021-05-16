package com.example.test.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.data.api.model.Entity
import com.example.test.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class EntityAdapter : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    private val mList = mutableListOf<Entity>()
    private var mClickListener: OnEntityClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding, mClickListener)
    }

    fun setItems(list: List<Entity>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnEntityClickListener(listener: OnEntityClickListener) {
        this.mClickListener = listener
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(mList[holder.adapterPosition])
    }


    class EntityViewHolder(val binding: ListItemBinding, val mClickListener: OnEntityClickListener?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Entity) {
            Picasso.get().load(entity.Poster).into(binding.ivImage)
            binding.tvTitle.text = entity.Title
            binding.tvDescription.text = entity.Plot
            binding.root.setOnClickListener {
                mClickListener?.onEntityClicked(entity)
            }
        }
    }
}

interface OnEntityClickListener {
    fun onEntityClicked(entity: Entity)
}

