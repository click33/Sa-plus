package com.pj.project4sp.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.project4sp.SP;
import com.pj.utils.sg.AjaxError;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.so.SoMap;

import cn.dev33.satoken.annotation.SaCheckPermission;

/**
 * Controller: 系统角色表
 * @author kong
 */
@RestController
@RequestMapping("/role/")
public class SpRoleController {

	/** 底层Mapper依赖 */
	@Autowired
	SpRoleMapper spRoleMapper;

	/** 增 */
	@RequestMapping("add")
	@SaCheckPermission(AuthConst.ROLE_LIST)
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson add(SpRole s){
		// 检验
		if(spRoleMapper.getById(s.getId()) != null) {
			return AjaxJson.getError("此id已存在，请更换");
		}
		SpRoleUtil.checkRoleThrow(s);
		int line = spRoleMapper.add(s);
		AjaxError.throwByLine(line, "添加失败");
		// 返回这个对象 
		long id = s.getId();
		if(id == 0) {
			id = SP.publicMapper.getPrimarykey();
		}
		return AjaxJson.getSuccessData(spRoleMapper.getById(id));
	}

	/** 删 */
	@RequestMapping("delete")
	@SaCheckPermission({AuthConst.ROLE_LIST, AuthConst.DEV})
	AjaxJson delete(long id){
		int line = spRoleMapper.delete(id);
		return AjaxJson.getByLine(line);
	}

	/** 改 */ 
	@RequestMapping("update")
	@SaCheckPermission({AuthConst.ROLE_LIST, AuthConst.DEV})
	AjaxJson update(SpRole s){
		SpRoleUtil.checkRoleThrow(s);
		int line = spRoleMapper.update(s);
		return AjaxJson.getByLine(line);
	}

	/** 查 */ 
	@RequestMapping("getById")
	@SaCheckPermission(AuthConst.IN_SYSTEM)
	AjaxJson getById(long id){
		SpRole s = spRoleMapper.getById(id);
		return AjaxJson.getSuccessData(s);
	}

	/** 查 - 集合  */
	@RequestMapping("getList")
	@SaCheckPermission(AuthConst.IN_SYSTEM)
	AjaxJson getList(){
		SoMap so = SoMap.getRequestSoMap();
		List<SpRole> list = spRoleMapper.getList(so);
		return AjaxJson.getSuccessData(list);
	}

}
