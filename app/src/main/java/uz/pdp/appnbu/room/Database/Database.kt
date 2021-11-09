package uz.pdp.appnbu.room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.pdp.appnbu.room.dao.HistoryModelDao
import uz.pdp.appnbu.room.DataConvertor
import uz.pdp.appnbu.room.entities.HistoryModel


@Database(entities = [HistoryModel::class],version = 2)
@TypeConverters(DataConvertor::class)
    abstract class AppDatabase:RoomDatabase() {
    abstract fun historymodeldao():HistoryModelDao
        companion object{
            private var appDatabase:AppDatabase?=null
            @Synchronized
            fun getInstance(context: Context):AppDatabase{
                if(appDatabase==null){
                    appDatabase= Room.databaseBuilder(context,AppDatabase::class.java,"my_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()


                }
                return appDatabase!!
            }
        }
    }
