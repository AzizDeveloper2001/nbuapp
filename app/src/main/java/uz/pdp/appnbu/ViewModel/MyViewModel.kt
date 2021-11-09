package uz.pdp.appnbu.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import uz.pdp.appnbu.models.CurrencyModel
import uz.pdp.appnbu.Retrofit.ApiClient
import uz.pdp.appnbu.Utils.NetworkHelper
import uz.pdp.appnbu.room.Database.AppDatabase

class MyViewModel:ViewModel() {
    fun getretrofitlist(context: Context):MutableLiveData<List<CurrencyModel>>{
        val  list=MutableLiveData<List<CurrencyModel>>()
        val networkHelper=NetworkHelper(context)
        val appDatabase=AppDatabase.getInstance(context)
        if(networkHelper.isNetworkConnected()){
            ApiClient.apiService.getcurrencylist().enqueue(object :retrofit2.Callback<List<CurrencyModel>>{
                override fun onResponse(
                    call: Call<List<CurrencyModel>>,
                    currency: Response<List<CurrencyModel>>
                ) {
                    if(currency.isSuccessful){
                        list.value=currency.body()
                    }
                }

                override fun onFailure(call: Call<List<CurrencyModel>>, t: Throwable) {

                }

            })
        } else {
            val a=appDatabase.historymodeldao().getallhistorymodels()
            if(a.isNotEmpty()){
                list.value=a.last().list
            }
        }
        return list
    }
}