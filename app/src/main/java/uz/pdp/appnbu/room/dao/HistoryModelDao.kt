package uz.pdp.appnbu.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.pdp.appnbu.room.entities.HistoryModel

@Dao
interface HistoryModelDao {
    @Insert()
    fun addhistorymodel(historyModel: HistoryModel)

    @Query("select * from historymodel")
    fun getallhistorymodels():List<HistoryModel>

}