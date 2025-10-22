package com.example.sotutest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sotutest.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ★エラーを解消するため、こちらも正しいレイアウトファイルを指すように変更
        setContentView(R.layout.activity_login)
    }
}
