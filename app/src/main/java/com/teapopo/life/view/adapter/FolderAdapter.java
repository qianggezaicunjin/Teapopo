package com.teapopo.life.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teapopo.life.model.imageselect.Folder;
import com.teapopo.life.model.imageselect.ImageConfig;
import com.teapopo.life.view.customView.RecyclableImageView;
import com.yancy.imageselector.R;



import java.util.ArrayList;
import java.util.List;


/**
 * 文件夹适配器
 * Created by Yancy on 2015/12/2.
 */
public class FolderAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<Folder> folderList = new ArrayList<>();
    private final static String TAG = "FolderAdapter";

    private int lastSelected = 0;

    private ImageConfig imageConfig;

    public FolderAdapter(Context context, ImageConfig imageConfig) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.imageConfig = imageConfig;
    }

    public void setData(List<Folder> folders) {
        if (folders != null && folders.size() > 0) {
            folderList.addAll(folders);
        } else {
            folderList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return folderList.size() + 1;
    }

    @Override
    public Folder getItem(int position) {
        if (position == 0)
            return null;
        return folderList.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.imageselector_item_folder, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder != null) {
            if (position == 0) {
                holder.folder_name_text.setText(R.string.all_folder);
                holder.image_num_text.setText("" + getTotalImageSize() + (context.getResources().getText(R.string.sheet)));

                if (folderList.size() > 0) {
                    Folder folder = folderList.get(0);

                    imageConfig.getImageLoader().displayImage(context, folder.cover.path, holder.folder_image,200,200);

//                    Glide.with(context)
//                            .load(new File(folder.cover.path))
//                            .placeholder(R.mipmap.imageselector_photo)
//                            .centerCrop()
//                            .into(holder.folder_image);
                }
            } else {

                Folder folder = getItem(position);
                holder.folder_name_text.setText(folder.name);
                holder.image_num_text.setText("" + folder.images.size() + (context.getResources().getText(R.string.sheet)));

                imageConfig.getImageLoader().displayImage(context, folder.cover.path, holder.folder_image,200,200);

//                Glide.with(context)
//                        .load(new File(folder.cover.path))
//                        .placeholder(R.mipmap.imageselector_photo)
//                        .centerCrop()
//                        .into(holder.folder_image);
            }

            if (lastSelected == position) {
                holder.indicator.setVisibility(View.VISIBLE);
            } else {
                holder.indicator.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

    class ViewHolder {

        RecyclableImageView folder_image;
        TextView folder_name_text;
        TextView image_num_text;
        RecyclableImageView indicator;

        ViewHolder(View itemView) {
            folder_image = (RecyclableImageView) itemView.findViewById(R.id.folder_image);
            folder_name_text = (TextView) itemView.findViewById(R.id.folder_name_text);
            image_num_text = (TextView) itemView.findViewById(R.id.image_num_text);
            indicator = (RecyclableImageView) itemView.findViewById(R.id.indicator);
            itemView.setTag(this);
        }

    }

    public int getSelectIndex() {
        return lastSelected;
    }


    private int getTotalImageSize() {
        int result = 0;
        if (folderList != null && folderList.size() > 0) {
            for (Folder folder : folderList) {
                result += folder.images.size();
            }
        }
        return result;
    }


    public void setSelectIndex(int position) {
        if (lastSelected == position)
            return;
        lastSelected = position;
        notifyDataSetChanged();
    }

}