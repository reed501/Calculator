package com.reedschrier.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity
{

    private boolean num = false; //true if last entered item is a number
    private boolean function = false;
    private boolean dec = false; //true if current value has a decimal point
    private boolean neg = false; //true if current value is false

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] screens = getResources().getStringArray(R.array.screens);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ListView drawer = (ListView) findViewById(R.id.drawer);

        drawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, screens));
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent();
                if (position == 0)
                    intent = new Intent(getApplicationContext(), Geometry.class);
                else if (position == 1)
                    intent = new Intent(getApplicationContext(), Statistics.class);
                drawerLayout.closeDrawer(drawer);
                startActivity(intent);
            }
        });
    }

    public void number (View view)
    {
        TextView display = (TextView) findViewById(R.id.display1);
        switch (view.getId())
        {
            case R.id.n0:
                display.append("0");
                break;
            case R.id.n1:
                display.append("1");
                break;
            case R.id.n2:
                display.append("2");
                break;
            case R.id.n3:
                display.append("3");
                break;
            case R.id.n4:
                display.append("4");
                break;
            case R.id.n5:
                display.append("5");
                break;
            case R.id.n6:
                display.append("6");
                break;
            case R.id.n7:
                display.append("7");
                break;
            case R.id.n8:
                display.append("8");
                break;
            case R.id.n9:
                display.append("9");
                break;
        }
        num = true;
        TextView display2 = (TextView) findViewById(R.id.display2);
        if (function)
            display2.setText(format(calculate(display.getText().toString())));
    }

    public void dec (View view)
    {
        if (!dec)
        {
            TextView display = (TextView) findViewById(R.id.display1);
            display.append(".");
            dec = true;
        }
    }

    public void func (View view)
    {
        TextView display = (TextView) findViewById(R.id.display1);
        if (num)
        {
            num = false;
            dec = false;
            function = true;
            neg = false;
            switch (view.getId())
            {
                case R.id.add:
                    display.append("+");
                    break;
                case R.id.sub:
                    display.append("-");
                    break;
                case R.id.mult:
                    display.append("*");
                    break;
                case R.id.div:
                    display.append("/");
                    break;
            }
        }
        else if (!num && !neg && view.getId() == R.id.sub)
        {
            display.append("-");
            neg = true;
        }
    }

    public void exponent (View view)
    {

    }

    public void equals (View view)
    {
        TextView display = (TextView) findViewById(R.id.display1);
        TextView displayResult = (TextView) findViewById(R.id.display2);
        if (!displayResult.getText().toString().equals(""))
        {
            display.setText(displayResult.getText().toString());
            displayResult.setText("");
            num = true;
            function = false;
        }
    }

    public void clear (View view)
    {
        num = false;
        dec = false;
        function = false;
        neg = false;
        TextView display1 = (TextView) findViewById(R.id.display1);
        TextView display2 = (TextView) findViewById(R.id.display2);
        display1.setText("");
        display2.setText("");
    }

    public void delete (View view)
    {
        TextView display = (TextView) findViewById(R.id.display1);
        TextView display2 = (TextView) findViewById(R.id.display2);
        if (display.getText().length() == 0)
            return;
        String text = display.getText().toString();
        char last = text.charAt(text.length() - 1);
        if (last == '.')
            dec = false;
        else if (last == '+' || last == '-' || last == '*' || last == '/')
            num = true;
        text = text.substring(0, text.length() - 1);
        display.setText(text);
        if (!text.contains("+") && !text.contains("-") && !text.contains("*") && !text.contains("/"))
            function = false;
        if (text.endsWith("+") || text.endsWith("-") || text.endsWith("*") || text.endsWith("/"))
        {
            display2.setText(format(calculate(text.substring(0, text.length() - 1))));
            return;
        }
        if (function)
            display2.setText(format(calculate(text)));
        else
            display2.setText("");
    }

    private String calculate (String s)
    {
        //count how many numbers are in the string
        StringTokenizer q = new StringTokenizer(s, "/*-+", false);
        if (q.countTokens() == 2)
        {
            //if it's just two, calculate the answer
            StringTokenizer t = new StringTokenizer(s, "/*-+", true);
            String v1 = t.nextToken();
            //check for negative signs
            if (v1.equals("-"))
                v1 += t.nextToken();
            String f = t.nextToken();
            String v2 = t.nextToken();
            if (v2.equals("-"))
                v2 += t.nextToken();
            double result = 0;
            switch (f)
            {
                case "+":
                    result = Double.parseDouble(v1) + Double.parseDouble(v2);
                    break;
                case "-":
                    result = Double.parseDouble(v1) - Double.parseDouble(v2);
                    break;
                case "*":
                    result = Double.parseDouble(v1) * Double.parseDouble(v2);
                    break;
                case "/":
                    if (Double.parseDouble(v2) == 0)
                        return "div by 0";
                    else
                        result = Double.parseDouble(v1) / Double.parseDouble(v2);
                    break;
            }
            return String.valueOf(result);
        }
        else
        {
            //if it's multiple, divide the string up,
            StringTokenizer t = new StringTokenizer(s, "/*-+", true);
            ArrayList<String> a = new ArrayList<String>();
            String p = t.nextToken();
            if (p.equals("-"))
                p += t.nextToken();
            a.add(p);
            //turn it into an arraylist with negatives included
            while (t.hasMoreTokens())
            {
                a.add(t.nextToken());
                String r = t.nextToken();
                if (r.equals("-"))
                    r += t.nextToken();
                a.add(r);
            }
            //cycle through each function, calculating each set of numbers and replacing it with the
            //answer
            while (a.contains("/"))
            {
                int i = a.indexOf("/");
                String result = calculate(a.get(i-1) + a.get(i) + a.get(i+1));
                a.set(i, result);
                a.remove(i+1);
                a.remove(i-1);
            }
            while (a.contains("*"))
            {
                int i = a.indexOf("*");
                String result = calculate(a.get(i-1) + a.get(i) + a.get(i+1));
                a.set(i, result);
                a.remove(i+1);
                a.remove(i-1);
            }
            while (a.contains("-"))
            {
                int i = a.indexOf("-");
                String result = calculate(a.get(i-1) + a.get(i) + a.get(i+1));
                a.set(i, result);
                a.remove(i+1);
                a.remove(i-1);
            }
            while (a.contains("+"))
            {
                int i = a.indexOf("+");
                String result = calculate(a.get(i-1) + a.get(i) + a.get(i+1));
                a.set(i, result);
                a.remove(i+1);
                a.remove(i-1);
            }
            return a.get(0);
        }
    }

    private String format (String n)
    {
        DecimalFormat sciNot = new DecimalFormat("0.###E0");
        double m = Double.parseDouble(n);
        if (n.contains("E-"))
            return sciNot.format(m);
        DecimalFormat df = new DecimalFormat("0.######");
        String r = df.format(m);
        if (r.contains("."))
        {
            String b = r.split("\\.")[0];
            if (b.length() > 8)
                return sciNot.format(m);
            else
                return r;
        }
        else
        {
            if (r.length() > 8)
                return sciNot.format(m);
            else
                return r;
        }
    }

}