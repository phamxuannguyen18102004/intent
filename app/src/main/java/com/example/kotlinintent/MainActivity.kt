package com.example.kotlinintent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var mapData: EditText
    private lateinit var emailAdd: EditText
    private lateinit var emailSubject: EditText
    private lateinit var emailCon: EditText
    private lateinit var btnMap: Button
    private lateinit var btnEmail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapData = findViewById(R.id.map_data)
        btnMap = findViewById(R.id.btn_map)
        emailAdd = findViewById(R.id.email_address)
        emailSubject = findViewById(R.id.email_subject)
        emailCon = findViewById(R.id.email_content)
        btnEmail = findViewById(R.id.btn_email)

        btnMap.setOnClickListener {
            val mapQuery = mapData.text.toString().trim()
            if (mapQuery.isNotEmpty()) {
                val mapUri = Uri.parse("geo:0,0?q=$mapQuery")
                showMap(mapUri)
            } else {
                Toast.makeText(this, "Enter a location to search!", Toast.LENGTH_SHORT).show()
            }
        }

        btnEmail.setOnClickListener {
            val addresses = arrayOf(emailAdd.text.toString().trim())
            val subject = emailSubject.text.toString().trim()
            val content = emailCon.text.toString().trim()
            if (addresses.isNotEmpty() && subject.isNotEmpty() && content.isNotEmpty()) {
                composeEmail(addresses, subject, content)
            } else {
                Toast.makeText(this, "Please fill all email fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun composeEmail(addresses: Array<String>, subject: String, content: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, content)
        }
        startActivity(intent)
    }

    private fun showMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geoLocation
        }
//        if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}
