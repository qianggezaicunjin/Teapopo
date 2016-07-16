package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewFolderBinding;
import com.teapopo.life.model.imageselect.Folder;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by louiszgm on 2016/7/16.
 */
public class ImageFolderAdapter extends BaseRecyclerViewAdapter<Folder,ImageFolderAdapter.FolderViewHolder> {
    public ImageFolderAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public ImageFolderAdapter.FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FolderViewHolder.createViewHolder(ItemRecyclerviewFolderBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.setFolder(data.get(position));
    }

    public static class FolderViewHolder extends RecyclerView.ViewHolder{

        public static FolderViewHolder createViewHolder(ViewDataBinding binding){
            return new FolderViewHolder(binding.getRoot(),binding);
        }
        public FolderViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }

        public void setFolder(Folder folder){
            ItemRecyclerviewFolderBinding binding = (ItemRecyclerviewFolderBinding) itemView.getTag();
            binding.setFolder(folder);
            binding.executePendingBindings();
        }
    }
}
