package uz.pdp.appnbu.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.appnbu.databinding.ItemChangesHistoryRvBinding
import uz.pdp.appnbu.models.CurrencyModel
import uz.pdp.appnbu.databinding.ItemViewpagerBinding

class HistoryRvAdapter(var list: List<CurrencyModel>):RecyclerView.Adapter<HistoryRvAdapter.Vh>() {
     inner class Vh(var itemRvBinding: ItemChangesHistoryRvBinding):
             RecyclerView.ViewHolder(itemRvBinding.root){
         fun onBind(currencyModel: CurrencyModel) {
             itemRvBinding.response=currencyModel


         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemChangesHistoryRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}