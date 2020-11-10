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
public class Sub2Fragment extends Fragment {

    protected View rootView;

    private SimpleDraweeView tenantlogo;


    public static Sub2Fragment newIntenes() {
        return new Sub2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_sub2, container, false);
            tenantlogo = rootView.findViewById(R.id.tenantlogo);
        }
        if (rootView != null && rootView.getParent() != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }


        return rootView;
    }
}
