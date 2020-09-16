package com.pj.project4sp.role4permission;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色权限中间表 
 * @author kong
 *
 */
@Service
public class SpRolePermissionService {

	
	@Autowired
	SpRolePermissionMapper spRolePermissionMapper;
	
	
	/**
	 * 获取指定角色的所有权限码 
	 */
    @Cacheable(value="api_pcode_list", key="#role_id")	// @增加缓存
    public List<String> getPcodeByRid(long role_id){
    	return spRolePermissionMapper.getPcodeByRoleId(role_id);
    }

	/**
	 * 获取指定角色的所有权限码 (Object类型) 
	 */
    @Cacheable(value="api_pcode_list2", key="#role_id")	// @增加缓存
    public List<Object> getPcodeByRid2(long role_id){
		List<String> codeList = spRolePermissionMapper.getPcodeByRoleId(role_id);					// 所有权限id  
		return codeList.stream().map(String::valueOf).collect(Collectors.toList());				// 转Object 
    }

    /**
     * [T] 修改角色的一组权限关系
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value= {"api_pcode_list", "api_pcode_list2"}, key="#role_id")	// @清除缓存 
    public int updateRoleMenu(long role_id, String[] pcodeArray){

    	// 万一为空 
    	if(pcodeArray == null){
    		pcodeArray = new String[0];
    	}
    	
    	// 先删
    	spRolePermissionMapper.deleteByRoleId(role_id);
    	
    	// 再添加
    	for(String pcode : pcodeArray){
    		spRolePermissionMapper.add(role_id, pcode);
        }
    	
    	// 返回
        return pcodeArray.length;
    }
	
	
	
}
