package appssquare.training2022.task.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import appssquare.training2022.task.R
import appssquare.training2022.task.domain.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import de.hdodenhof.circleimageview.CircleImageView

class ProductsAdapter(private val ctx:Context, private val productsList:ArrayList<Product>) : RecyclerView.Adapter<ProductsAdapter.ProductHolder>() {


    inner class ProductHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var productImg:CircleImageView = itemView.findViewById(R.id.productImg)
        var productNameTxt:TextView = itemView.findViewById(R.id.productNameTxt)
        var productPriceTxt:TextView = itemView.findViewById(R.id.productPriceTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(
            LayoutInflater.from(ctx).inflate(
                R.layout.product_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        var product = productsList[position]
        holder.productImg.loadImage(photo = product.image)
        holder.productNameTxt.text = product.name
        holder.productPriceTxt.text = HtmlCompat.fromHtml("${product.price} ${ctx.getCurrency()}",  HtmlCompat.FROM_HTML_MODE_LEGACY)

    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    private fun Context.getCurrency():String{
        return "<b><font color='red'>${getString(R.string.currency)}</font></b>"
    }


    fun CircleImageView.loadImage(photo: String) {
        Glide.with(this.context).load(photo).fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(this)

    }

}


