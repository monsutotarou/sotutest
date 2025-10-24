package com.example.sotutest

import android.content.Intent // HomeActivityへの遷移用にインポート
import android.os.Bundle
import android.widget.Button // Buttonをインポート
import android.widget.EditText // EditTextをインポート
import android.widget.ImageButton // ImageButtonをインポート
import android.widget.TextView // TextViewをインポート
import android.widget.Toast // Toast（通知）をインポート
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth // Firebase Auth をインポート
import com.google.firebase.auth.ktx.auth // Firebase Auth をインポート
import com.google.firebase.ktx.Firebase // Firebase をインポート

class EmailLoginActivity : AppCompatActivity() {

    // Firebase Auth のインスタンスを宣言
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_email_login)

        // Firebase Auth のインスタンスを初期化
        auth = Firebase.auth

        // XMLレイアウトからViewを取得
        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.buttonLogin)
        val registerText: TextView = findViewById(R.id.textViewRegister)
        val forgotPasswordText: TextView = findViewById(R.id.textViewForgotPassword) // (※もしあれば)
        val backButton: ImageButton = findViewById(R.id.buttonBack)

        // Android 10以降の全画面表示（ステータスバーなど）の調整
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- 1. ログインボタンが押された時の処理 ---
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 入力が空でないかチェック
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "メールアドレスとパスワードを入力してください", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 処理を中断
            }

            // Firebaseでログイン
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // ログイン成功
                        Toast.makeText(this, "ログインしました", Toast.LENGTH_SHORT).show()

                        // HomeActivityがまだ無いので、遷移処理はコメントアウト
                        /*
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish() // このログイン画面を閉じる
                        */
                    } else {
                        // ログイン失敗
                        Toast.makeText(this, "ログインに失敗しました: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // --- 2. アカウント登録テキストが押された時の処理 ---
        registerText.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 入力が空でないかチェック
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "メールアドレスとパスワードを入力してください", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 処理を中断
            }

            // Firebaseで新規登録
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 登録成功
                        Toast.makeText(this, "アカウントを登録しました", Toast.LENGTH_SHORT).show()

                        // 登録成功時もHomeActivityへの遷移は一旦コメントアウト
                        /*
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        */

                    } else {
                        // 登録失敗 (パスワードが短い、メール形式が不正、既に登録済みなど)
                        Toast.makeText(this, "登録に失敗しました: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // --- 3. 戻るボタンが押された時の処理 ---
        backButton.setOnClickListener {
            finish() // この画面(Activity)を閉じて、前の画面に戻る
        }

        // --- 4. パスワードを忘れた方テキストが押された時の処理 (おまけ) ---
        // (※もし実装する場合)
        /*
        forgotPasswordText.setOnClickListener {
            // パスワードリセット用の画面に遷移するなどの処理
        }
        */
    }
}