package com.reedschrier.calculator;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class StatisticsPageFragmentInput extends Fragment
{

    private static int count = 0;

    private static double[] data;
    private ViewGroup rootView;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState)
    {
        rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_statistics_page_input, container, false);
        //Add the first field.
        add().requestFocus();
        return rootView;
    }

    private EditText add ()
    {
        //Build a new EditText to add to the screen
        EditText t = new EditText(getContext());
        t.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        t.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t.setTextSize(32);
        t.setPadding(16, 16, 16, 16);
        t.setHint("enter data");
        rootView.addView(t, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //Checks for when this field is edited
        t.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction (TextView v, int actionId, KeyEvent event)
            {
                //If the next button is hit, and it's got numbers, add the next field and increment.
                if (actionId == EditorInfo.IME_ACTION_NEXT && !v.getText().toString().equals("") &&
                    !v.getText().toString().equals("."))
                {
                    add().requestFocus();
                    count++;
                }
                //Check and save the data, then stop anything from happening if it's invalid.
                saveData();
                return true;
            }
        });
        return t;
    }

    public void saveData ()
    {
        //include the last field even if they didn't hit enter.
        //Currently disabled as data doesn't update until next is hit.
        /*boolean include = false;
        EditText l = (EditText) v.getChildAt(count);
        if (l != null && l.getText() != null && !l.getText().toString().equals("") &&
            !l.getText().toString().equals("."))
            include = true;*/
        //Clear any empty fields
        int j = 0;
        while (j < count)
        {
            EditText r = (EditText) rootView.getChildAt(j);
            if (r.getText().toString().equals("") || r.getText().toString().equals("."))
            {
                rootView.removeView(r);
                count--;
            }
            else
                j++;
        }
        //If there's still data, fill the array
        if (count > 0)
        {
            /*if (include)
                data = new double[count + 1];
            else*/
            data = new double[count];
            for (int i = 0; i < data.length; i++)
            {
                EditText t = (EditText) rootView.getChildAt(i);
                data[i] = Double.parseDouble(t.getText().toString());
            }
        }
        //If there's no fields, return an array of just zero
        else
            data = new double[] {0.0};
    }

    public double[] getData ()
    {
        return data;
    }

    public static void clear()
    {
        count = 0;
        data = null;
    }
}
