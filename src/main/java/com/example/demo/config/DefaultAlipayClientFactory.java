package com.example.demo.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import java.util.Properties;

/**
 * @author 君泓 junying.wjy
 * @ClassName: DefaultAlipayClientFactory
 * @Description: TODO(公共请求参数拼接类)
 * @date 2018年7月23日 下午4:38:38
 */
public class DefaultAlipayClientFactory {

    public static AlipayClient alipayClient = null;

    public static String CHARSET = null;

    public static String SIGN_TYPE = null;

    public static String ALIPAY_PUBLIC_KEY = null;

    public static String RETURN_URL = null;

    public static String NOTIFY_URL = null;

    static {
        alipayClient = getAlipayClient();
    }

    /**
     * 封装公共请求参数
     *
     * @return AlipayClient
     */
    public static AlipayClient getAlipayClient() {

        if (alipayClient != null) {
            return alipayClient;
        }

        Properties prop = AlipayConfig.getProperties();

        // 网关
        String URL = prop.getProperty("ALIPAY_GATEWAY_URL");
        // 商户APP_ID
        String APP_ID = prop.getProperty("APP_ID");
        // 商户RSA 私钥
        String APP_PRIVATE_KEY = prop.getProperty("RSA2_PRIVATE_KEY");
        // 请求方式 json
        String FORMAT = prop.getProperty("FORMAT");
        // 编码格式，目前只支持UTF-8
        CHARSET = prop.getProperty("CHARSET");
        // 支付宝公钥
        ALIPAY_PUBLIC_KEY = prop.getProperty("ALIPAY_RSA2_PUBLIC_KEY");
        // 签名方式
        SIGN_TYPE = prop.getProperty("SIGN_TYPE");
        // 异步回调地址
        NOTIFY_URL = prop.getProperty("NOTIFY_URL");
        // 同步回调地址
        RETURN_URL = prop.getProperty("RETURN_URL");
        System.out.println("网关==>:" + URL);
        System.out.println("APP_ID==>:" + APP_ID);
        System.out.println("支付宝公钥==>:" + ALIPAY_PUBLIC_KEY);
        System.out.println("商户RSA 私钥==>:" + APP_PRIVATE_KEY);
        System.out.println("FORMAT==>:" + FORMAT);
        System.out.println("字符编码==>:" + CHARSET);
        System.out.println("签名方式==>:" + SIGN_TYPE);
        return new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }
}