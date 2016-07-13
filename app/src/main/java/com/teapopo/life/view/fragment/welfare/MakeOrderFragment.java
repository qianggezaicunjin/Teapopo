package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentMakeorderBinding;
import com.teapopo.life.databinding.LayoutReceiveAddressBinding;
import com.teapopo.life.model.Goods;
import com.teapopo.life.model.event.MakeOrderSuccessEvent;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.recyclerview.MakeOrderListAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.MakeOrderViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class MakeOrderFragment extends SwipeBackBaseFragment{

    private FragmentMakeorderBinding mBinding;

    //要下单的商品， 活动商品或者是购物车的商品
    private ArrayList<Parcelable> goodsList;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    @Inject
    MakeOrderViewModel mViewModel;

    @Inject
    RxBus mRxBus;
    private LayoutReceiveAddressBinding binding_addressinfo;

    public static MakeOrderFragment newInstance(ArrayList<Parcelable> goodsList){
        MakeOrderFragment fragment = new MakeOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("goodslist",goodsList);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        goodsList = getArguments().getParcelableArrayList("goodslist");
        ((GoodsHandleActivity)_mActivity).getFragmentComponent().inject(this);
        observerWhenMakeOrderDone();
    }

    private void observerWhenMakeOrderDone() {
        Observable<MakeOrderSuccessEvent> observable = mRxBus.toObserverable(MakeOrderSuccessEvent.class);
        compositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new RxSubscriber<MakeOrderSuccessEvent>() {
                                        @Override
                                        public void _onNext(MakeOrderSuccessEvent makeOrderSuccessEvent) {

                                            start(OrderSettleMentFragment.newInstance(makeOrderSuccessEvent.orderId));
                                        }

                                        @Override
                                        public void _onError(String s) {
                                            Timber.e(s);
                                        }
                                    }));
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMakeorderBinding.inflate(inflater);
        binding_addressinfo = LayoutReceiveAddressBinding.inflate(inflater);
        mBinding.setViewModel(mViewModel);
        mBinding.setHandler(this);
        binding_addressinfo.setHandler(this);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpGoodsList();
        setUpAddressInfo();
        setUpReMarkInfo();
        setToolBar();
    }

    private void setUpReMarkInfo() {
        EditText editText = new EditText(_mActivity);
        editText.setBackgroundResource(R.drawable.background_transparent);
        editText.setHint("订单备注信息");
        mBinding.rvGoodsSettlement.addFooter(editText);
    }

    private void setUpAddressInfo() {
        mBinding.rvGoodsSettlement.addHeader(binding_addressinfo.getRoot());
    }

    private void setUpGoodsList() {
        MakeOrderListAdapter adapter = new MakeOrderListAdapter(_mActivity,mViewModel.data);
        mBinding.rvGoodsSettlement.setAdapter(adapter);

        //
        mViewModel.handleGoodsList(goodsList);
    }

    public void clickAddress(View view){
        startForResult(AddressManageFragment.newInstance(),1);
    }

    public void clickMakeOrder(View view){
        mViewModel.makeOrder();
    }
    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        Timber.d("收到选择的地址");
        binding_addressinfo.setBaseinfo(data.getString("BaseInfo"));
        binding_addressinfo.setAddress(data.getString("address"));
    }

    //设置返回按钮监听
    public void setToolBar(){
        Toolbar toolbar=mBinding.toolbarMakeorder;
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
