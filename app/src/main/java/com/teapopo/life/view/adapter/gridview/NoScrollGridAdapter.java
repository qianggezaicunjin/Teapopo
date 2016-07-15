package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.teapopo.life.data.remote.NetWorkService;

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
      ImageView imageView = new ImageView(ctx);

        String image = imageUrls.get(position);
        String url= NetWorkService.IMAGE_ENDPOINT+image+NetWorkService.IMAGE_EXT;
        ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }

}
