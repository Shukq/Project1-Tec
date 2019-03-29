package com.example.project1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LogInActivity : AppCompatActivity() {
    private  var btn_login : Button ?= null
    private  var txt_user : EditText ?= null
    private  var txt_pass : EditText ?= null
    private var user = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        btn_login = findViewById(R.id.btn_login)
        txt_pass = findViewById(R.id.txt_pass)
        txt_user = findViewById(R.id.txt_user)

        btn_login?.setOnClickListener {
            validate()
        }

    }

    private fun validate()
    {
        val email = txt_user?.text.toString()
        val pass = txt_pass?.text.toString()

        if (email == user && this.pass == pass)
        {
            val intent = Intent(this,HomeActivity2::class.java)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
        }

    }

}

