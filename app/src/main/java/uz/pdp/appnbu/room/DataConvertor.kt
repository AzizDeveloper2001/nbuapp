package uz.pdp.appnbu.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.pdp.appnbu.models.CurrencyModel

class DataConvertor {

    @TypeConverter
    fun fromstring(str:String):List<CurrencyModel>{
       val listtype=object :TypeToken<List<CurrencyModel>>(){}.type
        return Gson().fromJson(str,listtype)
    }
    @TypeConverter
    fun fromarraylist(list:List<CurrencyModel>):String{
        val gson=Gson()
        return gson.toJson(list)

    }
}