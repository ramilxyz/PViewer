package xyz.ramil.pviewer.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

 class ResponseWrapper<out T>() : Serializable {
     @SerializedName("response")
    val data: T? = null
 }