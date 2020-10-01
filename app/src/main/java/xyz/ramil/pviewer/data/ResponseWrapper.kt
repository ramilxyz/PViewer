package xyz.ramil.pviewer.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseWrapper<out T> : Serializable {
    @SerializedName("response")
    val data: T? = null
}