function inputTab(selected, callfn) {
	var textbox = document.querySelector(selected),
	inputData,
	dataArr,
	keyCode;
	
	//事件对象
	var EventUtil = {
	  //根据浏览器对象来使用不同的方法添加事件
	  addHandler: function(element, type, handler) {
		if (element.addEventListener) {
		  element.addEventListener(type, handler, true); //dom2级事件处理,在冒泡阶段捕获
		} else if (element.attachEvent) {
		  element.attachEvent("on" + type, handler); //ie事件处理
		} else {
		  element["on" + type] = handler; //dom0级事件处理
		}
	  },
	
	  //取消事件默认行为
	  preventDefault: function(event) {
		if (event.preventDefault) {
		  event.preventDefault();
		} else {
		  event.returnValue = false;
		}
	  },
	
	  //取得event事件对象
	  getEvent: function(event) {
		return event ? event: window.event;
	  },
	
	  //取得输入的字符编码
	  getCharCode: function(event) {
		return event.keyCode;
	  },
	  //使tab键输出在textarea中
	  inputTab: function(event) {
			keyCode = EventUtil.getCharCode(event);
		// EventUtil.preventDefault(event);
		if (keyCode == 9) {
		  EventUtil.preventDefault(event);
		  // textbox.value = textbox.value + '\t';
		  insertText(textbox, '\t');
		}
	  },
	};
	EventUtil.addHandler(textbox, "keydown",
	function() {
	  EventUtil.inputTab(EventUtil.getEvent());
	});
}


function insertText(obj,str) {
    if (document.selection) {
        var sel = document.selection.createRange();
        sel.text = str;
    } else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
        var startPos = obj.selectionStart,
            endPos = obj.selectionEnd,
            cursorPos = startPos,
            tmpStr = obj.value;
        obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
        cursorPos += str.length;
        obj.selectionStart = obj.selectionEnd = cursorPos;
    } else {
        obj.value += str;
    }
}

