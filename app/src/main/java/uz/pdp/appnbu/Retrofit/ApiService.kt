package uz.pdp.appnbu.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import uz.pdp.appnbu.models.CurrencyModel

interface ApiService {
@GET("uz/exchange-rates/json/")
 fun getcurrencylist():Call<List<CurrencyModel>>
}