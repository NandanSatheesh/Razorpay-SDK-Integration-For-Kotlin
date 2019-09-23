package com.example.razorpay_sdk_checkout

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.razorpay_sdk_checkout.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        Checkout.preload(applicationContext)

        binding.startPaymentFlowButton.setOnClickListener {
            startCheckoutFlow()
        }
    }

    private fun startCheckoutFlow() {

        val activity: Activity = this

        val checkout = Checkout()

        try {
            val checkoutPaymentOptions = JSONObject()

            checkoutPaymentOptions.put("name", "Company Name")
            checkoutPaymentOptions.put("description", "Amount Description")
            //You can omit the image option to fetch the image from dashboard
            checkoutPaymentOptions.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            checkoutPaymentOptions.put("currency", "INR")
            checkoutPaymentOptions.put("amount", "100")

            val prefillEmailAndContact = JSONObject()
            prefillEmailAndContact.put("email", "test@test.com")
            prefillEmailAndContact.put("contact", "9876543210")

            checkoutPaymentOptions.put("prefill",prefillEmailAndContact)

            checkout.open(activity, checkoutPaymentOptions)
        } catch (e: Exception) {
            Toast.makeText(
                activity,
                "Error in payment: " + e.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(
            this,
            "Payment Failure. Error Code - $p0. Message - $p1",
            Toast.LENGTH_LONG
        ).show()

    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(
            this,
            "Payment Success. Transaction ID - $p0",
            Toast.LENGTH_LONG
        ).show()
    }
}
