window.sa_IM = {};
var Socket;
var adminId;
(function (){
	// var im = IM;
	sa_IM.id = function c(id){
		console.log(id)
	};
	sa_IM.login = function login(id){
		
		adminId = id;
		// 连接websocket
		this.Socket= new WebSocket(sa.cfg.ws_url+"/adminWebsocket/"+id);
		
		// this.Socket.onmessage = message;
		this.Socket.onopen = open;
		this.Socket.onclose = close;
	};
	
	// 发送信息
	sa_IM.send = function send(data){
		this.Socket.send(JSON.stringify(data));
	};
	
	// 接收消息
	sa_IM.message = function message(){
		return this.Socket;
	};
})();




var boo;
// 连接成功
function open(){
	console.log("连接成功");
	boo = true;
	setdate();
}
// 连接断开
function close(){
	console.log("连接断开");
	
	boo = false;
	setTimeout(function(){
		sa_IM.login(adminId);
	},3000);
}
// 心跳
function setdate(){
	if(boo){
		sa_IM.send({"type":"setdate"})
		setTimeout(function(){
			setdate()
		},3000);
	}
}

// 通用js
(function(){
	sa_IM.date = function date(date){
		date = new Date(date);
		var y=date.getFullYear();
		var m=date.getMonth()+1;
		var d=date.getDate();
		var h=date.getHours();
		var m1=date.getMinutes();
		
		
		var newdate = new Date();
		var ny=newdate.getFullYear();
		var nm=newdate.getMonth()+1;
		var nd=newdate.getDate();
		var nh=newdate.getHours();
		var nm1=newdate.getMinutes();
		
		if(y == ny){
			if(m == nm){
				if(d == nd-2){
					return '前天'
				}
				if(d == nd-1){
					return '昨天'
				}
				if(d == nd){
					m1 = m1<10?("0"+m1):m1;
					return h+":"+m1;
				}
				
			}
		}
		m = m<10?("0"+m):m;
		d = d<10?("0"+d):d;
		return y+"年"+m+"月"+d+"日";
	}
})()


if(window.Vue) {
	Vue.prototype.$sa_IM = sa_IM;
}


