package com.example.cookieclockier

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game_layout.*
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class GameActivity : AppCompatActivity() {

    private val mHandler = Handler()
    private lateinit var mTimer: Timer

    private val STATE_REQUEST_CODE_CHEAT = 0
    private val random_REQUEST_CODE_CHEAT = 1

    private var score = AtomicInteger(0)
    private var state_asset_ProduceVal: Int = 1
    private var state_asset_TimeVal: Int = 0
    private var randomProduceVal: Int = 0
    private var delay: Long = 0
    private var state_asset_StartPoint: Int = 0
    private var randomStartPoint: Int = 0
    private var state_asset_ID: String? = "none"
    private var randomID: String? = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)

        purchaseUpgradeButton.setOnClickListener {
            if (score.get() >= 20) {
                purchaseUpgrade()
            }
            else
                Toast.makeText(this@GameActivity, "You do not have enough cookies", Toast.LENGTH_SHORT).show()
        }

        cook_amount_view.text = getString(R.string.number_of_cookies, score.get())

        startBonusCookieTimer()
        bonusImageView.setOnClickListener {
            mTimer.cancel()
            setVisibilyOfBonus()
            when (Random().nextBoolean()) {
                true -> score.getAndAdd(11)
                false -> {
                    Assets.click.increaseProduceAmount()
                    startIncreaseClickProductionTimer()
                }
            }
            cook_amount_view.text =
                getString(R.string.number_of_cookies, score.get())
            startBonusCookieTimer()
        }

        cookyImage.setOnClickListener {v ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            v.startAnimation(bounceAnimation)
            incrementScore(Assets.click.produceAmount)
        }

        stateAssetImage.setOnClickListener {
            if (stateAssetImage.drawable == null) {
               val intent = Intent(this, StateAssetsActivity::class.java)
               intent.putExtra("cookiesNumber", score.get())
               startActivityForResult(intent, STATE_REQUEST_CODE_CHEAT)
           }
        }

        randomAssetImage.setOnClickListener {
            if (randomAssetImage.drawable == null ) {
                val intent = Intent(this, Random_Assets_Activity::class.java)
                intent.putExtra("cookiesNumber", score.get())
                startActivityForResult(intent, random_REQUEST_CODE_CHEAT)
            }
        }

    }

    private fun purchaseUpgrade() {
        score.addAndGet(-20)
        cook_amount_view.text = getString(R.string.number_of_cookies, score.get())
        var message = UpgradeList.getRandomUpgrade()
        Toast.makeText(this@GameActivity, message, Toast.LENGTH_SHORT).show()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("score", score.get())


        if (stateAssetImage.drawable != null) {
            editor.putBoolean("restoreStateAssetTask", true)
            editor.putInt("state_asset_StartPoint", state_asset_StartPoint)
            editor.putInt("state_asset_TimeVal", state_asset_TimeVal)
            editor.putInt("state_asset_ProduceVal", state_asset_ProduceVal)
            editor.putString("state_asset_ID", state_asset_ID)
        } else
            editor.putBoolean("restoreStateAssetTask", false)

        if (randomAssetImage.drawable != null) {
            editor.putBoolean("restorerandomTask", true)
            editor.putInt("randomStartPoint", randomStartPoint)
            editor.putInt("randomProduceValue", randomProduceVal)
            editor.putString("randomID", randomID)
        } else {
            editor.putBoolean("restorerandomTask", false)
        }
        editor.commit()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        var value = sharedPref.getInt("score", 0)

        score.set(value)
        cook_amount_view.text = getString(R.string.number_of_cookies, score.get())

        val restoreStateTask = sharedPref.getBoolean("restoreStateAssetTask", false)
        if (restoreStateTask) {
            state_asset_StartPoint = sharedPref.getInt("state_asset_StartPoint", 0)
            state_asset_TimeVal = sharedPref.getInt("state_asset_TimeVal", 0)
            state_asset_ID = sharedPref.getString("state_asset_ID", "none")
            when (state_asset_ID) {
                "first state" -> stateAssetImage.setImageResource(android.R.drawable.presence_video_busy)
                "second state" -> stateAssetImage.setImageResource(android.R.drawable.presence_video_online)
                "third state" -> stateAssetImage.setImageResource(android.R.drawable.presence_video_away)
            }

            StateAssetIncreaseScoreTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }

        val restorerandomTask = sharedPref.getBoolean("restorerandomTask", false)
        if (restorerandomTask) {
            randomStartPoint = sharedPref.getInt("randomStartPoint", 0)
            randomID = sharedPref.getString("randomID", "none")
            randomProduceVal = sharedPref.getInt("randomProduceValue", 0)

            when (randomID) {
                "first random" -> randomAssetImage.setImageResource(android.R.drawable.ic_menu_call)
                "second random" -> randomAssetImage.setImageResource(android.R.drawable.ic_menu_edit)
                "third random" -> randomAssetImage.setImageResource(android.R.drawable.btn_star_big_on)
                "fourth random" -> randomAssetImage.setImageResource(android.R.drawable.btn_star)
                "fifth random" -> randomAssetImage.setImageResource(android.R.drawable.ic_lock_lock)
                "sixth random" -> randomAssetImage.setImageResource(android.R.drawable.checkbox_on_background)
            }
            randomIncreaseScoreTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == STATE_REQUEST_CODE_CHEAT) {
                    val usedAmount = data.getIntExtra("cookiesUsed", 0)
                    cook_amount_view.text =
                        getString(R.string.number_of_cookies, score.addAndGet(usedAmount))
                    val resultImage = data.getIntExtra("image", 0)
                    stateAssetImage.setImageResource(resultImage)
                    state_asset_ProduceVal = data.getIntExtra("state_asset_ProduceVal", 0)
                    state_asset_TimeVal = data.getIntExtra("timeVal", 0)
                    state_asset_ID = data.getStringExtra("state_asset_ID")
                    StateAssetIncreaseScoreTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                } else if (requestCode == random_REQUEST_CODE_CHEAT) {
                    val usedAmount = data.getIntExtra("cookiesUsed", 0)
                    cook_amount_view.text =
                        getString(R.string.number_of_cookies, score.addAndGet(usedAmount))
                    val resultImage = data.getIntExtra("image", 0)
                    randomAssetImage.setImageResource(resultImage)
                    randomProduceVal = data.getIntExtra("produceVal", 0)
                    randomID = data.getStringExtra("randomId")
                    randomIncreaseScoreTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                }
            }
        }
    }

    private fun startBonusCookieTimer() {
        mTimer = Timer()
        mTimer.schedule(object : TimerTask() {
            override fun run() {
                mHandler.post(Runnable {
                    cancel()
                    setVisibilyOfBonus()
                    startBonusCookieTimer()
                })
            }
        },delay, 1000)
    }

    private fun startIncreaseClickProductionTimer() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                Assets.click.setDefaultProduceAmount()
            }
        },5000)
    }


    private fun incrementScore(produceNumber: Int) {
        score.getAndAdd(produceNumber)
        if (score.get() > 200) {
            presentEndGameDialog()
        }
        val newScore = getString(R.string.number_of_cookies, score.get())
        cook_amount_view.text = newScore
    }

    private fun presentEndGameDialog() {
        val builder = AlertDialog.Builder(this)
        val endGameTime = System.currentTimeMillis()


        builder.setTitle("Game completed")
        builder.setMessage("You have won")

        builder.setPositiveButton("Exit"){ _, _ ->
            finish()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setVisibilyOfBonus() {
        if (bonusImageView.visibility == View.VISIBLE) {
            delay = 7000
            bonusImageView.visibility = View.INVISIBLE
        } else if (bonusImageView.visibility == View.INVISIBLE) {
            delay = 1000
            bonusImageView.visibility = View.VISIBLE
        }
    }

    inner class StateAssetIncreaseScoreTask : AsyncTask<Void, Int, String>() {

        override fun doInBackground(vararg params: Void?): String? {
            for (i in state_asset_StartPoint until state_asset_TimeVal) {
                Thread.sleep(500)
                state_asset_StartPoint = i
                publishProgress(score.addAndGet(state_asset_ProduceVal))
            }
            return "OK"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val newScore = getString(R.string.number_of_cookies, values[0])
            cook_amount_view.text = newScore
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            stateAssetImage.setImageResource(0)
            state_asset_StartPoint = 0
        }

    }

    inner class randomIncreaseScoreTask : AsyncTask<Void, Int, String>() {

        override fun doInBackground(vararg params: Void?): String? {
            for (i in randomStartPoint until 10) {
                Thread.sleep(1000)
                publishProgress(score.addAndGet(randomProduceVal))
            }
            return "OK"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val newScore = getString(R.string.number_of_cookies, values[0])
            cook_amount_view.text = newScore
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            randomAssetImage.setImageResource(0)
        }
    }
}
