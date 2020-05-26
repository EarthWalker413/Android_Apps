package com.example.cookieclockier

class Click(var produceAmount: Int = 1) {
    
    fun increaseProduceAmount(): String {
        synchronized(this) {
            produceAmount++
            return "Now click gives $produceAmount cookies"
        }
    }

    fun setDefaultProduceAmount() {
        synchronized(this) {
            produceAmount = 1
        }
    }
}