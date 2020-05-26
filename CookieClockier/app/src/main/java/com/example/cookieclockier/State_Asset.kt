package com.example.cookieclockier

import kotlin.random.Random

abstract class State_Asset(val id: String, val image: Int, var price: Int, var produceAmount: Int, var timeAmount: Int) {

    fun decreasePrice(): String {
        var decreseValue = Random.nextInt(0, price/2)
        price -= decreseValue
        return "Now $id state asset has a price: $price"
    }

    fun increaseProduceAmount(): String {
        produceAmount += Random.nextInt(1, 3)
        return "Now $id tate asset has a produce amount: $produceAmount"
    }

    fun increaseTime(): String {
        timeAmount += Random.nextInt(1,4)
        return "Now $id creates cookies $timeAmount times"
    }

}