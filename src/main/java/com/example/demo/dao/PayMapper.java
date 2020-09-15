package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.entity.Orders;
import com.example.demo.domain.entity.PayRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
  * @Author:gyy
  * @Description:用户mapper层
  * @Date: 2019/7/5 17:30
  * @Param:
  * @return
  **/
@Repository
public interface PayMapper extends BaseMapper<Orders> {

    /**
     * 新增支付交易记录
     * @param userId
     * @param orderNum
     * @param totalAmount
     */
    void insertPayRecord(@Param("userId") Long userId, @Param("orderNum") String orderNum, @Param("totalAmount") BigDecimal totalAmount);

    /**
     * 查询该订单的支付记录
     * @param outTradeNo
     * @return
     */
    PayRecord getOrderPayRecord(@Param("outTradeNo") String outTradeNo);

    /**
     * 修改支付流水信息
     * @param payRecord
     */
    void updatePayRecordStatus(PayRecord payRecord);

    /**
     * 修改订单的状态
     * @param orders
     */
    void updateOrderStatus(Orders orders);

}
