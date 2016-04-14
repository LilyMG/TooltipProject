package com.example.lilittevosyan.tooltipproject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private TooltipPopup bottomPanelItemPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomPanelItemPopup = new TooltipPopup(this, null);
        bottomPanelItemPopup.setTitle("goodByeTooltip");

        findViewById(R.id.anchor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomPanelItemPopup.showAsDropDown(findViewById(R.id.anchor));
            }
        });


    }

}
