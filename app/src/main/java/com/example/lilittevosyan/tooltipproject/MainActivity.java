package com.example.lilittevosyan.tooltipproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ToolTipLinear tooltipPopup;
    private ViewGroup rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.activity_main, null);
        setContentView(rootView);
        rootView.findViewById(R.id.test_press_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                tooltipPopup.dismiss();
            }
        });
        tooltipPopup = new ToolTipLinear(this, null, rootView);
        tooltipPopup.setTitle("goodByeTooltip");
        tooltipPopup.setDismissFromOutside(true);
        tooltipPopup.setAnchorView(findViewById(R.id.anchor));
        tooltipPopup.setTooltipPosition(ToolTipLinear.TOOLTIP_POSITION_DOWN);

        findViewById(R.id.anchor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tooltipPopup.show();
            }
        });
    }

}
