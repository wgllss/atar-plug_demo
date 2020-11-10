package com.app.sub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.app.sub.R;
import com.common.framework.widget.CommonToast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author：atar
 * @date: 2020/11/6
 * @description:
 */
public class DemoFragmentDialog extends DialogFragment implements View.OnClickListener {
    private Context context;
    protected View rootView;
    private EditText edt_content;
    private TextView txt_ok;
    private TextView txt_close;

    public static DemoFragmentDialog create(Context context) {
        DemoFragmentDialog dialog = new DemoFragmentDialog();
        dialog.context = context;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.dialog_fragment_demo, container, false);
            edt_content = rootView.findViewById(R.id.edt_content);
            txt_ok = rootView.findViewById(R.id.txt_ok);
            txt_close = rootView.findViewById(R.id.txt_close);
        }
        if (rootView != null && rootView.getParent() != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && getActivity() != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.435), WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.dimAmount = 0.6f;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            getDialog().getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txt_ok.setOnClickListener(this);
        txt_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_close:
                dismiss();
                break;
            default:
                String str = edt_content.getText().toString();
                if (TextUtils.isEmpty(str)) {
                    CommonToast.show("输入内容为空");
                    return;
                }
                CommonToast.show(str);
                break;
        }
    }
}
