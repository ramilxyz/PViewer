package xyz.ramil.pikaviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import xyz.ramil.pikaviewer.model.PostModel

class Repo {
    companion object {

        var postDataBase: DataBase? = null

        var data: LiveData<List<PostModel>>? = null

        var post: PostModel? = null

        fun initializeDB(context: Context): DataBase {
            return DataBase.getDataseClient(context)
        }

        fun insertData(context: Context, postModel: PostModel) {
            postDataBase = initializeDB(context)
            CoroutineScope(IO).launch {
                postDataBase!!.postDao().InsertData(postModel)
            }
        }

        fun getData(context: Context): LiveData<List<PostModel>>? {
            postDataBase = initializeDB(context)
            data = postDataBase!!.postDao().getData()
            return data
        }

        fun getPost(context: Context, long: Long): PostModel? {
            postDataBase = initializeDB(context)
            post = postDataBase!!.postDao().getPost(long)

            return post
        }

    }
}