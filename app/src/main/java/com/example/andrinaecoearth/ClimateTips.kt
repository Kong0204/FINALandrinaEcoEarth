package com.example.ecoearth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.andrinaecoearth.databinding.LayoutClimateTipsBinding


class ClimateTips :  AppCompatActivity() {
    private lateinit var binding: LayoutClimateTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutClimateTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tipsVideoView1.setOnClickListener { tlink1() }
        binding.tipsVideoView2.setOnClickListener { tlink2() }
    }


    private fun tlink1(){
        val url = "https://youtu.be/--tawdcPi4w"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun tlink2(){
        val url = "https://youtu.be/zzzMBQH3ZLY"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}