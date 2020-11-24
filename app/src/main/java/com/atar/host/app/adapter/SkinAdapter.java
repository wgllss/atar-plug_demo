package com.atar.host.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atar.host.app.R;
import com.atar.host.app.configs.SkinTypeBean;
import com.common.framework.adapter.CommonAdapter;
import com.common.framework.skin.SkinUtils;

import java.util.List;

/**
 * @author：atar
 * @date: 2020/11/24
 * @description:
 */
public class SkinAdapter extends CommonAdapter<SkinTypeBean> {

    public SkinAdapter(List<?> list) {
        super(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_skin_item, null);
            mViewHolder.txt_item_name = (TextView) convertView.findViewById(R.id.txt_skin_name);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        SkinTypeBean info = getItem(position);
        if (info != null) {
            SkinUtils.setBackgroundColor(parent.getContext(), R.string.common_tab_bg_color_array_name, getSkinType(), mViewHolder.txt_item_name);
            if (position == getCurrentPostiotn()) {
                mViewHolder.txt_item_name.setText(info.getSkinName() + "(当前选中)");
            } else {
                mViewHolder.txt_item_name.setText(info.getSkinName());
            }
        }
        return convertView;
    }

    public class ViewHolder {
        TextView txt_item_name;
    }
}
