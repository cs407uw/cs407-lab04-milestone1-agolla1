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
            val startButton = findViewById<Button>(R.id.start)
            startButton.text = getString(R.string.download)
        }

        for (downloadProgress in 0..100 step 10) {
            Log.d(TAG, "Download Progress $downloadProgress%")
            withContext(Dispatchers.Main) {
                val progressText = findViewById<TextView>(R.id.progressText)
                progressText.text = "Download Progress $downloadProgress%"
            }
            delay(1000)
        }


        withContext(Dispatchers.Main) {
            val startButton = findViewById<Button>(R.id.start)
            val progressText = findViewById<TextView>(R.id.progressText)
            startButton.text = getString(R.string.start)
            progressText.text = getString(R.string.complete)
        }
    }

    fun startDownload(view: View) {
        job = CoroutineScope(Dispatchers.Default).launch {
            mockFileDownloader()
        }
    }

    fun stopDownload(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val startButton = findViewById<Button>(R.id.start)
            val progressText = findViewById<TextView>(R.id.progressText)
            startButton.text = getString(R.string.start)
            progressText.text = getString(R.string.cancelled)
        }
        job?.cancel()
    }
}