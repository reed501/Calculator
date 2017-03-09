package com.reedschrier.calculator;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Geometry extends AppCompatActivity
{

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry);
    }

    public void sphere (View view)
    {
        DialogFragment frag = new GeometryDialogFragment();
        frag.show(getSupportFragmentManager(), "vSphere");
    }

    public void cone (View view)
    {
        DialogFragment frag = new GeometryDialogFragment();
        frag.show(getSupportFragmentManager(), "vCone");
    }

    public void cylinder (View view)
    {
        DialogFragment frag = new GeometryDialogFragment();
        frag.show(getSupportFragmentManager(), "vCylinder");
    }
}
