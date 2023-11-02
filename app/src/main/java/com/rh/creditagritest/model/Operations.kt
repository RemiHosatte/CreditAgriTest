package com.rh.creditagritest

import com.google.gson.annotations.SerializedName
import java.lang.Long.parseLong
import java.text.SimpleDateFormat
import java.util.Date


data class Operations(

    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("date") var date: String? = null


)

fun Operations.getDateTime(): String? {
    try {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(parseLong(date) * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}