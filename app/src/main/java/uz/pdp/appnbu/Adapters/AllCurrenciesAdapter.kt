package uz.pdp.appnbu.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.pdp.appnbu.R
import uz.pdp.appnbu.databinding.ItemAllcurrenciesrvBinding
import uz.pdp.appnbu.models.CurrencyModel

class AllCurrenciesAdapter(var list:ArrayList<CurrencyModel>,var listener:AllCurrenciesAdapter.onCalculatorClick):RecyclerView.Adapter<AllCurrenciesAdapter.Vh>() {
     inner class Vh(var itemRvBinding: ItemAllcurrenciesrvBinding):
             RecyclerView.ViewHolder(itemRvBinding.root){
         fun onBind(currencyModel: CurrencyModel,context:Context) {
             itemRvBinding.currency=currencyModel
             Glide.with(context)
                 .load("https://nbu.uz/local/templates/nbu/images/flags/${list[position].code}.png")
                 .into(itemRvBinding.flagview)
               itemRvBinding.calcmenu.setOnClickListener {
                   listener.onclick(currencyModel)
               }

         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemAllcurrenciesrvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
         holder.onBind(list[position],holder.itemView.context)
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rvanim)

        holder.itemView.animation = animation

        animation.start()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface onCalculatorClick{
        fun onclick(currencyModel: CurrencyModel)
    }

    fun updatelist(a: List<CurrencyModel>){
        list=ArrayList<CurrencyModel>()
        list.addAll(a)
        notifyDataSetChanged()
    }

}