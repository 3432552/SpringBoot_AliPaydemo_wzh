package com.example.demo.service;

import com.example.demo.domain.dto.PayDTO;
import com.example.demo.domain.entity.Orders;

/**
  * @Author:cwd
  * @Description:订单service
  * @Date: 2019/7/5 17:29
  * @Param:
  * @return
  **/
public interface PayService {

    /**
     * 根据用户id、订单id获取订单信息
     * @param payDTO
     * @return
     */
    Orders getOrderInfoById(PayDTO payDTO);

    /**
     * 新增支付交易记录
     * @param payDTO
     * @param orderNum
     */
    void insertPayRecord(PayDTO payDTO, String orderNum);
}
