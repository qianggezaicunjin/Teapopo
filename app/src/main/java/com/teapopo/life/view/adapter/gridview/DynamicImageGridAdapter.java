package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.view.customView.Dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class DynamicImageGridAdapter extends BaseDynamicGridAdapter {

    public DynamicImageGridAdapter(Context context, List<?> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DynamicImageViewHolder holder;
        if(convertView == null){
            holder = new DynamicImageViewHolder();
            convertView.setTag(holder);
        }else {
            holder = (DynamicImageViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class DynamicImageViewHolder{

    }
}
