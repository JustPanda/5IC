package com.example.massi.flatdice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;


public class FragmentUno extends Fragment{

    private TextView t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_uno, container, false);


       /* // Create a new ImageView
        ImageView imageView=(ImageView)v.findViewById(R.id.uno);
        // Parse the SVG file from the resource
        SVG svg = SVGParser.getSVGFromResource(getResources(),R.raw.android);
        // Get a drawable from the parsed SVG and set it as the drawable for the ImageView
        imageView.setImageDrawable(svg.createPictureDrawable());
        // Set the ImageView as the content view for the Activity

*/

        return v;
    }
}
