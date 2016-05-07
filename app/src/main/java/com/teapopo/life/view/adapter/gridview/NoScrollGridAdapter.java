package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.teapopo.life.R;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.view.customView.RecyclableImageView;

import java.util.List;

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
        View view = View.inflate(ctx, R.layout.item_gridview_articleimage, null);
        RecyclableImageView imageView= (RecyclableImageView) view.findViewById(R.id.recyclableIv_articleimage);
        String url= NetWorkService.IMAGE_ENDPOINT+imageUrls.get(position)+NetWorkService.IMAGE_EXT;
        ImageLoader.getInstance().displayImage(url, imageView);

        return imageView;
    }

}
