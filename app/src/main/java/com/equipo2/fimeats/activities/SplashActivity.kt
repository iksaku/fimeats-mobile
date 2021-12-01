package com.equipo2.fimeats.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.equipo2.fimeats.R

class SplashActivity : AppCompatActivity() {
    private lateinit var llSplashScreen: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.llSplashScreen = findViewById(R.id.llSplashScreen)

        val animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash)
        this.llSplashScreen.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish() //close current activity
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

    }
}