package com.example.project1.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.project1.api.RetrofitClient
import com.example.project1.R
import com.example.project1.model.DefaultResponse
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private  var btn_register : Button?= null
    private  var txt_user : EditText?= null
    private  var txt_pass : EditText?= null
    private var txt_confirm : EditText?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btn_register = findViewById(R.id.btn_resetPass)
        txt_pass = findViewById(R.id.txt_SignUp_Pass)
        txt_user = findViewById(R.id.txt_SignUp_User)
        txt_confirm = findViewById(R.id.txt_pass_confirmation)

        btn_register?.setOnClickListener {
            createUser()
        }


    }


    private fun createUser()
    {
        val email = txt_SignUp_User?.text.toString().trim()
        val password = txt_SignUp_Pass?.text.toString()
        val confirm = txt_pass_confirmation?.text.toString()
        if(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()&& password == confirm)
        {
            val hash:HashMap<String,Any> = hashMapOf()
            hash.set("email",email)
            hash.set("password",password)

            RetrofitClient.instance.createUser(hash)
                .enqueue(object: Callback<DefaultResponse>{
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity,"User not created",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(this@SignUpActivity,"User created",Toast.LENGTH_LONG).show()
                            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@SignUpActivity,"User already exists!",Toast.LENGTH_LONG).show()
                            txt_user?.text = Editable.Factory.getInstance().newEditable("")
                            txt_pass?.text = Editable.Factory.getInstance().newEditable("")
                            txt_confirm?.text = Editable.Factory.getInstance().newEditable("")
                        }

                    }


                })
        }
        else
        {
            Toast.makeText(this,"Check your credentials",Toast.LENGTH_LONG).show()
        }

    }
}
