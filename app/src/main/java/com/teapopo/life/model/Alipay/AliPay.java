package com.teapopo.life.model.Alipay;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.util.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by louiszgm on 2016/7/2.
 */

public class AliPay extends BaseEntity {

    private String rsa_private_key_pkcs8 = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMy/R0bDTvmJwhLzQ5oVSMsp0d9hFEM13aw9jcSQPrckWcqVs7e+IHGX/Ki1SIpKWk7xbglej7k/UJPreflhG+4MNRAKHjfMdVRin27W4Jlg6gcgZM0vlcv2Sz0SycpdCIBURyrB9y+JbxfMibFqzTE4clsliUvrKyGhgQ3lPYH/AgMBAAECgYEAmmuZQlGDesjfSpOWZNuwrym3VBZPxfEWYxV5msp/wnj0D8qgZPBMX+AVethfAY3aVrfYGdDr7PdHuoha0i7fdGz7nKwzCMDbWyZ/1AesINHNSlwz6DmBPfH/sL9QWoMA+KvN4Ooh8VKHJPXDR9hPydM9tjionopCURgOtJq2F2ECQQDnWWFKIpMHkvOjtWZBJ4gJ8OeCdY+wO5oyj68lsQlMX2nPQxLABp6NieZx0NxaHNVH2B9ilkGo0V5qpIM6jg7tAkEA4pBDn4/48Vz1220TgVt3PJbnQkqe5sjjYBiZ1/Wrutp1UQtnNWgbNJMYDeGi7qHOA0ALdTml1DbwzNU5opzLGwJBALp5n+LatXqAZ6QIPlC8JXol1OWiDty1XhftGvcdmOoXajkmzkE71KcvhTEucb6syPks6jdT9760bA83ZZNYGA0CQQDKaMRha0imqbxkesBwUvzlvpOA4BWybUrl8VSQYcU4vC8PZragOhAEGl3lGO5tb1UUBkW2Rvhl7WeYN+6z3ox9AkB+6PwvNTEcgwRlf6hmrLN/9lOHYIO7Gg904POKVqTUR/0bMr0SGOFz7PWCjZwNnX0apo4HUh7M70/qpvrF079k";

    private String PARTNER = "2088021837165023";

    private String SELLER = "newstea2013@foxmail.com";

    private String notify_url = "http://www.teapopo.com/api/callback/alipay_notify";

    private String sign_type = "RSA";
    private String out_trade_no;

    private String price;
    public AliPay(String out_trade_no,String price){
        this.out_trade_no = out_trade_no;
        this.price = price;
    }

    public String getPayInfo() throws UnsupportedEncodingException {
        String sign1 = SignUtils.sign(getOrderInfo(),rsa_private_key_pkcs8);
        String sign = URLEncoder.encode(sign1,"UTF-8");
        return getOrderInfo()+ "&sign=\"" + sign + "\""+"&sign_type=" + "\"" + sign_type + "\"";
    }
    /**
     * 创建支付订单
     * @return
     */
    private String getOrderInfo() {
         String subject = "支付订单"+out_trade_no;
         String body = subject;
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url="+ "\"" + show_url + "\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
}
