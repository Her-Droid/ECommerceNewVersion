package id.herdroid.ecommerce.presentation.shimmer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.herdroid.ecommerce.R

class ShimmerAdapter : RecyclerView.Adapter<ShimmerAdapter.ShimmerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shimmer, parent, false)
        return ShimmerViewHolder(view)
    }

    override fun getItemCount() = 6

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {}

    inner class ShimmerViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
