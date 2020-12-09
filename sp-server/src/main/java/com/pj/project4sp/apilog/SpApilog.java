package com.pj.project4sp.apilog;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Model: api请求记录表
 * @author kong 
 */
@Data
public class SpApilog implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 记录id  */
	private String id;	
	
	/** 客户端ip  */
	private String reqIp;	
	
	/** 请求api  */
	private String reqApi;		
	
	/** 请求参数  */
	private String reqParame;	
	
	/** 请求方式 */
	private String reqType;	
	
	/** 请求token */
	private String reqToken;	
	
	/** 请求header */
	private String reqHeader;	

	
	/** 返回-状态码  */
	private int resCode;	
	
	/** 返回-信息描述  */
	private String resMsg;		
	
	/** 返回-整个信息字符串形式  */
	private String resString;		

	
	/**  user_id  */
	private long userId;		
	
	/** admin_id  */
	private long adminId;		

	/** 请求开始时间   */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone="GMT+8")
	private Date startTime;	
	/** 请求结束时间   */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone="GMT+8")
	private Date endTime;	
	/** 花费时间，单位ms   */
	private int costTime;	
	
	
	/**
	 * 构造一个普通 实体类
	 */
	public SpApilog() {}
	
	// 构造一个 save 实体类 
	public SpApilog(String id, String reqIp, String reqApi, String reqParame, String reqToken, long userId, long adminId) {
		super();
		this.id = id;
		this.reqIp = reqIp;
		this.reqApi = reqApi;
		this.reqParame = reqParame;
		this.reqToken = reqToken;
		this.userId = userId;
		this.adminId = adminId;
	}

	// 构造一个 update 实体类 
	public SpApilog(String id, int resCode, String resMsg, String resString, int costTime) {
		super();
		this.id = id;
		this.resCode = resCode;
		this.resMsg = resMsg;
		this.resString = resString;
		this.costTime = costTime;
	}

	
	
	
	

}
