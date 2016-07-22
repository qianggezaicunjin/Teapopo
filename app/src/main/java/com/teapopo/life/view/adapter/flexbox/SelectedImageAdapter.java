package com.teapopo.life.view.adapter.flexbox;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.ItemFlexboxSelectedImageBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.event.CountLeftSelectedImageEvent;
import com.teapopo.life.model.event.UploadImageEvent;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.view.adapter.LBaseAdapter;
import com.teapopo.life.view.adapter.LBaseAdapter.BaseViewHolder;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/19.
 */
public class SelectedImageAdapter extends LBaseAdapter<BaseEntity,BaseViewHolder> {

    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    public SelectedImageAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        return SelectedImageViewHolder.createViewHolder(ItemFlexboxSelectedImageBinding.inflate(LayoutInflater.from(getContext())));
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position,  BaseEntity data) {
        SelectedImageViewHolder selectedImageViewHolder = (SelectedImageViewHolder)holder;
        final ItemFlexboxSelectedImageBinding binding = (ItemFlexboxSelectedImageBinding) selectedImageViewHolder.getBinding();
        final Image image = (Image) data;
        ViewGroup rootView = (ViewGroup) binding.getRoot();
//        if(rootView.getTag()==null){
//            ViewGroup loadingView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_mask_loading, rootView, false);
//            rootView.addView(loadingView);
//            rootView.setTag(loadingView);
//            observeUploadImage(image,rootView);
//        }
        binding.imgDeleteSelectedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Timber.d("post a  image");
                removeData(image);
                //发出通知 更改gridview里面的图片选中状态
                ComponentHolder.getAppComponent().rxbus().post(image);
                ComponentHolder.getAppComponent().rxbus().post(new CountLeftSelectedImageEvent());
            }
        });
        selectedImageViewHolder.setImage(image);
    }

//    private void observeUploadImage(final Image image, final ViewGroup rootView) {
//        Observable<UploadImageEvent> observeble = ComponentHolder.getAppComponent().rxbus().toObserverable(UploadImageEvent.class);
//        compositeSubscription.add(
//        observeble.observeOn(AndroidSchedulers.mainThread())
//                        .doOnNext(new Action1<UploadImageEvent>() {
//                            @Override
//                            public void call(UploadImageEvent uploadImageEvent) {
//                                Timber.d("收到uploadImageEvent");
//                                if(image.path.equals((uploadImageEvent.iamgePath))){
//                                    rootView.removeView((View) rootView.getTag());
//                                }
//                            }
//                        })
//                .subscribe()
//        );
//
//    }

    public static class SelectedImageViewHolder extends BaseViewHolder{
        private ViewDataBinding mBinding;
        public static SelectedImageViewHolder createViewHolder(ViewDataBinding binding){
            return new SelectedImageViewHolder(binding.getRoot(),binding);
        }
        public SelectedImageViewHolder(View itemView, ViewDataBinding binding) {
            super(itemView);
            mBinding = binding;
        }

        public ViewDataBinding getBinding(){
            return mBinding;
        }
        public void setImage(Image image){
            ItemFlexboxSelectedImageBinding binding = (ItemFlexboxSelectedImageBinding) mBinding;
            Timber.d("setImage");
            binding.setImage(image);
            binding.executePendingBindings();
        }
    }

}
