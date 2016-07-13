package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.teapopo.life.R;
import com.teapopo.life.util.BitmapUtils;
import com.teapopo.life.view.customView.Dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class DynamicImageGridAdapter extends BaseDynamicGridAdapter {

    private Context mContext;
    public CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    public DynamicImageGridAdapter(Context context, List<?> items,int columnCount) {
        super(context, items,columnCount);
        mContext = context;
    }

    public void refresh(List<?> data){
       set(data);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DynamicImageViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dynamicgridview_image,null);
            holder = new DynamicImageViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (DynamicImageViewHolder) convertView.getTag();
        }
        PhotoInfo  photoInfo = (PhotoInfo) getItem(position);
        holder.setPhoto(photoInfo);
        return convertView;
    }

    private class DynamicImageViewHolder{
        ImageView imageView;
        public DynamicImageViewHolder(View view){
            imageView = (ImageView) view;

        }
        public void setPhoto(PhotoInfo photo){
            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFd(photo.getPhotoPath(),200,200);
            imageView.setImageBitmap(bitmap);
        }

    }
}
