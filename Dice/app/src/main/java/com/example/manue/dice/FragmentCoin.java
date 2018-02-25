package com.example.manue.dice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by manue on 25/02/2018.
 */

public class FragmentCoin extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Random r = new Random();
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView tv = (ImageView)v.findViewById(R.id.MainFragmentImageView);
        LinearLayout fl = (LinearLayout) v.findViewById(R.id.MainFragmentLinearLayout);
        fl.setBackground(getResources().getDrawable(R.color.yellow));
        int out= r.nextInt(2);
        if(out==1)
        {
            tv.setImageResource(R.drawable.testa);
        }
        else if(out==0)
        {
            tv.setImageResource(R.drawable.croce);
        }
        return v;
    }
}
