package com.example.sotutest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
// import android.widget.Toast // (Toastはもう使わないので削除してもOK)
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_login)

        val emailLoginButton = findViewById<MaterialButton>(R.id.emailLoginButton)
        val googleLoginButton = findViewById<MaterialButton>(R.id.googleLoginButton)

        // ★★★ 修正箇所1: IDを変更 ★★★
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)

        // 4. 「メールログイン」ボタンがクリックされたときの動作
        emailLoginButton.setOnClickListener {
            val intent = Intent(this, EmailLoginActivity::class.java)

            // ★★★ 追加: 「ログインモード」であることを伝える ★★★
            intent.putExtra("LOGIN_MODE", "LOGIN")

            startActivity(intent)
        }

        // 5. 「Googleログイン」ボタンがクリックされたときの動作
        googleLoginButton.setOnClickListener {
            val intent = Intent(this, GoogleLoginActivity::class.java)
            startActivity(intent)
        }

        // 6. 「アカウント登録」ボタンがクリックされたときの動作
        registerButton.setOnClickListener {
            val intent = Intent(this, EmailLoginActivity::class.java)

            // ★★★ 追加: 「登録モード」であることを伝える ★★★
            intent.putExtra("LOGIN_MODE", "REGISTER")

            startActivity(intent)
        }
    }
}