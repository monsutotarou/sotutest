// LoginActivity.kt

package com.example.sotutest // ← ご自身のパッケージ名に合っているか確認してください

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sotutest.R
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        // この行でXMLレイアウトと繋ぎます
        setContentView(R.layout.activity_login)
    }
}