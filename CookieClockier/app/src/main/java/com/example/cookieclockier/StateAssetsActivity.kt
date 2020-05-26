package com.example.cookieclockier

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.state_bonuses_assets.*

class StateAssetsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.state_bonuses_assets)

        val intent = intent
        var score = intent.getIntExtra("cookiesNumber", 0)

        val firstStateAsset = Assets.firstStateAsset
        configureFirstStateLayout(firstStateAsset, score)

        val secondStateAsset = Assets.secondStateAsset
        configureSecondStateLayout(secondStateAsset, score)

        val thirdStateAsset = Assets.thirdStateAsset
        configureThirdStateLayout(thirdStateAsset, score)
    }

    private fun configureFirstStateLayout(firstStateAsset: State_Asset, score:Int) {
        firstStateAddButton.setOnClickListener {
            if (firstStateAsset.price <= score) {
                val resultIntent = Intent()
                resultIntent.putExtra("image", android.R.drawable.presence_video_busy)
                resultIntent.putExtra("state_asset_ProduceVal", firstStateAsset.produceAmount)
                resultIntent.putExtra("timeVal", firstStateAsset.timeAmount)
                resultIntent.putExtra("state_asset_ID", firstStateAsset.id)
                val usedCookies = firstStateAsset.price.unaryMinus()
                resultIntent.putExtra("cookiesUsed", usedCookies)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "You have not enough cookies", Toast.LENGTH_SHORT).show()
            }
        }

        firstStateAddButton.text = getString(R.string.buyAssetButton, firstStateAsset.price)
        firstStateProduceView.text = getString(R.string.state_asset_produce, firstStateAsset.produceAmount)
        firstStateTimeView.text = getString(R.string.state_asset_time, firstStateAsset.timeAmount)
    }

    private fun configureSecondStateLayout(secondStateAsset: State_Asset, score:Int) {
        secondStateAddButton.setOnClickListener {
            if (secondStateAsset.price <= score) {
                val resultIntent = Intent()
                resultIntent.putExtra("image", android.R.drawable.presence_video_online)
                resultIntent.putExtra("state_asset_ProduceVal", secondStateAsset.produceAmount)
                resultIntent.putExtra("timeVal", secondStateAsset.timeAmount)
                resultIntent.putExtra("state_asset_ID", secondStateAsset.id)
                val usedCookies = secondStateAsset.price.unaryMinus()
                resultIntent.putExtra("cookiesUsed", usedCookies)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "You have not enough cookies", Toast.LENGTH_SHORT).show()
            }
        }

        secondStateAddButton.text = getString(R.string.buyAssetButton, secondStateAsset.price)
        secondStateProduceView.text = getString(R.string.state_asset_produce, secondStateAsset.produceAmount)
        secondStateTimeView.text = getString(R.string.state_asset_time, secondStateAsset.timeAmount)
    }

    private fun configureThirdStateLayout(thirdStateAsset: State_Asset, score: Int) {
        thirdStateAddButton.setOnClickListener {
            if (thirdStateAsset.price <= score) {
                val resultIntent = Intent()
                resultIntent.putExtra("image", android.R.drawable.presence_video_away)
                resultIntent.putExtra("state_asset_ProduceVal", thirdStateAsset.produceAmount)
                resultIntent.putExtra("timeVal", thirdStateAsset.timeAmount)
                resultIntent.putExtra("state_asset_ID", thirdStateAsset.id)
                val usedCookies = thirdStateAsset.price.unaryMinus()
                resultIntent.putExtra("cookiesUsed", usedCookies)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "You have not enough cookies", Toast.LENGTH_SHORT).show()
            }
        }

        thirdStateAddButton.text = getString(R.string.buyAssetButton, thirdStateAsset.price)
        thirdStateProduceView.text = getString(R.string.state_asset_produce, thirdStateAsset.produceAmount)
        thirdStateTimeView.text = getString(R.string.state_asset_time, thirdStateAsset.timeAmount)
    }
}