package com.pj.project4sp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	/**
	 * json序列化对象 
	 */
	@Autowired
	public static ObjectMapper objectMapper;
	
	// 注入 
	@Autowired
	public void setBean(
			PublicMapper publicMapper,
			PublicService publicService,
			ObjectMapper objectMapper
			) {
		SP.publicMapper = publicMapper;
		SP.publicService = publicService;
		SP.objectMapper = objectMapper;
	}
	
	
	
	
}
