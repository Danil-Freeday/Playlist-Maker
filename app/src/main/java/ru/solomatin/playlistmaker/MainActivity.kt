package ru.solomatin.playlistmaker

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // Определение макета на основе темы

        val selectedLayout = determineThemeLayout()
        super.onCreate(savedInstanceState)
        setContentView(selectedLayout)

        // Регистрация локального широковещательного приемника для обновления темы

        val themeUpdateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                setContentView(determineThemeLayout())
                initializeUI()
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(themeUpdateReceiver, IntentFilter("com.example.UPDATE_THEME"))

        // Настройка интерфейса

        initializeUI()
    }

    // Метод для определения макета в зависимости от темы

        @SuppressLint("SuspiciousIndentation")
        private fun determineThemeLayout(): Int {
            val isDarkModeEnabled = getSharedPreferences("AppPrefs", MODE_PRIVATE).getBoolean("DARK_MODE", false)
                return if (isDarkModeEnabled) R.layout.main_dark else R.layout.main_light
    }

         // Метод для настройки интерфейса

          private fun initializeUI() {
             supportActionBar?.hide()

        // Настройка кнопки перехода к настройкам

        val settingsButton = findViewById<Button>(R.id.button)
        settingsButton.setOnClickListener {
            val settingsIntent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        // Настройка кнопки перехода к медиа

        val mediaButton = findViewById<Button>(R.id.button2)
        mediaButton.setOnClickListener {
            val mediaIntent = Intent(this@MainActivity, MediaActivity::class.java)
            startActivity(mediaIntent)
        }

        // Настройка кнопки перехода к поиску

        val searchButton = findViewById<Button>(R.id.button3)
        searchButton.setOnClickListener {
            val searchIntent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(searchIntent)
        }
    }

}
