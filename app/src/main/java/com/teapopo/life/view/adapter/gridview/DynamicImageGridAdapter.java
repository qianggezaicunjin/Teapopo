package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.teapopo.life.R;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.event.TellUpLoadDoneOrNot;
import com.teapopo.life.view.customView.Dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.Observable;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class DynamicImageGridAdapter extends BaseDynamicGridAdapter {

    private Context mContext;
    public DynamicImageGridAdapter(Context context, List<?> items, int columnCount) {
        super(context, items, columnCount);
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
        registerSubscribe(photoInfo);
        holder.setPhoto(photoInfo);
        return convertView;
    }
    //更新图片的上传状态
    private void registerSubscribe(PhotoInfo photoInfo) {
        Observable<TellUpLoadDoneOrNot> observable = ComponentHolder.getAppComponent().rxbus().toObserverable(TellUpLoadDoneOrNot.class);

    }

    private class DynamicImageViewHolder{
        ImageView imageView;

        public DynamicImageViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.img_publishimage);
        }
        public void setPhoto(PhotoInfo photo){
            ImageLoader.getInstance().displayImage("file:/" + photo.getPhotoPath(), imageView);
        }
    }
}
