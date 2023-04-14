package com.example.fotografpaylasimuygulamasi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.fotografpaylasimuygulamasi.R
import com.google.firebase.auth.FirebaseAuth

class KullaniciActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val guncelkullanici = auth.currentUser
        if (guncelkullanici!=null){
            val intent = Intent(this, HaberlerActivity::class.java)
            startActivity(intent)
            finish()

        }









    }
    fun girisYap(view:View){
        val email = findViewById<TextView>(R.id.emailText).text.toString()
        val sifre = findViewById<TextView>(R.id.passwordText).text.toString()
        auth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener { task->
            if (task.isSuccessful){
                val guncelKullanici = auth.currentUser?.email.toString()
                Toast.makeText(this,"Hoşgeldin:${guncelKullanici}",Toast.LENGTH_LONG).show()
                val intent = Intent(this, HaberlerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception->
            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
    fun kayitOl(view:View){
        val email = findViewById<TextView>(R.id.emailText).text.toString()
        val sifre = findViewById<TextView>(R.id.passwordText).text.toString()

        try {
            auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener { task ->
                // asenkron
                if (task.isSuccessful) {
                    // diğer aktiviteye gidelim
                    val intent = Intent(this, HaberlerActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e("createUser", "Hata: ${e.localizedMessage}")
        }



    }
}