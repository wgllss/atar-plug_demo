package com.app.sub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.sub.MenuItemBean;
import com.app.sub.R;
import com.app.sub.adapter.SubMenuAdapter;
import com.app.sub.dialog.DemoFragmentDialog;
import com.app.sub.dialog.DialogDemo;
import com.common.business.code.activity.CommonTitleActivity;
import com.common.business.code.utils.IntentUtil;
import com.common.framework.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainSub2Activity extends CommTitleResouseActivity implements SubMenuAdapter.OnItemClickListener {

    private RecyclerView recyclerview;
    private List<MenuItemBean> list = new ArrayList<MenuItemBean>();
    private SubMenuAdapter subMenuAdapter = new SubMenuAdapter(list);

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_main_sub2);
        setActivityTitle("插件子页2");

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        list.add(new MenuItemBean(0, "子页2 使用 dialog"));
        list.add(new MenuItemBean(1, "子页2 使用fragment"));
        list.add(new MenuItemBean(2, "子页2 跳转到 主页2"));
        list.add(new MenuItemBean(3, "子页2 使用 DialogFragment"));
        list.add(new MenuItemBean(4, "子页2 使用 viewPager"));
        list.add(new MenuItemBean(5, "子页2 使用 livedada, 网络请求后用"));
        recyclerview.setAdapter(subMenuAdapter);

        subMenuAdapter.setListener(this);
    }

    @Override
    public void onItemClick(MenuItemBean info) {
        switch (info.getItemID()) {
            case 0:
                new DialogDemo(thisContext).show();
                break;
            case 1:
                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, MainSubFragmentActivity.class));
                break;
            case 2:
                try {
                    Class<?> cls = Class.forName("com.atar.host.app.activity.Main3Activity");
                    Intent intent = new Intent(thisContext, cls);
                    intent.putExtra(PluginManager.HOST_CLASS_NAME, true);
                    IntentUtil.startOtherActivity(thisContext, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                DemoFragmentDialog fragmentDialog = DemoFragmentDialog.create(thisContext);
                fragmentDialog.show(getSupportFragmentManager(), "adfafdaf");
                break;
            case 4:
                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, ViewPagerActivity.class));
                break;
            case 5:
                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, NetTestActivity.class));
                break;
        }
    }
}
