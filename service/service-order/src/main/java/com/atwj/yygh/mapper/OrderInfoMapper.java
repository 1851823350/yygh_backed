package com.atwj.yygh.mapper;

import com.atwj.yygh.model.order.OrderInfo;
import com.atwj.yygh.vo.order.OrderCountQueryVo;
import com.atwj.yygh.vo.order.OrderCountVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-15 12:26
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
