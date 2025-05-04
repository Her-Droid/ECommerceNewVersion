package id.herdroid.ecommerce.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.herdroid.ecommerce.R

class CategoryAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(DIFF_CALLBACK) {

    private var selectedCategory: String = "All"

    fun setSelectedCategory(category: String) {
        selectedCategory = category
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)

        fun bind(category: String) {
            tvCategory.text = category
            tvCategory.setOnClickListener {
                if (selectedCategory != category) {
                    selectedCategory = category
                    notifyDataSetChanged()
                }
                onClick(category)
            }

            val isSelected = category == selectedCategory
            tvCategory.setBackgroundResource(
                if (isSelected) R.drawable.bg_category_selected
                else R.drawable.bg_category_unselected
            )
            tvCategory.setTextColor(
                ContextCompat.getColor(
                    tvCategory.context,
                    if (isSelected) R.color.white else R.color.purple_100
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    }
}
