package com.example.torangsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.splashlibrary.SplashFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, SplashFragment()).commit()
    }
}