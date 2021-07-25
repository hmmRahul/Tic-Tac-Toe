package com.hmmRahul.tictactoe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmmRahul.tictactoe.databinding.FragWinnerDeclarationBinding;


public class WinnerDeclarationFrag extends Fragment {


    FragWinnerDeclarationBinding fragmentBlankBinding;
    public WinnerDeclarationFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBlankBinding = FragWinnerDeclarationBinding.inflate(inflater,container,false);
        MainActivity mainActivity = (MainActivity) getActivity();
        String winner = mainActivity.getWinner();
        fragmentBlankBinding.textView.setText(winner);

        if(winner.equals("BLUE WINS"))
        {
            fragmentBlankBinding.fragmentMainLayout.setBackgroundColor(getResources().getColor(R.color.transparentP2));
        }
        else if(winner.equals("RED WINS"))
        {
            fragmentBlankBinding.fragmentMainLayout.setBackgroundColor(getResources().getColor(R.color.transparentP1));
        }
        else
        {
            fragmentBlankBinding.fragmentMainLayout.setBackgroundColor(getResources().getColor(R.color.transparentblack));
        }

        return fragmentBlankBinding.getRoot();

    }
}