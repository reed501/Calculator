package com.reedschrier.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticsPageFragmentOutput extends Fragment
{

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater
                    .inflate(R.layout.fragment_statistics_page_output, container, false);
        return rootView;
    }
}
