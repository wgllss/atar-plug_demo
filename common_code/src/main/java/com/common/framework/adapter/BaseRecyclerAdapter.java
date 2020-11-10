package com.common.framework.adapter;

import android.content.Context;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> list;
    private Handler handler;
    private Context context;
    protected int currentPostiotn = -1;
    private int condition = -1;
    private int skinType;

    public BaseRecyclerAdapter(List<?> list) {
        this.list = (List<T>) list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getCurrentPostiotn() {
        return currentPostiotn;
    }

    public void setCurrentPostiotn(int currentPostiotn) {
        this.currentPostiotn = currentPostiotn;
        notifyDataSetChanged();
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
        notifyDataSetChanged();
    }

    public int getSkinType() {
        return skinType;
    }

    public void setSkinType(int skinType) {
        this.skinType = skinType;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (list != null && list.size() > position) {
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    public void clearList() {
        if (list != null) {
            list.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
