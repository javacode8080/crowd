package com.company.crowd.handler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.crowd.api.MySQLRemoteService;
import com.company.crowd.config.OSSProperties;
import com.company.crowd.constants.CrowdConstant;
import com.company.crowd.entity.vo.DetailProjectVO;
import com.company.crowd.entity.vo.MemberConfirmInfoVO;
import com.company.crowd.entity.vo.MemberLoginVO;
import com.company.crowd.entity.vo.ProjectVO;
import com.company.crowd.entity.vo.ReturnVO;
import com.company.crowd.util.CrowdUtil;
import com.company.crowd.util.ResultEntity;

@Controller
public class ProjectConsumerHandler {
	
	@Autowired
	private OSSProperties ossProperties;
	
	@Autowired
	private MySQLRemoteService mySQLRemoteService;
	
	@RequestMapping("/get/project/detail/{projectId}")
	public String getProjectDetail(@PathVariable("projectId") Integer projectId, Model model) {
		
		ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);
		
		if(ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
			DetailProjectVO detailProjectVO = resultEntity.getData();
			
			model.addAttribute("detailProjectVO", detailProjectVO);
		}
		
		return "project-show-detail";
	}
	
	@RequestMapping("/create/confirm")
	public String saveConfirm(ModelMap modelMap, HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO) {
		
		// 1.???Session??????????????????????????????ProjectVO??????
		ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
		
		// 2.??????projectVO???null
		if(projectVO == null) {
			throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
		}
		
		// 3.??????????????????????????????projectVO?????????
		projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
		
		// 4.???Session??????????????????????????????
		MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		
		Integer memberId = memberLoginVO.getId();
		
		// 5.????????????????????????projectVO??????
		ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO, memberId);
		
		// 6.???????????????????????????????????????
		String result = saveResultEntity.getResult();
		if(ResultEntity.FAILED.equals(result)) {
			
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
			
			return "project-confirm";
		}
		
		// 7.????????????ProjectVO?????????Session?????????
		session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
		
		// 8.??????????????????????????????????????????????????????
		return "redirect:http://localhost/project/create/success";
	}
	
	@ResponseBody
	@RequestMapping("/create/save/return.json")
	public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
		
		try {
			// 1.???session???????????????????????????ProjectVO??????
			ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
			
			// 2.??????projectVO?????????null
			if(projectVO == null) {
				return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
			}
			
			// 3.???projectVO??????????????????????????????????????????
			List<ReturnVO> returnVOList = projectVO.getReturnVOList();
			
			// 4.??????returnVOList??????????????????
			if(returnVOList == null || returnVOList.size() == 0) {
				
				// 5.?????????????????????returnVOList???????????????
				returnVOList = new ArrayList<>();
				// 6.?????????????????????????????????????????????????????????projectVO?????????
				projectVO.setReturnVOList(returnVOList);
			}
			
			// 7.???????????????????????????returnVO??????????????????
			returnVOList.add(returnVO);
			
			// 8.?????????????????????ProjectVO??????????????????Session?????????????????????????????????????????????Redis
			session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
			
			// 9.????????????????????????????????????
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	// JavaScript?????????formData.append("returnPicture", file);
	// returnPicture????????????????????????
	// file???????????????????????????????????????????????????
	@ResponseBody
	@RequestMapping("/create/upload/return/picture.json")
	public ResultEntity<String> uploadReturnPicture(
			
			// ???????????????????????????
			@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
		String returnPicturePath = upload_pictrue(returnPicture, "return", null);
		if(returnPicturePath==null){
			return ResultEntity.failed("?????????????????????");
		}
		return ResultEntity.successWithData(returnPicturePath);
	/*	// 1.??????????????????
		ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(
				ossProperties.getEndPoint(), 
				ossProperties.getAccessKeyId(), 
				ossProperties.getAccessKeySecret(), 
				returnPicture.getInputStream(), 
				ossProperties.getBucketName(), 
				ossProperties.getBucketDomain(), 
				returnPicture.getOriginalFilename());
		
		// 2.?????????????????????
		return uploadReturnPicResultEntity;*/

	}
	
	@RequestMapping("/create/project/information")
	public String saveProjectBasicInfo(
			
			// ???????????????????????????????????????????????????
			ProjectVO projectVO, 
			
			// ?????????????????????
			MultipartFile headerPicture, 
			
			// ???????????????????????????
			List<MultipartFile> detailPictureList, 
			
			// ????????????????????????????????????ProjectVO????????????Session???
			HttpSession session,
			
			// ??????????????????????????????????????????????????????????????????????????????
			ModelMap modelMap
			) throws IOException {
		
		// ????????????????????????
		// 1.????????????headerPicture??????????????????
		boolean headerPictureIsEmpty = headerPicture.isEmpty();
		
		if(headerPictureIsEmpty) {
			
			// 2.?????????????????????????????????????????????????????????????????????
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
			
			return "project-launch";
			
		}
		// 3.???????????????????????????????????????????????????????????????[???????????????????????????OSS???????????????????????????????????????]
		// 5.????????????????????????????????????????????????????????????
		String headerPicturePath = upload_pictrue(headerPicture, "header", modelMap);
		if(headerPicturePath==null){
			return "project-launch";
		}else{
			// 6.??????ProjectVO?????????
			projectVO.setHeaderPicturePath(headerPicturePath);
		}




		/*	ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
				ossProperties.getEndPoint(), 
				ossProperties.getAccessKeyId(), 
				ossProperties.getAccessKeySecret(), 
				headerPicture.getInputStream(), 
				ossProperties.getBucketName(), 
				ossProperties.getBucketDomain(), 
				headerPicture.getOriginalFilename());
		
		String result = uploadHeaderPicResultEntity.getResult();
		
		// 4.??????????????????????????????
		if(ResultEntity.SUCCESS.equals(result)) {
			
			// 5.????????????????????????????????????????????????????????????
			String headerPicturePath = uploadHeaderPicResultEntity.getData();
			
			// 6.??????ProjectVO?????????
			projectVO.setHeaderPicturePath(headerPicturePath);
		} else {
			
			// 7.???????????????????????????????????????????????????????????????
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
			
			return "project-launch";
			
		}*/
		
		// ????????????????????????
		// 1.???????????????????????????????????????????????????
		List<String> detailPicturePathList = new ArrayList<String>();
		
		// 2.??????detailPictureList????????????
		if(detailPictureList == null || detailPictureList.size() == 0) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
			
			return "project-launch";
		}
		
		// 3.??????detailPictureList??????
		for (MultipartFile detailPicture : detailPictureList) {
			
			// 4.??????detailPicture????????????
			if(detailPicture.isEmpty()) {
				
				// 5.????????????????????????????????????????????????????????????????????????
				modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
				
				return "project-launch";
			}

			// 5.????????????????????????????????????????????????????????????
			String detailPicturePath = upload_pictrue(detailPicture, "detail",modelMap);
			if(detailPicturePath==null){
				return "project-launch";
			}else{
				detailPicturePathList.add(detailPicturePath);
			}
			
		/*	// 6.????????????
			ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(
					ossProperties.getEndPoint(), 
					ossProperties.getAccessKeyId(), 
					ossProperties.getAccessKeySecret(), 
					detailPicture.getInputStream(), 
					ossProperties.getBucketName(), 
					ossProperties.getBucketDomain(), 
					detailPicture.getOriginalFilename());
			
			// 7.??????????????????
			String detailUploadResult = detailUploadResultEntity.getResult();
			
			if(ResultEntity.SUCCESS.equals(detailUploadResult)) {
				
				String detailPicturePath = detailUploadResultEntity.getData();
				
				// 8.??????????????????????????????????????????
				detailPicturePathList.add(detailPicturePath);
			} else {
				
				// 9.???????????????????????????????????????????????????????????????
				modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
				
				return "project-launch";
			}*/
			
		}
		
		// 10.???????????????????????????????????????????????????ProjectVO???
		projectVO.setDetailPicturePathList(detailPicturePathList);
		
		// ??????????????????
		// 1.???ProjectVO????????????Session???
		session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
		
		// 2.??????????????????????????????????????????????????????????????????
		return "redirect:http://localhost/project/return/info/page";
	}

	/**
	 * ???????????????????????????????????????????????????OSS???????????????
	 * @param multipartFile
	 * @param picType
	 * @param modelMap
	 * @return
	 */
	public String upload_pictrue (MultipartFile multipartFile, String picType, ModelMap modelMap){
		//??????????????????
		String name=multipartFile.getOriginalFilename();
		//??????????????????
		String subffix=name.substring(name.lastIndexOf(".")+1,name.length());
		//????????????
		if(subffix.equals("")||(!subffix.equals("jpg")&&!subffix.equals("jpeg")&&!subffix.equals("png")&&!subffix.equals("gif")))
		{
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_PIC_UPLOAD_FAILED_FORMAT_ERROR);
			return null;
		}
		//??????????????????????????????
		Random rand = new Random();//???????????????
		int random = rand.nextInt();
		String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+String.valueOf(random > 0 ? random : ( -1) * random);
		//??????????????????????????????static
		String path = "/JAVA/codeLoding/aaa/static/img/"+picType+"/";
		File file=new File("D:"+path);
		if(!file.exists())//???????????????????????????
		{
			file.mkdirs();
		}
		//????????????
		try {
			multipartFile.transferTo(new File(file+"\\"+fileName+"."+subffix));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//5.???????????????????????????
		String realPath=path+fileName+"."+subffix;
		return realPath;
	}
}
