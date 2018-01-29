package com.example.filippofantinato.calculator

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashScreen : AppCompatActivity()
{
    val SPLASH_DISPLAY_LENGTH: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Handler().postDelayed(Runnable {
            /* Create an Intent that will start the Menu-Activity. */
            this@SplashScreen.startActivity(Intent(this, MainActivity::class.java))
            this@SplashScreen.finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}