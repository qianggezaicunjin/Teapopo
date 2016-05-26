package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.teapopo.life.R;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.ViewUtils;
import com.teapopo.life.view.customView.RecyclableImageView;

import java.util.List;

import timber.log.Timber;

public class NoScrollGridAdapter extends BaseAdapter {

    /** 上下文 */
    private Context ctx;
    /** 图片Url集合 */
    private List<String> imageUrls;

    public NoScrollGridAdapter(Context ctx, List<String> urls) {
        this.ctx = ctx;
        this.imageUrls = urls;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageUrls == null ? 0 : imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageview = (ImageView) View.inflate(ctx, R.layout.item_gridview_articleimage, null);
//        ImageView imageview= (ImageView) view.findViewById(R.id.recyclableIv_articleimage);
//        int size1 =  parent.getWidth()/ DataUtils.dealImageShowColums(imageUrls.size());
//        imageview.setLayoutParams(new LinearLayout.LayoutParams(size1,size1));
        String url= NetWorkService.IMAGE_ENDPOINT+imageUrls.get(position)+NetWorkService.IMAGE_EXT;
        Timber.d("容器的宽度为:%d,imageview的宽度为:%d,高度为:%d",parent.getWidth(),imageview.getWidth(),imageview.getHeight());
        ImageLoader.getInstance().displayImage(url, imageview);
        return imageview;
    }

}
