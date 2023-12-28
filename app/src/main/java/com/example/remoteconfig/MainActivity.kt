package com.example.remoteconfig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class MainActivity : AppCompatActivity() {
    private val remoteConfig: FirebaseRemoteConfig by lazy {
        com.google.firebase.Firebase.remoteConfig
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val message = remoteConfig.getString("Message")
                    updateMessage(message)
                } else {
                    Toast.makeText(this, "Fetch Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateMessage(message: String) {
        val welcomeTextView = findViewById<TextView>(R.id.text)
        welcomeTextView.text = message
    }
}
