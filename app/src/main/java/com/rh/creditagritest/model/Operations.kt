package com.rh.creditagritest

import com.google.gson.annotations.SerializedName


data class Operations (

  @SerializedName("id"       ) var id       : String? = null,
  @SerializedName("title"    ) var title    : String? = null,
  @SerializedName("amount"   ) var amount   : String? = null,
  @SerializedName("category" ) var category : String? = null,
  @SerializedName("date"     ) var date     : String? = null

)