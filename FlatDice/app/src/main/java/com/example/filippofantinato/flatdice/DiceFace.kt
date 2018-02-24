package com.example.filippofantinato.flatdice

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class DiceFace : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater!!.inflate(R.layout.dice_face, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image: ImageView = view!!.findViewById(R.id.image) as ImageView
        val path: Int = arguments["number"] as Int
//        image.setImageResource(path)
    }

    companion object {

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