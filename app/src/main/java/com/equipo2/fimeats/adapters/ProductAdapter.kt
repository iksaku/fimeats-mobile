package com.equipo2.fimeats.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.equipo2.fimeats.R
import com.equipo2.fimeats.models.Product
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapter(private val context: Context, private val productListener: ProductListener): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    var listProducts = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.product_item, parent, false
        ))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = listProducts[position]
        Log.i("PRODUCT!!!", product.name)
        holder.tvName.text = product.name
        if (product.quantity.isNotEmpty()) {
            holder.tvQuantity.text = "Cantidad: ${product.quantity}"
        }
        holder.tvPrice.text = "Precio: $${String.format("%.2f", product.price)}"
        Log.i("IMAGEN", product.image)

        var idImage: Int? = null
        if (product.image.isNotEmpty()) {
            idImage = context.resources.getIdentifier(
                product.image.substringBeforeLast('.')
                    .lowercase(Locale.getDefault()).replace('-', '_'), "drawable", context.packageName)
        }

        holder.ivImage.setImageResource(idImage ?: R.drawable.food_placeholder)

        holder.itemView.setOnClickListener {
            productListener.onClick(product, position)
        }
    }

    override fun getItemCount() = listProducts.size

    fun updateData(data: List<Product>) {
        listProducts.clear()
        listProducts.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvProductQuantity)
        val tvPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val ivImage: ImageView = itemView.findViewById(R.id.ivProductImage)
    }

}