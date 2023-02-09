package com.demo.cicd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.demo.cicd.databinding.ActivityMainBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCenter.start(application,"c22cbf25-c462-412c-8d7c-4879139e6cda",Analytics::class.java,Crashes::class.java)


        binding.calculateButton.setOnClickListener {

            try {
                val interestRate = binding.interestEditText.text.toString().toFloat()
                val currentAge = binding.ageEditText.text.toString().toInt()
                val retirementAge = binding.retirementEditText.text.toString().toInt()
                val monthly = binding.monthlySavingsEditText.text.toString().toFloat()
                val current = binding.currentEditText.text.toString().toFloat()

                val properties:HashMap<String, String> = HashMap<String, String>()
                properties.put("interest_rate", interestRate.toString())
                properties.put("current_age", currentAge.toString())
                properties.put("retirement_age", retirementAge.toString())
                properties.put("monthly_savings", monthly.toString())
                properties.put("current_savings", current.toString())

                if (interestRate <= 0) {
                    Analytics.trackEvent("wrong_interest_rate", properties)
                }
                if (retirementAge <= currentAge) {
                    Analytics.trackEvent("wrong_age", properties)
                }

                val futureSavings = calculateRetirement(interestRate, current, monthly, (retirementAge - currentAge)*12)
                Log.i("MySavings are ","${futureSavings}")
            } catch (ex: Exception) {
                Analytics.trackEvent(ex.message)
            }
        }
    }

    fun calculateRetirement(interestRate: Float, currentSavings: Float, monthly: Float, numMonths: Int): Float {
        var futureSavings = currentSavings * (1+(interestRate/100/12)).pow(numMonths)

        for (i in 1..numMonths) {
            futureSavings += monthly * (1+(interestRate/100/12)).pow(i)
        }

        return  futureSavings
    }
}
