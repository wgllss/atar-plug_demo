package com.atar.host.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.framework.adapter.PagerImageAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author：atar
 * @date: 2020/11/19
 * @description:
 */
public class PagerAdAdapter extends PagerImageAdapter<String> {

    private OnItemClickListener onItemClickListener;

    public PagerAdAdapter(List<String> list) {
        super(list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        SimpleDraweeView imgaeView = new SimpleDraweeView(container.getContext());
        imgaeView.setScaleType(ImageView.ScaleType.FIT_XY);
        imgaeView.setImageURI(list.get(position));
        container.addView(imgaeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (position == list.size() - 1) {
            imgaeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClisk(position);
                    }
                }
            });
        }
        return imgaeView;
    }

    public interface OnItemClickListener {

        void onItemClisk(int position);
    }
}
