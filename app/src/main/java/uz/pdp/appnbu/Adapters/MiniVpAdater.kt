package uz.pdp.appnbu.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.appnbu.R
import uz.pdp.appnbu.models.CurrencyModel
import uz.pdp.appnbu.databinding.ItemViewpagerBinding

class MiniVpAdater(var list: List<CurrencyModel>):RecyclerView.Adapter<MiniVpAdater.Vh>() {
     inner class Vh(var itemRvBinding: ItemViewpagerBinding):
             RecyclerView.ViewHolder(itemRvBinding.root){
         fun onBind(currencyModel: CurrencyModel) {
             itemRvBinding.currency=currencyModel


         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.vpanim)

        holder.itemView.animation = animation
    }

    override fun getItemCount(): Int {
        return list.size
    }
}