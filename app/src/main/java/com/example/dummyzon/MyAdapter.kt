package com.example.dummyzon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class MyAdapter(val context: HomeNavFragment, private val productArrayList: List<Product>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: CircleImageView
        val title: TextView
        val price: TextView
        val brand: TextView

        init {
            thumbnail = itemView.findViewById(R.id.ivThumbnail)
            title = itemView.findViewById(R.id.tvTitle)
            price = itemView.findViewById(R.id.tvPrice)
            brand = itemView.findViewById(R.id.tvBrand)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productArrayList[position]

        Glide.with(context).load(currentItem.thumbnail).into(holder.thumbnail)
        holder.title.text = currentItem.title
        holder.price.text = currentItem.price.toString()
        holder.brand.text = currentItem.brand
    }

}