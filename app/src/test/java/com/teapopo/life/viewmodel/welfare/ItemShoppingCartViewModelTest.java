package com.teapopo.life.viewmodel.welfare;

import com.teapopo.life.model.welfare.CartGoods;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by louiszgm on 2016/7/8.
 */
public class ItemShoppingCartViewModelTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test1() throws CloneNotSupportedException {
        CartGoods cartGoods = new CartGoods();
        cartGoods.goods_num = 1;
        newObject(cartGoods);
        Assert.assertEquals(2,cartGoods.goods_num);
    }

    private CartGoods newObject(CartGoods cartGoods) throws CloneNotSupportedException {
        CartGoods cartGoods1 = (CartGoods) cartGoods.clone();
        cartGoods1.goods_num = 2;
        return cartGoods1;
    }
}