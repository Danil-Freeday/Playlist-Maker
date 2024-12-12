package ru.solomatin.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {

        // Определение макета в зависимости от темы

        val selectedLayout = determineLayoutBasedOnTheme()
        super.onCreate(savedInstanceState)
        setContentView(selectedLayout)

        supportActionBar?.hide()

        // Настройка переключателя темы

        val themeToggleSwitch = findViewById<Switch>(R.id.themeSwitch)
        val preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val darkModeEnabled = preferences.getBoolean("DARK_MODE", false)


        themeToggleSwitch.isChecked = darkModeEnabled

        // Слушатель изменения состояния переключателя

        themeToggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            persistThemePreference(isChecked)
            recreate()
        }

        // Отправка локального широковещательного сообщения об изменении темы

        val themeUpdateIntent = Intent("com.example.UPDATE_THEME")
        LocalBroadcastManager.getInstance(this).sendBroadcast(themeUpdateIntent)


        initializeUIComponents()
    }

    @SuppressLint("WrongViewCast")
    private fun initializeUIComponents() {

        // Кнопка возврата

        val returnButton = findViewById<ImageButton>(R.id.backButton)
        returnButton.setOnClickListener {
            finish()
        }

        // Кнопка для поделиться приложением

        val appShareButton = findViewById<Button>(R.id.share)
        appShareButton.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Группа ИС-211")
            }

            startActivity(Intent.createChooser(sharingIntent, "Поделиться приложением через"))
        }

        // Кнопка для отправки сообщения в поддержку

        val supportContactButton = findViewById<Button>(R.id.sendToSupport)
        supportContactButton.setOnClickListener {
            val developerEmail = "danil-solomatin03@mail.ru"
            val emailSubject = "Сообщение разработчикам Playlist Maker"
            val emailBody = "Работает!"

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$developerEmail")
                putExtra(Intent.EXTRA_TEXT, emailSubject)
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }

            // Проверка наличия email-клиента перед отправкой

            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                Toast.makeText(this, "Не найден почтовый клиент", Toast.LENGTH_SHORT).show()
            }
        }

        // Кнопка для просмотра пользовательского соглашения

        val agreementViewButton = findViewById<Button>(R.id.viewUserAgreement)
        agreementViewButton.setOnClickListener {
            val agreementPageUri = Uri.parse("https://vivt.ru")
            val openBrowserIntent = Intent(Intent.ACTION_VIEW, agreementPageUri)
            startActivity(openBrowserIntent)
        }
    }

    // Определение макета на основе темы (темная или светлая)

    private fun determineLayoutBasedOnTheme(): Int {
        val darkModeEnabled = getSharedPreferences("AppPrefs", MODE_PRIVATE).getBoolean("DARK_MODE", false)
        return if (darkModeEnabled) R.layout.settings_dark else R.layout.settings_light
    }

    // Сохранение предпочтений темы

    private fun persistThemePreference(darkModeEnabled: Boolean) {
        val editor = getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
        editor.putBoolean("DARK_MODE", darkModeEnabled)
        editor.apply()
    }
}
