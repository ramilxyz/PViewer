package xyz.ramil.pikaviewer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.ramil.pikaviewer.model.PostModel

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: PostModel)

    @Query("SELECT * FROM PostModel")
    fun getData(): LiveData<List<PostModel>>

    @Query("SELECT * FROM PostModel WHERE  :id=id")
    fun getPost(id: Long): PostModel?
}