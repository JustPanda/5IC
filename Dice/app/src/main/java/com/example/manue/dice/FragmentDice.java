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

public class FragmentDice extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        Random r = new Random();
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView iv = (ImageView) v.findViewById(R.id.MainFragmentImageView);
        LinearLayout fl = (LinearLayout) v.findViewById(R.id.MainFragmentLinearLayout);
        fl.setBackground(getResources().getDrawable(R.color.orange));
        int out=0;
        switch(1 + r.nextInt(6))
        {
            case 1:
                iv.setImageResource(R.drawable.dice1);
                break;
            case 2:
                iv.setImageResource(R.drawable.dice2);
                break;
            case 3:
                iv.setImageResource(R.drawable.dice3);
                break;
            case 4:
                iv.setImageResource(R.drawable.dice4);
                break;
            case 5:
                iv.setImageResource(R.drawable.dice5);
                break;
            case 6:
                iv.setImageResource(R.drawable.dice6);
                break;
        }
        return v;
    }
}

