package uz.pdp.appnbu.WorkManager

import android.content.Context
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.pdp.appnbu.Retrofit.ApiClient
import uz.pdp.appnbu.Utils.NetworkHelper
import uz.pdp.appnbu.models.CurrencyModel
import uz.pdp.appnbu.room.Database.AppDatabase
import uz.pdp.appnbu.room.entities.HistoryModel

class MyWorker(val context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {
    private lateinit var appDatabase: AppDatabase
    private lateinit var networkHelper: NetworkHelper
    override fun doWork(): Result {

        appDatabase= AppDatabase.getInstance(context)
        networkHelper= NetworkHelper(context)

        ApiClient.apiService.getcurrencylist().enqueue(object :Callback<List<CurrencyModel>>{
            override fun onResponse(
                call: Call<List<CurrencyModel>>,
                response: Response<List<CurrencyModel>>
            ) {
                if(response.isSuccessful){

                        val date=appDatabase.historymodeldao().getallhistorymodels().
                        reversed()[0].list[0].date

                        if(response.body()!![0].date!=date){
                            appDatabase.historymodeldao().addhistorymodel(HistoryModel(list = response.body()!!))
                        }
                    }

                }


            override fun onFailure(call: Call<List<CurrencyModel>>, t: Throwable) {

            }

        })
      return Result.success()
    }

}