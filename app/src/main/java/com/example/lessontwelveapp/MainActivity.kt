package com.example.lessontwelveapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    // Объявление переменных для пользовательского интерфейса
    lateinit var etEmail: EditText
    lateinit var etConfPass: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var tvRedirectLogin: TextView

    // Обьект для аутентификации с помощью Firebase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {
                v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                systemBars.bottom)
            insets
        }
        //инициализация пользовательского интерфейса
        etEmail = findViewById(R.id.etSEmailAddress)
        etConfPass = findViewById(R.id.etSConfPassword)
        etPass = findViewById(R.id.etSPassword)
        btnSignUp = findViewById(R.id.btnSSigned)
        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)
        // Инициализация объекта FirebaseAuth для аутентификации
        auth = Firebase.auth

        btnSignUp.setOnClickListener {
            singUpUser()
        }
        //Если у нас, есть аккаунт переходим на активность LoginActivity
        tvRedirectLogin.setOnClickListener{
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun singUpUser() {
        //Все поля с editText,сохраняем в переменные в виде строк текста
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confPass = etConfPass.text.toString()
        //Проверка на пустые поля
        if (email.isBlank() || pass.isBlank() || confPass.isBlank()) {
            Toast.makeText(this, "Почта и пароль не могут быть пустыми",
                Toast.LENGTH_SHORT).show()
            return
        }
        //Проверка на совпадение паролей
        if (pass != confPass) {
            Toast.makeText(this, "Пароль и пароль подтверждения не совпадают", Toast.LENGTH_SHORT).show()
                return
        }
        //Создание пользователя с использованием email и пароля через
        Firebase

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this,"Регистрация успешна",Toast.LENGTH_SHORT).show()
                        intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this,"Ошибка регистрации",Toast.LENGTH_SHORT).show()
            }
        }

    }
}