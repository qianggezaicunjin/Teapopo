package com.teapopo.life.view.adapter.gridview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;


import com.teapopo.life.R;
import com.teapopo.life.databinding.ItemGridSelectimageBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.model.imageselect.ImageConfig;
import com.teapopo.life.view.adapter.LBaseAdapter;
import com.teapopo.life.viewModel.publisharticle.ItemSelectImageViewModel;


import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 *
 */
public class ImageAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<Image> imageList;
    private Context mContext;
    private final static String TAG = "ImageAdapter";

    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;

    private ImageConfig mImageConfig;
    private int mItemSize;
    private AbsListView.LayoutParams mItemLayoutParams;
    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    public ImageAdapter(Context context,ImageConfig imageConfig,List<Image> imageList) {
        mContext = context;
        mImageConfig = imageConfig;
        this.imageList = imageList;
        mLayoutInflater = LayoutInflater.from(mContext);
        mItemLayoutParams = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT);
    }


    @Override
    public int getCount() {
        return mImageConfig.isShowCamera() ? imageList.size() + 1 : imageList.size();
    }

    @Override
    public Image getItem(int position) {
        if (mImageConfig.isShowCamera()) {
            if (position == 0) {
                return null;
            }
            return imageList.get(position - 1);
        } else {
            return imageList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position)==TYPE_CAMERA){
            convertView = mLayoutInflater.inflate(R.layout.item_camera,parent,false);
            convertView.setTag(null);
        }else if(getItemViewType(position)==TYPE_NORMAL){
            ItemGridSelectimageBinding binding = ItemGridSelectimageBinding.inflate(mLayoutInflater,parent,false);
             GridSelectViewHolder holder;
            if (convertView == null) {
                convertView = binding.getRoot();
                holder = new GridSelectViewHolder(convertView,binding);
            } else {
                holder = (GridSelectViewHolder) convertView.getTag();
                if (holder == null) {
                    convertView = binding.getRoot();
                    holder = new GridSelectViewHolder(convertView,binding);
                }
        }
            if (mItemSize > 0) {
                ItemSelectImageViewModel viewModel = new ItemSelectImageViewModel();
                viewModel.image = getItem(position);
                holder.setViewModel(viewModel);
                ObserveClickImage(viewModel,getItem(position));
            }

     }
        GridView.LayoutParams layoutParams = (GridView.LayoutParams) convertView.getLayoutParams();
        if (layoutParams.height != mItemSize) {
            convertView.setLayoutParams(mItemLayoutParams);
        }
        return convertView;
    }

    private void ObserveClickImage(final ItemSelectImageViewModel viewModel,Image image) {
        compositeSubscription.add(ComponentHolder.getAppComponent().rxbus().toObserverable(Image.class)
                .doOnNext(new Action1<Image>() {

                    @Override
                    public void call(Image image) {
                        if(image.isSelected!=true){
                            viewModel.changeImageState(image);
                        }
                    }
                })
                .subscribe());

    }

    public void setItemSize(int columnWidth) {
        if (mItemSize == columnWidth) {
            return;
        }
        mItemSize = columnWidth;
        mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if (mImageConfig.isShowCamera() && position == 0) {
            return TYPE_CAMERA;
        }
        return TYPE_NORMAL;
    }


     class GridSelectViewHolder {
        private ItemGridSelectimageBinding binding;

        public GridSelectViewHolder(View rootView,ViewDataBinding binding) {
            this.binding = (ItemGridSelectimageBinding) binding;
            rootView.setTag(this);
        }
        public void setViewModel(ItemSelectImageViewModel viewModel){
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }


}