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
	 * 获取指定角色的所有权限码 【增加缓存】
	 */
    @Cacheable(value="api_pcode_list", key="#roleId")	
    public List<String> getPcodeByRid(long roleId){
    	return spRolePermissionMapper.getPcodeByRoleId(roleId);
    }

	/**
	 * 获取指定角色的所有权限码 (Object类型)  【增加缓存】
	 */
    @Cacheable(value="api_pcode_list2", key="#roleId")	
    public List<Object> getPcodeByRid2(long roleId){
		List<String> codeList = spRolePermissionMapper.getPcodeByRoleId(roleId);					
		return codeList.stream().map(String::valueOf).collect(Collectors.toList());				
    }

    /**
     * [T] 修改角色的一组权限关系	【清除缓存 】
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value= {"api_pcode_list", "api_pcode_list2"}, key="#roleId")	
    public int updateRoleMenu(long roleId, String[] pcodeArray){

    	// 万一为空 
    	if(pcodeArray == null){
    		pcodeArray = new String[0];
    	}
    	
    	// 先删
    	spRolePermissionMapper.deleteByRoleId(roleId);
    	
    	// 再添加
    	for(String pcode : pcodeArray){
    		spRolePermissionMapper.add(roleId, pcode);
        }
    	
    	// 返回
        return pcodeArray.length;
    }
	
	
	
}
