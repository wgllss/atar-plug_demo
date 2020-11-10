package com.atar.host.app.activity;

import com.common.business.code.activity.BasePresenter;
import com.common.business.code.activity.CommonTitleActivity;
import com.common.business.code.lifecyle.BaseViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;

/**
 * @author：atar
 * @date: 2020/10/26
 * @description:
 */
public abstract class RefreshLayoutActivity<VM extends BaseViewModel, P extends BasePresenter> extends CommTitleResouseActivity<VM, P> implements OnRefreshListener, OnLoadMoreListener {

    protected RefreshLayout refreshLayout;
    private boolean isFirstLoad = true;

    public void setEnableRefresh(boolean enableRefresh) {
        if (refreshLayout != null) {
            refreshLayout.setEnableRefresh(enableRefresh);
        }
    }

    public void setEnableAutoLoadMore(boolean enableAutoLoadMore) {
        if (refreshLayout != null) {
            refreshLayout.setEnableAutoLoadMore(enableAutoLoadMore);
        }
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        if (refreshLayout != null) {
            refreshLayout.setEnableLoadMore(enableLoadMore);
        }
    }

    public void autoRefresh() {
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    public void autoLoadMore() {
        if (refreshLayout != null) {
            refreshLayout.autoLoadMore();
        }
    }

    public void finishRefresh() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
    }

    public void finishLoadMore() {
        if (refreshLayout != null) {
            refreshLayout.finishLoadMore();
        }
    }

    public void finishBoth() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * @author :Atar
     * @createTime: 2018/5/17 16:14
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description: 是否第一次加载
     */
    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    public void setIsFirstLoad(boolean isFirstLoad) {
        this.isFirstLoad = isFirstLoad;
    }

    protected void setRefreshUI(RefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
        if (refreshLayout != null) {
            setEnableRefresh(true);
            setEnableLoadMore(true);
            setEnableAutoLoadMore(true);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}
