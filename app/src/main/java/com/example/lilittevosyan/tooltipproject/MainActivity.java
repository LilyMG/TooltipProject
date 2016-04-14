package com.example.lilittevosyan.tooltipproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private ToolTipLinear tooltipPopup;
    private ViewGroup rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.activity_main, null);
        setContentView(rootView);
        tooltipPopup = new ToolTipLinear(this, null, rootView);
        tooltipPopup.setTitle("goodByeTooltip");

        findViewById(R.id.anchor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tooltipPopup.show();
            }
        });



    }

}
