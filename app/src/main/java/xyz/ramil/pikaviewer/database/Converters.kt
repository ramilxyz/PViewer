package xyz.ramil.pikaviewer.database

import androidx.room.TypeConverter

import java.util.*
import java.util.stream.Collectors

class Converters {
    @TypeConverter
    fun from(from: List<String?>): String {
        return from.stream().collect(Collectors.joining(","))
    }
    @TypeConverter
    fun to(to: String): List<String> {
        return Arrays.asList(*to.split(",".toRegex()).toTypedArray())
    }
}