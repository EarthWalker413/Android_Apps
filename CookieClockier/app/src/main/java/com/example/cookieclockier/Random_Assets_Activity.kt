package com.example.cookieclockier

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.sys_bonuses_assets.*


class Random_Assets_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sys_bonuses_assets)

        val intent = intent
        var score = intent.getIntExtra("cookiesNumber", 0)

        first_system_bonus.setOnClickListener {
//            presentDialog(Assets.sysAsset_1, score, R.drawable.)
            presentDialog(Assets.sysAsset_1, score, android.R.drawable.ic_menu_call)
        }

        second_system_bonus.setOnClickListener {
            presentDialog(Assets.sysAsset_2, score, android.R.drawable.ic_menu_edit)
        }

        third_system_bonus.setOnClickListener {
            presentDialog(Assets.sysAsset_3, score, android.R.drawable.btn_star_big_on)
        }

        fourth_system_bonus.setOnClickListener {
            presentDialog(Assets.sysAsset_4, score, android.R.drawable.btn_star)
        }

        fifth_system_bonus.setOnClickListener {
            presentDialog(Assets.sysAsset_5, score, android.R.drawable.ic_lock_lock)
        }

        sixth_system_bonus.setOnClickListener {
            presentDialog(Assets.sysAsset_6, score, android.R.drawable.checkbox_on_background)
        }


    }

    private fun presentRejectDialog(price: Int) {
        Toast.makeText(this, "You need $price cookies", Toast.LENGTH_SHORT).show()
    }

    private fun presentDialog(random: Sys_Asset_Random, score: Int, image: Int) {
        val builder = AlertDialog.Builder(this@Random_Assets_Activity)
        val randomPrice = random.price
        val produceAmount = random.produceAmount
        builder.setTitle("Purchase Random")
        builder.setMessage("Price: $randomPrice cookies, Produce: $produceAmount")
        builder.setPositiveButton("BUY"){ _, _ ->
            if (score >= random.price) {
                createResponseIntent(random, image)
            } else {
               presentRejectDialog(random.price)
            }
        }

        builder.setNegativeButton("CANCEL"){ dialog, _ ->
            dialog.cancel()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun createResponseIntent(random: Sys_Asset_Random, image: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra("image", image)
        resultIntent.putExtra("produceVal", random.produceAmount)
        resultIntent.putExtra("randomId", random.id)
        val usedCookies = random.price.unaryMinus()
        resultIntent.putExtra("cookiesUsed", usedCookies)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}

