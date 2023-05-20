package com.example.ecoearth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.andrinaecoearth.R
import com.example.andrinaecoearth.databinding.LayoutDonationClimateBinding

class DonationClimate : AppCompatActivity() {
    //private lateinit var binding: LayoutDonationClimateBinding
    //private var binding = LayoutDonationClimateBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_donation_climate)

        val donationLink1 = "https://my.rotary.org/en/donate"
        val donationLink2 =
            "https://hss.mercy.org.my/mercy-start-0.0.1-SNAPSHOT/recent#!#recent"
        val donationLink3 = "https://donate.seasia.greenpeace.org/malaysia/donation-page/#"

        //get reference to the donation buttons
        val donationButton1 = findViewById<Button>(R.id.donationChoice1)
        val donationButton2 = findViewById<Button>(R.id.donationChoice2)
        val donationButton3 = findViewById<Button>(R.id.donationChoice3)


        //set on click listener for buttons
        donationButton1.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(donationLink1)
            startActivity(intent)
        }
        donationButton2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(donationLink2)
            startActivity(intent)
        }
        donationButton3.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(donationLink3)
            startActivity(intent)
        }

    }

}