package com.pj.project4sp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.project4sp.public4mapper.PublicMapper;
import com.pj.project4sp.public4mapper.PublicService;

/**
 * 公共Mapper 与 公共Service 
 * @author kong
 *
 */
@Component
public class SP {
	

	/**
	 * 公共Mapper
	 */
	public static PublicMapper publicMapper;	
	/**
	 * 公共Service
	 */
	public static PublicService publicService;				
	
	// 注入 
	@Autowired
	public void setBean(
			PublicMapper publicMapper,
			PublicService publicService
			) {
		SP.publicMapper = publicMapper;
		SP.publicService = publicService;
	}
	
	
	
	
}
