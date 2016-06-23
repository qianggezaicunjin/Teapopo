package com.teapopo.life.view.customView.FlexBox;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by louiszgm-pc on 2016/6/20.
 */
public class FlexBoxWithAdapter extends FlexboxLayout {
    private ListAdapter adapter;
    public FlexBoxWithAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setAdapter(@NonNull ListAdapter adapter) {
        if (adapter == null) throw new NullPointerException("FlowLayout.setAdapter不能传空参数");
        this.adapter = adapter;
        changeViews(); //这个是用来给Flowlayout加view的逻辑，先忽略
        adapter.registerDataSetObserver(new DataSetObserver() {

            @Override
            public void onChanged() {
                changeViews();
            }

            @Override
            public void onInvalidated() {
                changeViews();
            }
        });
    }
    public ListAdapter getAdapter(){
        return this.adapter;
    }
    private void changeViews() {
        final int count = adapter.getCount();  //先拿到要显示的view的数量
        if (count > 0) {
            //在即将显示的数量大于0的情况下 再lookup一下flowlayout当前子view的数量
            int childCount = getChildCount();
            if (childCount > count) {
                //如果说flexboxlayout当前的子view数量多过要显示的view数量，那就删除几个，让其数量保持一致
                removeViews(count, childCount - count);
            }
            //上面就是所说的‘能复用就复用，不能复用再创建‘

            for (int i = 0; i < count; i++) {
                //这个就比较简单了， 向adapter要view。
                //getChildAt(i) 就是adapter内getView中的第二个参数 convertView,
                //如果有就传过去 ，没有就是null,这时按正常来说，我们会重新创建一个view。
                final View view = adapter.getView(i, getChildAt(i), this);
                if (view.getLayoutParams() == null) {
                    //好了，这时呢得到了一个view,看看有没有 布局参数，
                    //没有就给一个，免得FlowLayout自动生成一个的话
                    //会match_parent,这样就不合我们的要求了，
                    LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    view.setLayoutParams(lp);
                }

                if (getChildAt(i) != view) {
                    //这里，为啥要判断下呢？
                    //这要说到前面的蹩脚的复用了，
                    //前面把getChildAt(i)传给了getView，如果FlexboxLayout本身就有view,
                    //那么在getView里面，就只是改变一下text,image等等的数据，这时
                    //getChildAt(i) 和adater返回的view肯定还是同一个view，所以不用重复加
                    //----
                    //但是如果不一样，那就是说，getChildAt(i)就是null , 跟本就没有，
                    addView(view);
                }
            }
        }
    }
}
