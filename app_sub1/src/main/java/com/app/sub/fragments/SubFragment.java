package com.app.sub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.sub.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author：atar
 * @date: 2020/11/5
 * @description:
 */
public class SubFragment extends Fragment {

    protected View rootView;

    private SimpleDraweeView tenantlogo;


    public static SubFragment newIntenes() {
        return new SubFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_sub, container, false);
            tenantlogo = rootView.findViewById(R.id.tenantlogo);
        }
        if (rootView != null && rootView.getParent() != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }


        tenantlogo.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604638658125&di=34b0a808169079f119afe34e734f9f86&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F30%2F29%2F01300000201438121627296084016.jpg");
        return rootView;
    }
}
