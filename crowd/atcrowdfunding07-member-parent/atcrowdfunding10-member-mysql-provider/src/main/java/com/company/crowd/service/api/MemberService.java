package com.company.crowd.service.api;

import com.company.crowd.entity.po.MemberPO;

public interface MemberService {

	MemberPO getMemberPOByLoginAcct(String loginacct);

	void saveMember(MemberPO memberPO);

}
