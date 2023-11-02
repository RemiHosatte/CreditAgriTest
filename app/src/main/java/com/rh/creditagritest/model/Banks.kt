package com.rh.creditagritest

import com.google.gson.annotations.SerializedName


data class Banks (

  @SerializedName("name"     ) var name     : String?             = null,
  @SerializedName("isCA"     ) var isCA     : Int?                = null,
  @SerializedName("accounts" ) var accounts : ArrayList<Accounts> = arrayListOf()

)