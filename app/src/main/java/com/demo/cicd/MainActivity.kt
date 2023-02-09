package com.demo.cicd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.cicd.databinding.ActivityMainBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCenter.start(application,"key",Analytics::class.java,Crashes::class.java)

        binding.calculateButton.setOnClickListener {
            Crashes.generateTestCrash()
        }
    }
}
