package com.example.project1.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.Patterns
import android.view.Menu
import android.widget.*
import com.example.project1.R
import com.example.project1.api.RetrofitClient
import com.example.project1.model.DefaultResponse
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddRestActivity : AppCompatActivity() {
    private lateinit var name:EditText
    private lateinit var type:EditText
    private lateinit var schedule:EditText
    private lateinit var contactInfo:EditText
    private lateinit var locationX:EditText
    private lateinit var locationY:EditText
    private lateinit var staticSpinner: Spinner
    private lateinit var btnConfirmRest: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.project1.R.layout.activity_add_rest)
        staticSpinner = findViewById(R.id.static_spinner)
        btnConfirmRest = findViewById(R.id.btn_confirmRest)
        name = findViewById(R.id.input_addName)
        type = findViewById(R.id.input_addType)
        schedule = findViewById(R.id.input_addSchedule)
        contactInfo = findViewById(R.id.input_addContact)
        locationX = findViewById(R.id.input_addX)
        locationY = findViewById(R.id.input_addY)

        val staticAdapter = ArrayAdapter
            .createFromResource(
                this, com.example.project1.R.array.spin_restCost,
                R.layout.spinner_item
            )

        // Specify the layout to use when the list of choices appears
        staticAdapter
            .setDropDownViewResource(R.layout.spinner_dropdown_item)

        // Apply the adapter to the spinner
        staticSpinner.adapter = staticAdapter
        btnConfirmRest.setOnClickListener {
            validateRest()
        }



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.example.project1.R.menu.details_activity, menu)
        return true
    }

    fun validateRest()
    {
        val restName = name.text.toString()
        val restType = type.text.toString()
        val restSchedule = schedule.text.toString()
        val restContact = contactInfo.text.toString()
        val restCost = staticSpinner.selectedItemPosition
        val x = locationX.text.toString()
        val y = locationY.text.toString()
        val img = ArrayList<String>()
        if(checkEmpty())
        {
            val hash:HashMap<String,Any> = hashMapOf()
            hash.set("name",restName)
            hash.set("food",restType)
            hash.set("contactInfo",restContact)
            hash.set("schedule",restSchedule)
            hash.set("cost",restCost)
            hash.set("x",x)
            hash.set("y",y)
            hash.set("images",img)

            RetrofitClient.instance.addRest(hash)
                .enqueue(object: Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(this@AddRestActivity,"User not created", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(this@AddRestActivity,"Restaurant added!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@AddRestActivity,"Input error!", Toast.LENGTH_SHORT).show()
                        }

                    }


                })
        }
        else
        {
            Toast.makeText(this,"Check your credentials", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkEmpty(): Boolean {
        val restName = name.text.toString()
        val restType = type.text.toString()
        val restSchedule = schedule.text.toString()
        val restContact = contactInfo.text.toString()
        val restCost = staticSpinner.selectedItemPosition
        val x = locationX.text.toString()
        val y = locationY.text.toString()

        return restName.isNotEmpty() && restType.isNotEmpty() && restSchedule.isNotEmpty() && restContact.isNotEmpty() && x.isNotEmpty() && y.isNotEmpty()

    }
}
