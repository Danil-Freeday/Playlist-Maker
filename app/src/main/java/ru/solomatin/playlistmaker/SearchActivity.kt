package ru.solomatin.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    // Переменная для хранения текста поиска
    private var currentSearchText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        // Определение макета в зависимости от темы

        val selectedLayout = determineThemeLayout()
        super.onCreate(savedInstanceState)
        setContentView(selectedLayout)

        val searchInputField = findViewById<EditText>(R.id.searchInput)
        val clearSearchButton = findViewById<ImageButton>(R.id.clearButton)

        searchInputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentSearchText = s.toString()

                clearSearchButton.visibility = if (s.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Настройка кнопки очистки поля ввода

        clearSearchButton.setOnClickListener {
            searchInputField.text.clear()
            dismissKeyboard(searchInputField)
        }

        // Настройка кнопки возврата

        val returnButton = findViewById<ImageButton>(R.id.backButton)
        returnButton.setOnClickListener {
            finish()
        }
        supportActionBar?.hide()
    }

    // Метод для определения макета на основе текущей темы

    private fun determineThemeLayout(): Int {
        val isDarkModeEnabled = getSharedPreferences("AppPrefs", MODE_PRIVATE).getBoolean("DARK_MODE", false)
        return if (isDarkModeEnabled) R.layout.search_dark else R.layout.search_light
    }

    // Метод для скрытия клавиатуры

    private fun dismissKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // Сохранение состояния текста поиска при изменении конфигурации

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SEARCH_TEXT", currentSearchText)
    }

    // Восстановление текста поиска из сохраненного состояния

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredSearchText = savedInstanceState.getString("SEARCH_TEXT", "")
        findViewById<EditText>(R.id.searchInput).setText(restoredSearchText)
    }
}
