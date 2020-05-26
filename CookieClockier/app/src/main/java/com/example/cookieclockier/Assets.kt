package com.example.cookieclockier

object Assets {
    val firstStateAsset = FirstStateAsset()
    val secondStateAsset = SecondStateAsset()
    val thirdStateAsset = ThirdStateAsset()
    val sysAsset_1 = Sys_Asset_Random("first random")
    val sysAsset_2 = Sys_Asset_Random("second random")
    val sysAsset_3 = Sys_Asset_Random("third random")
    val sysAsset_4 = Sys_Asset_Random("fourth random")
    val sysAsset_5 = Sys_Asset_Random("fifth random")
    val sysAsset_6 = Sys_Asset_Random("sixth random")

    val click = Click()
}

class FirstStateAsset : State_Asset("first state", android.R.drawable.presence_video_busy, 7,  1, 15)
class SecondStateAsset : State_Asset("second state", android.R.drawable.presence_video_online, 40, 4, 20)
class ThirdStateAsset : State_Asset("third state",android.R.drawable.presence_video_away, 125, 7, 50)

