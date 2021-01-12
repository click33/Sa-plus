// package com.pj.project4sp.websocket;
//
// import cn.dev33.satoken.stp.StpUtil;
// import com.alibaba.fastjson.JSON;
// import com.pj.project4sp.SP;
// import com.pj.utils.so.SoMap;
// import org.springframework.stereotype.Component;
// import javax.websocket.*;
// import javax.websocket.server.PathParam;
// import javax.websocket.server.ServerEndpoint;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// /**
//  * description: AdminWebsocket
//  * date: 2021/1/9 16:45
//  *
//  * @author zz
//  */
// @Component
// @ServerEndpoint(value = "/adminWebsocket/{adminId}")
// public class AdminWebsocket {
//
//     public Session session;
//
//     public Integer adminId;
//
//     public static List<AdminWebsocket> adminWebsocketList = new ArrayList<>();
//
//
//     @OnOpen
//     public void onOpen(Session session, @PathParam("adminId") Integer adminId) throws IOException {
//
//         this.session = session;
//         this.adminId = adminId;
//         // 保存连接
//         adminWebsocketList.add(this);
//         session.setMaxIdleTimeout(20000);
//     }
//
//     @OnClose
//     public void onClose(){
//
//         // 删除关闭连接
//         for (int i = 0 ; i < adminWebsocketList.size() ; i++){
//             if (this.equals(adminWebsocketList.get(i))){
//                 adminWebsocketList.remove(i);
//                 break;
//             }
//         }
//         System.err.println("关闭连接");
//     }
//
//     @OnError
//     public void onError(Session session, Throwable error) {
//
//         System.err.println("数据异常");
//         error.printStackTrace();
//     }
//
//     @OnMessage
//     public void onMessage(String message){
//
//         // 接收的消息
//         Map<String, Object> map = JSON.parseObject(message);
//         // 发送的消息
//         Map<String ,Object> returnMap = new HashMap<>();
//
//         // *********************************************** 后 台 管 理 ***************************************************
//         if ("admin".equals(map.get("ofType"))){
//
//             returnMap.put("ofType", "admin");
//             if (StpUtil.hasPermission(adminId, (String) returnMap.get("ofType"))) {
//                 SoMap soMap = SoMap.getSoMap();
//                 soMap.setMap(map);
//                 soMap.startPage();
//                 returnMap.put("data", SP.spAdminMapper.getList(soMap));
//                 returnMap.put("dataCount", soMap.getDataCount());
//                 returnMap.put("code", 200);
//             }else{
//                 returnMap.put("code",400);
//                 returnMap.put("info","admin权限不足");
//             }
//
//             sendMessageTo(returnMap);
//         }
//         //*************************************
//
//     }
//
//     /**
//      * 返回前端消息
//      */
//     public synchronized void sendMessageTo(Map<String, Object> returnMap) {
//
//         if (session.isOpen()) {
//             String jsonString = JSON.toJSONString(returnMap);
//             try {
//                 System.err.println(jsonString);
//                 session.getBasicRemote().sendText(jsonString);
//             } catch (IOException e) {
//                 // TODO 自动生成的 catch 块
//                 e.printStackTrace();
//             }
//         }
//     }
// }
