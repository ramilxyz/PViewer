package xyz.ramil.pikaviewer.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.util.*
import java.util.stream.Collectors

class Converters {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    fun fromHobbies(hobbies: List<String?>): String {
        return hobbies.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toHobbies(data: String): List<String> {
        return Arrays.asList(*data.split(",".toRegex()).toTypedArray())
    }
}