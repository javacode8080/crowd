package com.company.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalProjectVO {
	
	private Integer projectId;
	private String projectName;
	private String headerPicturePath;
	private Integer money;
	private String deployDate;//发布日期
	private Integer percentage;//筹集百分比
	private Integer supporter;//支持人数

}
