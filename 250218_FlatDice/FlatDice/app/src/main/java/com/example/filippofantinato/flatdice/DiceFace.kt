package com.example.filippofantinato.flatdice

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dice_face.*

class DiceFace : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater!!.inflate(R.layout.dice_face, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        image.setImageResource(arguments["number"] as Int)
    }

    companion object
    {

        fun newInstance(number: Int): DiceFace
        {
            var face = DiceFace()
            var args = Bundle()
            args.putInt("number", number)
            face.arguments=args
            return face
        }

    }
}