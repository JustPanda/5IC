package com.example.greg.flatdice;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DiceFragment extends Fragment
{
    private int num;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num=getArguments().getInt("num", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.dice_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int draw=0;
        switch(num)
        {
            case 1: draw=R.drawable.one; break;
            case 2: draw=R.drawable.two; break;
            case 3: draw=R.drawable.three; break;
            case 4: draw=R.drawable.four; break;
            case 5: draw=R.drawable.five; break;
            case 6: draw=R.drawable.six; break;
        }
        view.findViewById(R.id.diceContainer).setBackgroundResource(draw);
    }

    public static DiceFragment newInstance(int num)
    {
        DiceFragment diceFragment=new DiceFragment();
        Bundle args=new Bundle();
        args.putInt("num", num);
        diceFragment.setArguments(args);
        return diceFragment;
    }
}
