package com.example.aprenderinclusivo2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.aprenderinclusivo2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Iniciar objeto de interconexão
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //A usar DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }
}