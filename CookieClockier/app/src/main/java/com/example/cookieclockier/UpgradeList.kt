package com.example.cookieclockier

import kotlin.random.Random

object UpgradeList {
    fun getRandomUpgrade(): String {
        return when (Random.nextInt(0, 21)) {
            0 -> Assets.firstStateAsset.decreasePrice()
            1 -> Assets.firstStateAsset.increaseProduceAmount()
            2 -> Assets.firstStateAsset.increaseTime()
            3 -> Assets.secondStateAsset.decreasePrice()
            4 -> Assets.secondStateAsset.increaseProduceAmount()
            5 -> Assets.secondStateAsset.increaseTime()
            6 -> Assets.thirdStateAsset.decreasePrice()
            7 -> Assets.thirdStateAsset.increaseProduceAmount()
            8 -> Assets.thirdStateAsset.increaseTime()
            9 -> Assets.sysAsset_1.increaseProduceAmount()
            10 -> Assets.sysAsset_1.decreasePrice()
            11 -> Assets.sysAsset_2.increaseProduceAmount()
            12 -> Assets.sysAsset_2.decreasePrice()
            13 -> Assets.sysAsset_3.increaseProduceAmount()
            14 -> Assets.sysAsset_3.decreasePrice()
            15 -> Assets.sysAsset_4.increaseProduceAmount()
            16 -> Assets.sysAsset_4.decreasePrice()
            17 -> Assets.sysAsset_5.increaseProduceAmount()
            18 -> Assets.sysAsset_5.decreasePrice()
            19 -> Assets.sysAsset_6.increaseProduceAmount()
            20 -> Assets.sysAsset_6.decreasePrice()
            21 -> Assets.click.increaseProduceAmount()
            else -> "nothing"
        }

    }

}