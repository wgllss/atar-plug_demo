package com.app.sub.activity;

import android.os.Bundle;

import com.app.sub.R;
import com.app.sub.fragments.Sub2Fragment;
import com.app.sub.fragments.SubFragment;
import com.common.framework.adapter.FragmentAdapter;
import com.common.framework.utils.ZzLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * @author：atar
 * @date: 2020/11/6
 * @description:
 */
public class ViewPagerActivity extends CommTitleResouseActivity implements ViewPager.OnPageChangeListener {

    @Override
    public void initControl(@Nullable Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_view_pager);
        setActivityTitle("插件页 viewpager");
        ViewPager view_pager = findViewById(R.id.view_pager);

        List<Fragment> list = new ArrayList<>();
        list.add(SubFragment.newIntenes());
        list.add(Sub2Fragment.newIntenes());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), list);
        view_pager.setAdapter(fragmentAdapter);
        view_pager.setOffscreenPageLimit(list.size());

        view_pager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setOnDrawerBackEnabled(position == 0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
