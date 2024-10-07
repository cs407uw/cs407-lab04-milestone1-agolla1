package com.cs407.lab4_milestone1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val TAG = "MyActivity"
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private suspend fun mockFileDownloader() {
        withContext(Dispatchers.Main) {
            val startBut = findViewById<Button>(R.id.start)
            startBut.text = getString(R.string.downloading)
        }

        for (downloadProgress in 0..100 step 10) {
            Log.d(TAG, "Download Progress $downloadProgress%")
            withContext(Dispatchers.Main) {
                val progText = findViewById<TextView>(R.id.progressText)
                progText.text = "Download Progress $downloadProgress%"
            }
            delay(1000)
        }


        withContext(Dispatchers.Main) {
            val startBut = findViewById<Button>(R.id.start)
            val progText = findViewById<TextView>(R.id.progressText)
            startBut.text = getString(R.string.start)
            progText.text = getString(R.string.complete)
        }
    }

    fun startDownload(view: View) {
        job = CoroutineScope(Dispatchers.Default).launch {
            mockFileDownloader()
        }
    }

    fun stopDownload(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val startBut = findViewById<Button>(R.id.start)
            val progText = findViewById<TextView>(R.id.progressText)
            startBut.text = getString(R.string.start)
            progText.text = getString(R.string.cancel)
        }
        job?.cancel()
    }
}