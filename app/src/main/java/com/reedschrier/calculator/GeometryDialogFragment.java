package com.reedschrier.calculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GeometryDialogFragment extends DialogFragment
{

    public GeometryDialogFragment ()
    {

    }

    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        final Dialog dialog = new Dialog(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        if (getTag().equals("vSphere"))
            dialog.setContentView(inflater.inflate(R.layout.fragment_v_sphere_dialog, null));
        else if (getTag().equals("vCone"))
            dialog.setContentView(inflater.inflate(R.layout.fragment_v_cone_dialog, null));
        else if (getTag().equals("vCylinder"))
            dialog.setContentView(inflater.inflate(R.layout.fragment_v_cylinder_dialog, null));
        final Button calc = (Button) dialog.findViewById(R.id.calc);
        calc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                calculate(dialog);
            }
        });
        Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private void calculate (Dialog dialog)
    {
        TextView output = (TextView) dialog.findViewById(R.id.output);
        double num;
        String result = "";
        if (getTag().equals("vSphere"))
        {
            EditText radius = (EditText) dialog.findViewById(R.id.input1);
            if (!radius.getText().toString().equals(""))
            {
                num = Math.pow(Double.parseDouble(radius.getText().toString()), 3) * 4 / 3;
                result = convert(num);
            }
        }
        else if (getTag().equals("vCone"))
        {
            EditText radius = (EditText) dialog.findViewById(R.id.input1);
            EditText height = (EditText) dialog.findViewById(R.id.input2);
            if (!radius.getText().toString().equals("") &&
                !height.getText().toString().equals(""))
            {
                num = Math.pow(Double.parseDouble(radius.getText().toString()), 2) *
                      Double.parseDouble(height.getText().toString()) / 3;
                result = convert(num);
            }
        }
        else if (getTag().equals("vCylinder"))
        {
            EditText radius = (EditText) dialog.findViewById(R.id.input1);
            EditText height = (EditText) dialog.findViewById(R.id.input2);
            if (!radius.getText().toString().equals("") &&
                !height.getText().toString().equals(""))
            {
                num = Math.pow(Double.parseDouble(radius.getText().toString()), 2) *
                      Double.parseDouble(height.getText().toString());
                result = convert(num);
            }
        }
        if (!result.equals(""))
            output.setText(result);
    }

    private String convert (double num)
    {
        String result;
        if (num == (int) num)
            result = String.valueOf((int) num);
        else
            result = String.valueOf(num);
        if (result.length() > 7)
            result = result.substring(0, 7);
        result += "\u03c0";
        return result;
    }
}
