package com.pj.project4sp.apilog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.project4sp.SP;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.so.SoMap;

import cn.dev33.satoken.stp.StpUtil;

/**
 * Controller: api请求记录表
 * @author kong 
 */
@RestController
@RequestMapping("/SgApilog/")
public class SpApilogControlle {

	/** 底层 Mapper 对象 */
	@Autowired
	SpApilogMapper spApilogMapper;
	

	/** 删 */
	@RequestMapping("delete")
	AjaxJson delete(String id){
		StpUtil.checkPermission(AuthConst.APILOG_LIST);
		int line = spApilogMapper.delete(id);
		return AjaxJson.getByLine(line);
	}

	/** 删 - 根据id列表 */
	@RequestMapping("deleteByIds")
	AjaxJson deleteByIds(){
		// 鉴权
		StpUtil.checkPermission(AuthConst.APILOG_LIST);	
		// 开始删除 
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class); 
		int line = SP.publicMapper.deleteByIds("sp_apilog", ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 删 - 根据日期范围 */
	@RequestMapping("deleteByStartEnd")
	AjaxJson deleteByStartEnd(String startTime, String endTime){
		StpUtil.checkPermission(AuthConst.APILOG_LIST);
		int line = spApilogMapper.deleteByStartEnd(startTime, endTime);
		return AjaxJson.getSuccessData(line);
	}
	
	/** 查 - 集合（参数为null或0时默认忽略此条件）   */
	@RequestMapping("getList")
	AjaxJson getList() { 
		StpUtil.checkPermission(AuthConst.APILOG_LIST);	
		SoMap so = SoMap.getRequestSoMap();
		List<SpApilog> list = spApilogMapper.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}

	/** 统计  */
	@RequestMapping("staBy")
	AjaxJson staBy() { 
		StpUtil.checkPermission(AuthConst.APILOG_LIST);
		SoMap so = SoMap.getRequestSoMap();
		SoMap data = spApilogMapper.staBy(so);
		return AjaxJson.getSuccessData(data);
	}
	

	
	
}
