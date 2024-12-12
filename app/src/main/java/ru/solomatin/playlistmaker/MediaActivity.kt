package ru.solomatin.playlistmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MediaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // Определение макета на основе текущей темы

        val selectedLayout = determineThemeLayout()
        super.onCreate(savedInstanceState)
        setContentView(selectedLayout)
    }

    // Метод для определения макета активности в зависимости от текущей темы

    private fun determineThemeLayout(): Int {

        val darkModeEnabled = getSharedPreferences("AppPrefs", MODE_PRIVATE).getBoolean("DARK_MODE", false)
        return if (darkModeEnabled) R.layout.media_dark else R.layout.media_light
    }
}
