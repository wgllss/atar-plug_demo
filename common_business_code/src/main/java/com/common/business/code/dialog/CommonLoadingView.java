package com.common.business.code.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.common_business_code.R;


public class CommonLoadingView extends Dialog {
    private TextView txt_loading_text;

    public CommonLoadingView(Context context) {
        super(context, R.style.Loading);
        setContentView(R.layout.common_loading);
        txt_loading_text = (TextView) getWindow().findViewById(R.id.txt_loading_text);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public void show(String showText) {
        if (txt_loading_text != null && showText.length() > 0) {
            txt_loading_text.setText(showText);
            txt_loading_text.setVisibility(View.VISIBLE);
        } else {
            txt_loading_text.setVisibility(View.GONE);
        }
        if (!isShowing()) {
            show();
        }
    }
}
