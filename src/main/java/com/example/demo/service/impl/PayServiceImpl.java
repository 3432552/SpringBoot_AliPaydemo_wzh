package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.PayMapper;
import com.example.demo.domain.dto.PayDTO;
import com.example.demo.domain.entity.Orders;
import com.example.demo.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:cwd
 * @Description:订单业务层
 * @Date: 2019/7/5 17:30
 * @Param:
 * @return
 **/
@Service
@Slf4j
public class PayServiceImpl extends ServiceImpl<PayMapper, Orders> implements PayService {

    @Autowired
    private PayMapper payMapper;

    /**
     * 根据用户id、订单id获取订单信息
     *
     * @param payDTO
     * @return
     */
    @Override
    public Orders getOrderInfoById(PayDTO payDTO) {
        Orders orders1 = payMapper.selectOne(new LambdaQueryWrapper<Orders>().
                eq(Orders::getOrderNum, payDTO.getOrderId()).
                eq(Orders::getMemberId, payDTO.getUserId()));
        return orders1;
    }

    /**
     * 新增支付交易记录
     *
     * @param payDTO
     * @param orderNum
     */
    @Override
    public void insertPayRecord(PayDTO payDTO, String orderNum) {
        try {
            payMapper.insertPayRecord(payDTO.getUserId(), orderNum, payDTO.getTotalAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
