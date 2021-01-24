package com.pj.project4sp.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * description: AdminWebsocketService
 * date: 2021/1/11 12:01
 *
 * @author jh
 */
@Service
public class AdminWebsocketService {

    /**
     * 向后台管理主动发送
     * @param ofType  鉴权名
     */
    @Async
    public void sendType(String ofType){

        System.err.println(ofType);
        Map<String ,Object> map = new HashMap<>();

        map.put("ofType",ofType);
        map.put("pageNo",1);
        map.put("pageSize",10);
        for (AdminWebsocket admin : AdminWebsocket.adminWebsocketList) {
            admin.onMessage(JSON.toJSONString(map));
        }
    }
}
