package xyz.ramil.pikaviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import xyz.ramil.pikaviewer.model.PostModel

class DataBaseManager {
    private var database: DataBase? = null
    var dbName = "PikaViewer"
    private var context: Context? = null
    var postClass: PostClass? = null
        get() {
            if (field == null) field = PostClass()
            return field
        }
        private set

    private fun initDataBase() {
        if (context == null) {
            return
        }
        if (database == null) database = Room
            .databaseBuilder(context!!, DataBase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    inner class PostClass {
        fun addPost(model: PostModel?) {
            if (model == null) {
                return
            }
            database!!.postDao().insertPost(model)
        }

        val allPosts: LiveData<List<PostModel>>
            get() = database!!.postDao().postsLiveData

        fun deletPosts() {
            database!!.postDao().delete()
        }
    }

    companion object {
        var instance: DataBaseManager? = null
        @Synchronized
        fun create(ctx: Context?): DataBaseManager? {
            if (ctx == null) {
                throw NullPointerException("DataBaseManager::context null")
            }
            if (instance == null) {
                instance = DataBaseManager()
            }
            instance!!.context = ctx
            instance!!.initDataBase()
            return instance
        }

        fun get(): DataBaseManager? {
            if (instance == null) return null
            if (instance!!.context == null) {
                return instance
            }
            if (instance!!.database == null || !instance!!.database!!.isOpen) create(
                instance!!.context
            )
            instance!!.initDataBase()
            return instance
        }

        fun release() {
            if (instance == null) return
            if (instance!!.database != null) {
                instance!!.database!!.close()
                instance!!.database = null
            }
            instance = null
        }
    }
}