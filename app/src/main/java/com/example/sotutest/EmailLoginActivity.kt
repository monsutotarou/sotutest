package com.example.sotutest

// import android.content.Intent // (HomeActivityへの遷移時に必要)
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmailLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var currentMode = "LOGIN" // "LOGIN" か "REGISTER" が入る

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_email_login)

        auth = Firebase.auth

        // XMLレイアウトからViewを取得
        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.buttonLogin)
        val forgotPasswordText: TextView = findViewById(R.id.textViewForgotPassword)
        val backButton: ImageButton = findViewById(R.id.buttonBack)

        // ★★★ 1. LoginActivityから渡されたモードを受け取る ★★★
        currentMode = intent.getStringExtra("LOGIN_MODE") ?: "LOGIN"

        // ★★★ 2. モードに応じてボタンのテキストを変更 ★★★
        if (currentMode == "REGISTER") {
            loginButton.text = "アカウントを登録"
        } else {
            loginButton.text = "ログイン"
        }


        // 全画面表示の調整
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- 1. ログイン/登録ボタンが押された時の処理 ---
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "メールアドレスとパスワードを入力してください", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ★★★ 3. モードに応じて処理を分岐 ★★★
            if (currentMode == "REGISTER") {
                // --- アカウント登録処理 (復活) ---
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "アカウントを登録しました", Toast.LENGTH_SHORT).show()
                            // (そのままログイン状態になるので、ホーム画面に遷移してもOK)
                            /*
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                            */
                        } else {
                            Toast.makeText(this, "登録に失敗しました: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                // --- ログイン処理 ---
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "ログインしました", Toast.LENGTH_SHORT).show()
                            /*
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                            */
                        } else {
                            Toast.makeText(this, "ログインに失敗しました: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        // --- 戻るボタンの処理 ---
        backButton.setOnClickListener {
            finish()
        }

        // --- パスワードを忘れた方テキストの処理 ---
        forgotPasswordText.setOnClickListener {
            Toast.makeText(this, "この機能は未実装です", Toast.LENGTH_SHORT).show()
        }
    }
}