package com.reedschrier.calculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class Statistics extends AppCompatActivity
{

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        final ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled (int position, float positionOffset,
                                        int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected (int position)
            {
                if (position == 1)
                {
                    StatisticsPageFragmentInput inPage = (StatisticsPageFragmentInput) pagerAdapter.getItem(0);
                    updateData(inPage.getData());
                }
            }

            @Override
            public void onPageScrollStateChanged (int state)
            {
            }

        });

    }

    @Override
    public void onBackPressed ()
    {
        StatisticsPageFragmentInput.clear();
        super.onBackPressed();
    }

    private void updateData (double[] data)
    {
        TextView vMean = (TextView) findViewById(R.id.meanOut);
        TextView vMedian = (TextView) findViewById(R.id.medianOut);
        TextView vMode = (TextView) findViewById(R.id.modeOut);
        if (data == null || (data.length == 1 && data[0] == 0))
        {
        }
        else
        {
            Arrays.sort(data);
            int size = data.length;

            double mean;
            double sum = 0.0;

            String mode = "none";
            double max = 0.0;
            double temp;
            int freq = 0;
            int tempFreq;

            double median = data[size / 2];

            for (int i = 0; i < size; i++)
            {
                sum += data[i];

                temp = data[i];
                tempFreq = 1;
                for (int j = i + 1; j < size; j++)
                    if (data[j] == temp)
                        tempFreq++;
                if (tempFreq > freq)
                {
                    max = temp;
                    freq = tempFreq;
                }
            }

            mean = sum / size;

            if (freq > 1)
                mode = String.valueOf(max);

            vMean.setText(String.valueOf(mean));
            vMedian.setText(String.valueOf(median));
            vMode.setText(mode);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter
    {
        public ScreenSlidePagerAdapter (FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem (int position)
        {
            Fragment page;
            if (position == 0)
                page = new StatisticsPageFragmentInput();
            else if (position == 1)
                page = new StatisticsPageFragmentOutput();
            else
                return null;
            return page;
        }

        @Override
        public int getCount ()
        {
            return 2;
        }
    }
}
