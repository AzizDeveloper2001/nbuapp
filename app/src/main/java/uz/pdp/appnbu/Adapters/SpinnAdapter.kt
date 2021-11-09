package uz.pdp.appnbu.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import uz.pdp.appnbu.R
import uz.pdp.appnbu.databinding.SpinnerItemBinding
import uz.pdp.appnbu.models.CurrencyModel

class SpinnAdapter(var list:ArrayList<CurrencyModel>):
    BaseAdapter(){
    override fun getCount(): Int {
       return list.size

    }

    override fun getItem(position: Int): CurrencyModel? {
       return list[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding:SpinnerItemBinding = if(convertView==null){
            SpinnerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        } else{
            SpinnerItemBinding.bind(convertView)
        }
        if(list[position].code=="UZS"){
            Glide.with(parent.context)
                .load(R.drawable.flag_uzb)
                .into(binding.flagimage)
        } else {
            Glide.with(parent.context)
                .load("https://nbu.uz/local/templates/nbu/images/flags/${list[position].code}.png")
                .into(binding.flagimage)
        }
        binding.currencytext.text=list[position].code
        return binding.root
    }

}