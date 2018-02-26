package com.example.filippofantinato.flatdice

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.Gravity


class MainActivity : AppCompatActivity()
{
    private val MOVE_DEFAULT_TIME: Long = 500
    private val EXIT_DEFAULT_TIME: Long = 250
    private val MIN_DISTANCE: Float = 150.0f
    private var x1: Float = 0f
    private var x2: Float = 0f
    private var y1: Float = 0f
    private var y2: Float = 0f
    private var indexToImage: IntArray = intArrayOf(
                R.drawable.one,
                R.drawable.two,
                R.drawable.three,
                R.drawable.four,
                R.drawable.fifth,
                R.drawable.six)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm: FragmentTransaction=fragmentManager.beginTransaction()
        fm.replace(R.id.frame, DiceFace.newInstance(indexToImage[0]) as Fragment)
        fm.commit()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        when(event!!.action)
        {
            MotionEvent.ACTION_DOWN ->
            {
                x1=event.x
                y1=event.y
            }
            MotionEvent.ACTION_UP ->
            {
                x2=event.x
                y2=event.y
                val deltaX: Float = x2-x1
                val deltaY: Float = y2-y1

                if(Math.abs(deltaX)>MIN_DISTANCE)
                {
                    if(x2>x1)
                    {
                        changeFragment(Gravity.END, Gravity.START)
                    }else{
                        changeFragment(Gravity.START, Gravity.END)
                    }
                }else{
                    if(Math.abs(deltaY)>MIN_DISTANCE)
                    {
                        if(y2>y1)
                        {
                            changeFragment(Gravity.BOTTOM, Gravity.TOP)
                        }else{
                            changeFragment(Gravity.TOP, Gravity.BOTTOM)
                        }
                    }
                }
            }
        }
        return true
    }

    private fun changeFragment(from: Int, to: Int)
    {
        val fm: FragmentTransaction = fragmentManager.beginTransaction()
        val actualFragment = fragmentManager.findFragmentById(R.id.frame)
        val nextFragment: Fragment = DiceFace.newInstance(indexToImage[(Math.random()*6).toInt()])
        val exitAnimation = Slide(from)
        val enterAnimation = Slide(to)
        enterAnimation.duration=MOVE_DEFAULT_TIME
        exitAnimation.duration=EXIT_DEFAULT_TIME
        nextFragment.enterTransition=enterAnimation
        actualFragment.exitTransition=exitAnimation
        fm.replace(R.id.frame, nextFragment)
        fm.commit()
    }
}
