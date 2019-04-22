package com.example.project1.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.project1.R
import com.example.project1.api.RetrofitClient
import com.example.project1.model.DefaultResponse
import kotlinx.android.synthetic.main.activity_log_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LogInActivity : AppCompatActivity() {
    private  var btn_login : Button ?= null
    private  var txt_user : EditText ?= null
    private  var txt_pass : EditText ?= null
    private var user = ""
    private var pass = ""
    private lateinit var retrofit : Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        btn_login = findViewById(R.id.btn_resetPass)
        txt_pass = findViewById(R.id.txt_pass)
        txt_user = findViewById(R.id.txt_user)

        btn_login?.setOnClickListener {
            validate()
            //val intent = Intent(this, HomeActivity2::class.java)
            //startActivity(intent)
        }

        txt_passChange.setOnClickListener {
            if(txt_user?.text.toString().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(txt_user?.text.toString()).matches())
            {
                val email = txt_user?.text.toString().trim()
                val hash:HashMap<String,Any> = hashMapOf()
                hash.set("email",email)

                RetrofitClient.instance.forgotPass(hash)
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(this@LogInActivity,"Server error!",Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            if(response.isSuccessful)
                            {
                                Toast.makeText(this@LogInActivity,"Code send!",Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LogInActivity, ChangePassActivity::class.java)
                                intent.putExtra("email",email)
                                startActivity(intent)
                                finish()
                            }
                            else
                            {
                                Toast.makeText(this@LogInActivity,"User dont exist!",Toast.LENGTH_SHORT).show()
                                txt_user?.text = Editable.Factory.getInstance().newEditable("")
                            }

                        }


                    })
            }
            else
            {
                Toast.makeText(this@LogInActivity,"Check your user!",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validate()
    {
        val email = txt_user?.text.toString().trim()
        val password = txt_pass?.text.toString()
        val hash:HashMap<String,Any> = hashMapOf()
        hash.set("email",email)
        hash.set("password",password)

        RetrofitClient.instance.logIn(hash)
            .enqueue(object: Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(this@LogInActivity,"Server error!",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    if(response.isSuccessful)
                    {
                        Toast.makeText(this@LogInActivity,"Login successful!",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LogInActivity, HomeActivity2::class.java)
                        intent.putExtra("email",email)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this@LogInActivity,"User dont exist!",Toast.LENGTH_SHORT).show()
                        txt_user?.text = Editable.Factory.getInstance().newEditable("")
                        txt_pass?.text = Editable.Factory.getInstance().newEditable("")
                    }

                }


            })

    }

}

