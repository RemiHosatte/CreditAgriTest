package com.rh.creditagritest

import com.google.gson.annotations.SerializedName


data class Accounts(

    @SerializedName("order") var order: Int? = null,
    @SerializedName("id") var id: String,
    @SerializedName("holder") var holder: String,
    @SerializedName("role") var role: Int? = null,
    @SerializedName("contract_number") var contractNumber: String,
    @SerializedName("label") var label: String,
    @SerializedName("product_code") var productCode: String,
    @SerializedName("balance") var balance: Double,
    @SerializedName("operations") var operations: ArrayList<Operations> = arrayListOf()

)