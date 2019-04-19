package com.example.project1.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.project1.api.RetrofitClient
import com.example.project1.R
import com.example.project1.model.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private  var btn_register : Button?= null
    private  var txt_user : EditText?= null
    private  var txt_pass : EditText?= null
    private var txt_confirm : EditText?= null
    private var user = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btn_register = findViewById(R.id.btn_signUp)
        txt_pass = findViewById(R.id.txt_SignUp_Pass)
        txt_user = findViewById(R.id.txt_SignUp_User)
        txt_confirm = findViewById(R.id.txt_pass_confirmation)

        btn_register?.setOnClickListener {
            validate()
        }

    }

    private fun validate()
    {
        val email = txt_user?.text.toString()
        val pass = txt_pass?.text.toString()
        createUser()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
    }

    private fun createUser()
    {
        val email = txt_user?.text.toString().trim()
        val password = txt_pass?.text.toString()

        if(email.isEmpty())
        {
            txt_user!!.error = "Email required"
            txt_user!!.requestFocus()
            return
        }
        if(password.isEmpty())
        {
            txt_pass!!.error = "Password required"
            txt_pass!!.requestFocus()
            return
        }

        RetrofitClient.instance.createUser(email, password)
            .enqueue(object: Callback<DefaultResponse>{
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,"fallo",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_LONG).show()
                    Log.i("ERROR",response.toString())
                }


            })


    }
}
