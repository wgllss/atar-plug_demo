package com.app.sub.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubHomeActivity extends MainSub2Activity {


    @Override
    protected void initValue() {
        super.initValue();
        setOnDrawerBackEnabled(false);
        setActivityTitle("插件中主页");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                //spanCount 当横向时，2代表每列2行，
                switch (i) {
                    case 0:
                    case 7:
                        return 2;//  spanCount/2  个位置占满一格
                    default:
                        return 1;// spanCount/1  个位置占满一格
                }
            }
        });
        return gridLayoutManager;
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }
}
