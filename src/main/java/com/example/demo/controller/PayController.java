package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.example.demo.common.Result;
import com.example.demo.config.DefaultAlipayClientFactory;
import com.example.demo.dao.PayMapper;
import com.example.demo.domain.dto.PayDTO;
import com.example.demo.domain.entity.Orders;
import com.example.demo.domain.entity.PayRecord;
import com.example.demo.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @Author:zy
 * @Description:支付controller
 * @Date: 2019/8/13 17:23
 * @Param:
 * @return
 **/
@Controller
@RequestMapping(value = "pay")
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private PayMapper payMapper;

    @PostMapping(value = "/toPay")
    @ResponseBody
    public Result pay(HttpServletRequest httpRequest, HttpServletResponse httpResponse, PayDTO payDTO)
            throws ServletException, IOException {
        if (ObjectUtils.isEmpty(payDTO.getUserId())) {
            return Result.error("userId为空");
        }
        if (ObjectUtils.isEmpty(payDTO.getOrderId())) {
            return Result.error("orderId为空");
        }
        //付款之前,订单表已经有订单了。。。
        Orders orders = payService.getOrderInfoById(payDTO);
        if (ObjectUtils.isEmpty(orders)) {
            return Result.error("该订单不存在");
        }
        if (orders.getOrderStatus() != 1) {
            return Result.error("该订单非待支付状态或已过期，请重新下单");
        }
        //payDTO.setSubject(orders.getProductName());
        //payDTO.setTotalAmount(orders.getActualMoney());
        //新增支付交易记录
        payService.insertPayRecord(payDTO, orders.getOrderNum());
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = DefaultAlipayClientFactory.alipayClient;
        // 创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 在公共参数中设置回跳和通知地址
        alipayRequest.setReturnUrl(DefaultAlipayClientFactory.RETURN_URL);
        alipayRequest.setNotifyUrl(DefaultAlipayClientFactory.NOTIFY_URL);
        alipayRequest.setBizContent("{"
                + "    \"out_trade_no\":\"" + orders.getOrderNum() + "\","
                + "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","
                + "    \"total_amount\":" + payDTO.getTotalAmount().toString() + ","
                + "    \"subject\":\"" + payDTO.getSubject() + "\""
                + "  }");// 填充业务参数
        String form = "";
        System.out.println(alipayClient);
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + DefaultAlipayClientFactory.CHARSET);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
        return null;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(String outTradeNo, String tradeNo) throws AlipayApiException {
        AlipayClient alipayClient = DefaultAlipayClientFactory.alipayClient;
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +
                "\"trade_no\":\"" + tradeNo + "\"" +
                "  }");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        Object parse = JSONObject.parse(response.getBody());
        System.out.println(parse);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return parse;
    }

    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public void returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("#################################同步回调######################################");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println("params" + params);
        String alipayPublicKey = DefaultAlipayClientFactory.ALIPAY_PUBLIC_KEY;
        String charset = DefaultAlipayClientFactory.CHARSET;
        String signType = DefaultAlipayClientFactory.SIGN_TYPE;
        System.out.println("alipayPublicKey" + alipayPublicKey);
        System.out.println("charset" + charset);
        System.out.println("signType" + signType);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset, signType);

        // ——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号=" + out_trade_no);
            System.out.println("支付宝交易号=" + trade_no);
            System.out.println("付款金额=" + total_amount);
            response.getWriter().write(
                    "trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" + total_amount);
        } else {
            response.getWriter().write("验签失败");
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * @return
     * @Author:gyy
     * @Description:支付异步回调
     * @Date: 2019/8/16 11:42
     * @Param:
     **/
    @RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response)
            throws AlipayApiException, IOException {
        System.out.println("#################################异步回调######################################");
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println("params" + params);
        String alipayPublicKey = DefaultAlipayClientFactory.ALIPAY_PUBLIC_KEY;
        String charset = DefaultAlipayClientFactory.CHARSET;
        String signType = DefaultAlipayClientFactory.SIGN_TYPE;
        System.out.println("alipayPublicKey" + alipayPublicKey);
        System.out.println("charset" + charset);
        System.out.println("signType" + signType);
        // 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset, signType);

        // ——请在这里编写您的程序（以下代码仅作参考）——

        /*
         * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
         * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
         * 4、验证app_id是否为该商户本身。
         */
        if (signVerified) {// 验证成功
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号=" + out_trade_no);
            System.out.println("支付宝交易号=" + trade_no);
            System.out.println("交易状态=" + trade_status);
            Orders orders = new Orders();
            //查询该订单最新的支付记录
            PayRecord payRecord = this.payMapper.getOrderPayRecord(out_trade_no);
            orders.setOrderNum(out_trade_no);
            //找到支付记录id好修改
            payRecord.setId(payRecord.getId());
            payRecord.setPaySerialNumber(trade_no);
            if (trade_status.equals("TRADE_FINISHED")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意：
                // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意：
                // 付款完成后，支付宝系统发送该交易状态通知
                //支付成功，维护订单状态
                orders.setOrderStatus(2);
                orders.setUpdateTime(new Date());
                orders.setPayTime(new Date());
                orders.setPayType(0);
                payMapper.updateOrderStatus(orders);
                //维护支付流水状态
                payRecord.setPayStatus(1);
            } else {
                //维护支付流水状态
                payRecord.setPayStatus(2);
            }
            //修改支付流水信息
            this.payMapper.updatePayRecordStatus(payRecord);
            System.out.println("异步回调验证成功");
            response.getWriter().write("success");
        } else {// 验证失败
            System.out.println("异步回调验证失败");
            response.getWriter().write("fail");
            // 调试用，写文本函数记录程序运行情况是否正常
            // String sWord = AlipaySignature.getSignCheckContentV1(params);
            // AlipayConfig.logResult(sWord);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
