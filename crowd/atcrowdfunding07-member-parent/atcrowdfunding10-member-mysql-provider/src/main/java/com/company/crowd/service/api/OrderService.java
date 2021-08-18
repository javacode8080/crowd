package com.company.crowd.service.api;

import java.util.List;

import com.company.crowd.entity.vo.AddressVO;
import com.company.crowd.entity.vo.OrderProjectVO;
import com.company.crowd.entity.vo.OrderVO;

public interface OrderService {

	OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

	List<AddressVO> getAddressVOList(Integer memberId);

	void saveAddress(AddressVO addressVO);

	void saveOrder(OrderVO orderVO);

}
