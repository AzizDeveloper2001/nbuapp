package uz.pdp.appnbu.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.pdp.appnbu.models.CurrencyModel

@Entity
data class HistoryModel (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val list:List<CurrencyModel>
        ){
}