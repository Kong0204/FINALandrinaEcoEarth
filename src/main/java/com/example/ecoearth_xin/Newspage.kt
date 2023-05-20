package com.example.ecoearth_xin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ecoearth_xin.databinding.ActivityHomepageBinding
import com.example.ecoearth_xin.databinding.ActivityNewspageBinding

class Newspage : AppCompatActivity() {

    private lateinit var binding: ActivityNewspageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewspageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.new1.setOnClickListener{nlink1()}
        binding.new2.setOnClickListener{nlink2()}
        binding.new3.setOnClickListener{nlink3()}


    }
    private fun nlink1(){
        val url = "https://climate.nasa.gov/news/3246/nasa-says-2022-fifth-warmest-year-on-record-warming-trend-continues/"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun nlink2(){
        val url = "https://www.nst.com.my/news/nation/2023/03/887295/most-malaysians-not-sure-what-they-can-do-tackle-climate-change-expert#:~:text=According%20to%20the%20World%20Bank,degrees%20Celsius%20by%20the%202090s."
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun nlink3(){
        val url = "https://www.bbc.com/news/science-environment-24021772"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}