package com.example.sotutest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    private val TAG = "GoogleLoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)

        auth = Firebase.auth

        // --- Googleサインインの準備 ---
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // --- 結果の受け取り処理 ---
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // 結果が返ってきたら、タスクを取得
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleGoogleSignInResult(task)
        }

        // --- ★画面が作成されたら、すぐにGoogleログインフローを開始 ---
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    // --- Googleサインインの結果処理 ---
    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "Googleサインイン成功: ${account.id}")
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "Googleサインイン失敗", e)
            Toast.makeText(this, "Googleサインインに失敗しました: ${e.message}", Toast.LENGTH_LONG).show()

            // ★失敗したら、この画面を閉じて選択画面に戻る
            finish()
        }
    }

    // --- FirebaseへGoogle認証情報でログイン ---
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Firebaseにログインしました: ${user?.displayName}", Toast.LENGTH_SHORT).show()

                    // ★成功したらHomeActivityへ (まだ無ければコメントアウト)
                    /*
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    */

                    // ★ HomeActivityに行く場合も、行かない場合も、この画面は閉じる
                    finish()

                } else {
                    Toast.makeText(this, "Firebaseログインに失敗しました: ${task.exception?.message}", Toast.LENGTH_LONG).show()

                    // ★失敗したら、この画面を閉じて選択画面に戻る
                    finish()
                }
            }
    }
}