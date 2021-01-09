package com.pj.project4sp;

import com.pj.project4sp.admin.SpAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.project4sp.public4mapper.PublicMapper;
import com.pj.project4sp.public4mapper.PublicService;
import org.springframework.stereotype.Repository;

/**
 * 公共Mapper 与 公共Service 
 * @author kong
 *
 */
@Component
@Repository
public class SP {
	

	/**
	 * 公共Mapper
	 */
	public static PublicMapper publicMapper;

	/**
	 * 管理员账号 Mapper
	 */
	public static SpAdminMapper spAdminMapper;
	/**
	 * 公共Service
	 */
	public static PublicService publicService;				
	

	@Autowired
	public void setBean(
			PublicMapper publicMapper,
			SpAdminMapper spAdminMapper,

			PublicService publicService
			) {
		SP.publicMapper = publicMapper;
		SP.spAdminMapper = spAdminMapper;

		SP.publicService = publicService;
	}
	
	
	
	
}
