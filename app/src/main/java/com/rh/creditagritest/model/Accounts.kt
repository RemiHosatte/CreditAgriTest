package com.rh.creditagritest

import com.google.gson.annotations.SerializedName


data class Accounts (

  @SerializedName("order"           ) var order          : Int?                  = null,
  @SerializedName("id"              ) var id             : String?               = null,
  @SerializedName("holder"          ) var holder         : String?               = null,
  @SerializedName("role"            ) var role           : Int?                  = null,
  @SerializedName("contract_number" ) var contractNumber : String?               = null,
  @SerializedName("label"           ) var label          : String?               = null,
  @SerializedName("product_code"    ) var productCode    : String?               = null,
  @SerializedName("balance"         ) var balance        : Double?               = null,
  @SerializedName("operations"      ) var operations     : ArrayList<Operations> = arrayListOf()

)