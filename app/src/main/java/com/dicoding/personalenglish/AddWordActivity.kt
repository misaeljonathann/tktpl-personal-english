package com.dicoding.personalenglish

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddWordActivity: AppCompatActivity() {

    companion object {
        val EXTRA_WORD = "com.misael.personalenglish.EXTRA_WORD"
//        val EXTRA_TIME = "com.dicoding.mvvmlab.EXTRA_TIME"
    }

    private lateinit var editTextWord: EditText

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)

        editTextWord = findViewById(R.id.edit_text_word)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_close)
    }

    fun saveWord() {
        var word: String = editTextWord.text.toString()

        if (word.trim().isEmpty()) {
            Toast.makeText(this, "Jangan kosong tong", Toast.LENGTH_SHORT).show()
            return
        }

        val data: Intent = Intent()
        data.putExtra(EXTRA_WORD, word)

        setResult(RESULT_OK, data)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_word_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_word -> {
                saveWord()
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}