package com.example.lilittevosyan.tooltipproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by LilitTevosyan on 4/14/16.
 */
public class TooltipPopup extends PopupWindow {

    private TextView titleTextView;
    private Context context;
    private LayoutInflater layoutInflater;
    private View rootView;

    public TooltipPopup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initViews();
    }

    private void initViews() {
        rootView = layoutInflater.inflate(R.layout.popup_view, null);
        titleTextView = (TextView) rootView.findViewById(R.id.tooltip_title);


//        titleTextView = new TextView(context);
//        titleTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        titleTextView.setSingleLine();
//        titleTextView.setText("hello tooltip");
//        titleTextView.measure(0, 0);
//        setWidth(titleTextView.getMeasuredWidth());
//        setHeight(titleTextView.getMeasuredWidth());

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rootView.measure(0, 0);
        setWidth(rootView.getMeasuredWidth());
        setHeight(rootView.getMeasuredWidth());
        setContentView(rootView);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

}
