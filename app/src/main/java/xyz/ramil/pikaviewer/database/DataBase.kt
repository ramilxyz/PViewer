package xyz.ramil.pikaviewer.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.ramil.pikaviewer.model.PostModel

@Database(entities = arrayOf(PostModel::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {

    abstract fun postDao(): DAOAccess

    companion object {

        @Volatile
        lateinit var INSTANCE: DataBase

        fun getDataseClient(context: Context): DataBase {

            if (::INSTANCE.isInitialized) return INSTANCE

            synchronized(this) {

                INSTANCE = Room.databaseBuilder(context, DataBase::class.java, "pikaviewer")
                    .fallbackToDestructiveMigration().build()

                return INSTANCE

            }
        }

    }
}