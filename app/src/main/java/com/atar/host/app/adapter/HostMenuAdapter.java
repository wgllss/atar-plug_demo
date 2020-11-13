package com.atar.host.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atar.host.app.R;
import com.atar.host.app.beans.MenuItemBean;
import com.common.framework.adapter.BaseRecyclerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：atar
 * @date: 2020/11/6
 * @description:
 */
public class HostMenuAdapter extends BaseRecyclerAdapter<MenuItemBean> {

    private OnItemClickListener listener;

    public HostMenuAdapter(List<?> list) {
        super(list);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        setContext(parent.getContext());
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sub_menu_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (getList() != null && getList().size() > 0) {
            viewHolder.bind(getList().get(position), position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_item;
        View view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txt_item = itemView.findViewById(R.id.txt_item);
        }

        public void bind(MenuItemBean info, int position) {
            txt_item.setText(info.getItemName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(info);
                    }
                }
            });
        }

        public void setData(MenuItemBean info) {
            txt_item.setText(info.getItemName());
        }
    }

    //局部更改UI
    public void refreshItem(int position, MenuItemBean menuItemBean, RecyclerView recyclerview, LinearLayoutManager layoutManager) {
        try {
            int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
            int lastItemPosition = layoutManager.findLastVisibleItemPosition();
            if (position >= firstItemPosition && lastItemPosition >= position) {
                //得到要更新的item的view
                View view = recyclerview.getChildAt(position - firstItemPosition);
                if (null != recyclerview.getChildViewHolder(view)) {
                    ViewHolder viewHolder = (ViewHolder) recyclerview.getChildViewHolder(view);
                    try {
                        viewHolder.setData(menuItemBean);
                    } catch (Exception e) {
                    }
                } else {
                    notifyItemChanged(position);
                }
            } else {
                notifyItemChanged(position);
            }
        } catch (Exception e) {
            notifyItemChanged(position);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(MenuItemBean info);
    }
}
