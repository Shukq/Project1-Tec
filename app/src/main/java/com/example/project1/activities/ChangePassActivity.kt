package com.example.project1.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.project1.R
import com.example.project1.api.RetrofitClient
import com.example.project1.model.DefaultResponse
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_log_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassActivity : AppCompatActivity() {
    private var code:EditText ?= null
    private var password:EditText ?= null
    private lateinit var btnReset: Button
    private var email:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        code = findViewById(R.id.txt_code)
        password = findViewById(R.id.txt_passChangeConfirm)
        email = this.intent.getStringExtra("email")
        btnReset = findViewById(R.id.btn_resetPass)
        btnReset.setOnClickListener {
           if(code?.text!!.isNotEmpty() && password?.text!!.isNotEmpty())
           {
               validate()
           }
            else
           {
               Toast.makeText(this@ChangePassActivity,"Check inputs!", Toast.LENGTH_SHORT).show()
           }
        }
    }

    private fun validate()
    {
        val hash:HashMap<String,Any> = hashMapOf()
        hash.set("code",code?.text.toString().toInt())
        hash.set("password",password?.text.toString())
        hash.set("email",email)


        RetrofitClient.instance.updPass(hash)
            .enqueue(object: Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(this@ChangePassActivity,"Server error!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    if(response.isSuccessful)
                    {
                        Toast.makeText(this@ChangePassActivity,"Password changed!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this@ChangePassActivity,"Check your inputs!", Toast.LENGTH_SHORT).show()
                    }

                }


            })

    }

}
