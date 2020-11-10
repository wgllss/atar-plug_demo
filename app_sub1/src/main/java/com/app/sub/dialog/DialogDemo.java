package com.app.sub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.app.sub.R;

import androidx.annotation.NonNull;

/**
 * @author：atar
 * @date: 2020/10/23
 * @description:
 */
public class DialogDemo extends Dialog {

    public DialogDemo(@NonNull Context context) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.dialog_demo);
        getWindow().findViewById(R.id.txt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
