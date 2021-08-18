package com.company.crowd.mapper;

import com.company.crowd.entity.po.OrderProjectPO;
import com.company.crowd.entity.po.OrderProjectPOExample;
import com.company.crowd.entity.vo.OrderProjectVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

//@Component
public interface OrderProjectPOMapper {
    int countByExample(OrderProjectPOExample example);

    int deleteByExample(OrderProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderProjectPO record);

    int insertSelective(OrderProjectPO record);

    List<OrderProjectPO> selectByExample(OrderProjectPOExample example);

    OrderProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderProjectPO record, @Param("example") OrderProjectPOExample example);

    int updateByExample(@Param("record") OrderProjectPO record, @Param("example") OrderProjectPOExample example);

    int updateByPrimaryKeySelective(OrderProjectPO record);

    int updateByPrimaryKey(OrderProjectPO record);
    
    OrderProjectVO selectOrderProjectVO(Integer returnId);
}