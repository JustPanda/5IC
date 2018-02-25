package com.example.davide.flatdice;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by davide on 20/02/18.
 */

public class Face6 extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.six_face, container, false);
        View tv = v.findViewById(R.id.text_view6);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        tv.setBackgroundColor(color);

        // Inflate the layout for this fragment
        return v;
    }
}
