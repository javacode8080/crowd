package com.company.crowd.service.api;

import java.util.List;

import com.company.crowd.entity.vo.DetailProjectVO;
import com.company.crowd.entity.vo.PortalTypeVO;
import com.company.crowd.entity.vo.ProjectVO;

public interface ProjectService {

	void saveProject(ProjectVO projectVO, Integer memberId);
	
	List<PortalTypeVO> getPortalTypeVO();
	
	DetailProjectVO getDetailProjectVO(Integer projectId);

}
