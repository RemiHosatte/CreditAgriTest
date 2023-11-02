package com.rh.creditagritest

import com.google.gson.annotations.SerializedName


data class BankData (

  @SerializedName("banks" ) var banks : ArrayList<Banks> = arrayListOf()

)