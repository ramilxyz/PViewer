package xyz.ramil.pikaviewer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.List;

import xyz.ramil.pikaviewer.model.PostModel;

@Database(entities = {PostModel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})

abstract class DataBase extends RoomDatabase {

    public abstract IPostDao postDao();

    @Dao
    static abstract class IPostDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        abstract long insertPost(PostModel model);

        @Query("SELECT * FROM PostModel")
        abstract LiveData<List<PostModel>> getPostsLiveData();

        @Query("DELETE FROM PostModel")
        abstract void delete();
    }


}
