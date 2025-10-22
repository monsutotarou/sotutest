package com.example.sotutest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.button.MaterialButton

/**
 * アプリ起動時の最初の画面（ログイン方法選択画面）を担当するActivity
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. スプラッシュスクリーンをインストールします
        // 必ず setContentView の前に呼び出してください
        installSplashScreen()

        // 2. このActivityに対応するレイアウトファイル(activity_login.xml)を設定します
        setContentView(R.layout.activity_login)

        // 3. レイアウトから各ボタンを見つけます
        //    R.id.〜 は activity_login.xml で設定したIDです
        val emailLoginButton = findViewById<MaterialButton>(R.id.emailLoginButton)
        val googleLoginButton = findViewById<MaterialButton>(R.id.googleLoginButton)
        val guestLoginButton = findViewById<MaterialButton>(R.id.guestLoginButton)

        // 4. 「メールログイン」ボタンがクリックされたときの動作を設定します
        emailLoginButton.setOnClickListener {
            // EmailLoginActivity（メール入力画面）を開くための「Intent」を作成します
            val intent = Intent(this, EmailLoginActivity::class.java)

            // Intentを使って新しい画面を開きます
            startActivity(intent)
        }

        // 5. 「Googleログイン」ボタンがクリックされたときの動作（現在は未実装のメッセージ表示）
        googleLoginButton.setOnClickListener {
            // TODO: 今後、Googleログインの機能をここに実装します
            Toast.makeText(this, "Googleログインは未実装です", Toast.LENGTH_SHORT).show()
        }

        // 6. 「ゲストログイン」ボタンがクリックされたときの動作（現在は未実装のメッセージ表示）
        guestLoginButton.setOnClickListener {
            // TODO: 今後、ゲストログインの機能をここに実装します
            Toast.makeText(this, "ゲストログインは未実装です", Toast.LENGTH_SHORT).show()
        }
    }
}

