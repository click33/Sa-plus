package com.pj.project4sp.apilog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * API日志插入策略  
 * @author kong
 *
 */
@Component
public class SpApilogInsertTemplate {

	/**
	 * sql操作对象 
	 */
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	/**
	 * 异步插入，不影响请求响应速度 
	 * @param a API日志对象
	 */
	@Async
	public void saveObj(SpApilog a) {
		String sql = "insert into sp_apilog ( "
				+ "id, req_ip, req_api, req_parame, req_type, "
				+ "req_token, req_header, user_id, admin_id, "
				+ "res_code, res_msg, res_string, "
				+ "start_time, end_time, cost_time"
				+ ") values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		Object[] args = {
				a.getId(), a.getReqIp(), a.getReqApi(), a.getReqParame(), a.getReqType(), 
				a.getReqToken(), a.getReqHeader(), a.getUserId(), a.getAdminId(), 
				a.getResCode(), a.getResMsg(), a.getResString(),
				a.getStartTime(), a.getEndTime(), a.getCostTime()
				};
				
		jdbcTemplate.update(sql, args);
	}
	
}
