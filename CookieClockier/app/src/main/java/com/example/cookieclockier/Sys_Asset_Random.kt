package com.example.cookieclockier

import kotlin.random.Random

class Sys_Asset_Random(val id: String, var price: Int = Random.nextInt(11,31), var produceAmount: Int = price/10) {

    fun decreasePrice(): String {
        val decrease = Random.nextInt(0, price/2)
        price -= decrease
        return "Now $id has a price: $price"
    }

    fun increaseProduceAmount(): String {
        val increase = Random.nextInt(1, 3)
        produceAmount += increase
        return "Now $id has produce amount: $produceAmount"
    }

}