package com.example.filippofantinato.flatdice

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var diceFace = (DiceFace())
        var bt = fragmentManager.beginTransaction()
        bt.add(R.id.frame, diceFace as Fragment)
        bt.commit()
    }
}
