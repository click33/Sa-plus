<template>
  <!-- 自定义slot -->
  <div v-if="$slots.default" class="c-item" :class="{br: br}">
    <label v-if="name && name.length > 0" class="c-label">{{ name }}：</label> 
    <span v-else-if="name === undefined" /> 
    <label v-else class="c-label" /> 
    <span v-else /> 
    <slot />
  </div>
  <!-- 普通信息 -->
  <div v-else-if="type == 'text'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <span>{{ value }}</span>
    <span v-if="sa.isNull(value)">无</span>
  </div>
  <!-- num -->
  <div v-else-if="type == 'num'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <span class="tc-num">{{ value }}</span>
    <span v-if="sa.isNull(value)">无</span>
  </div>
  <!-- textarea -->
  <div v-else-if="type == 'textarea'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <span class="c-item-mline">{{ value }}</span>
    <span v-if="sa.isNull(value)">无</span>
  </div>
  <!-- img -->
  <div v-else-if="type == 'img'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <img v-if="value" :src="value" class="info-img" @click="sa.showImage(value, '400px', '400px')">
    <span v-else>无</span>
  </div>
  <!-- audio、video、file -->
  <div v-else-if="type == 'audio' || type == 'video' || type == 'file'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <el-link v-if="!sa.isNull(value)" type="info" :href="value" target="_blank">{{ value }}</el-link>
    <span v-else>无</span>
  </div>
  <!-- img-list -形如：url1,url2,url3 -->
  <div v-else-if="type == 'img-list'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <div v-if="value_arr.length > 0" class="c-item-mline image-box image-box-info">
      <div v-for="image in value_arr" class="image-box-2">
        <img :src="image" @click="sa.showImage(image, '500px', '400px')">
      </div>
    </div>
    <span v-else>无</span>
  </div>
  <!-- audio-list、video-list、file-list、img-video-list -->
  <div v-else-if="type == 'audio-list' || type == 'video-list' || type == 'file-list' || type == 'img-video-list'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <div v-if="value_arr.length > 0" class="c-item-mline">
      <div v-for="item in value_arr">
        <el-link type="info" :href="item" target="_blank">{{ item }}</el-link>
      </div>
    </div>
    <span v-else>无</span>
  </div>
  <!-- 钱 money (单位 元) -->
  <div v-else-if="type == 'money'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <b class="c-price">￥{{ value }}</b>
  </div>
  <!-- 钱 price-f (单位 分) -->
  <div v-else-if="type == 'money-f'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <b class="c-price">￥{{ value / 100 }}</b>
  </div>
  <!-- 富文本 richtext f -->
  <div v-else-if="type == 'richtext' || type == 'f'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <div class="editor-box content-box-info c-item-mline">
      <div v-html="value" />
    </div>
  </div>
  <!-- 显示枚举 j、num -->
  <div v-else-if="type == 'enum' || type == 'j' || type == 'switch'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <span v-for="j in jvList" :key="j.key">
      <b v-if="value == j.key" :style="{color: j.color || '#303236'}">{{ j.value }}</b>
    </span>
  </div>
  <!-- link -->
  <div v-else-if="type == 'link'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <!-- <span class="c-item-mline">{{value}}</span> -->
    <el-link v-if="!sa.isNull(value)" type="primary" :href="value" target="_blank">{{ value }}</el-link>
    <span v-else>无</span>
  </div>
	
  <!-- 日期 -->
  <div v-else-if="type == 'date'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <span class="tc-date">{{ sa.forDate(value, 1) }}</span>
    <span v-if="sa.isNull(value)">无</span>
  </div>
  <!-- 日期时间 -->
  <div v-else-if="type == 'datetime'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <span class="tc-date">{{ sa.forDate(value, 2) }}</span>
    <span v-if="sa.isNull(value)">无</span>
  </div>
  <!-- 时间 -->
  <div v-else-if="type == 'time'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <span class="tc-date">{{ value }}</span>
    <span v-if="sa.isNull(value)">无</span>
  </div>
	
  <!-- 评分组件 -->
  <div v-else-if="type == 'rate'" class="c-item" :class="{br: br}">
    <label class="c-label">{{ name }}：</label> 
    <div style="display: inline-block;">
      <el-rate :value="value <= 5 ? value : 5" show-text disabled />
      <span v-if="sa.isNull(value)">无</span>
    </div>
  </div>
	
</template>

<script>
module.exports = {
  // props: ['name', 'value'],
  props: {
    // text、num、
    type: {
      default: 'text'
    },
    // label提示文字
    name: {
      type: String
    },
    // 绑定的值 
    value: {},
    // 提示文字
    placeholder: {},
    // 是否禁用
    disabled: {},
    // 是否换行 
    br: {
      type: Boolean,
      default: false
    },
    // type=menu时，值列表    -- 形如：{1: '正常[green]', 2: '禁用[red]'}  
    jv: { default: '' },
    // type=menu时，具体的枚举类型 -- 1=单选框，2=单选文字，3=单选按钮，4=单选下拉框
    jtype: { default: 1 },
    // 级联选择的数据列表
    options: {},
    // 快捷按钮显示列表，形如：add,get,delete,export,reset 
    show: {},	
    // 分页信息 
    curr: {}, size: {}, total: {}, sizes: {}, 
    // 空值时显示的文字
    not: { default: '无' }
			
  },
  data() {
    return {
      // 日期范围时的值 
      dateRangeValue: [],
      // 快捷按钮显示按钮列表 
      showBtns: [],
      // type=menu时，解析的值列表    -- 形如：[{key: 1, value: '正常', color: 'green'}]
      jvList: [],
      // type = img-list 时，解析的元素List
      value_arr: []
    }
  },
  watch: {
    // 监听一些类型的 value 变动 
    value: function(oldValue, newValue) {
      // img-list、audio-list、video-list、file-list、img-video-list
      if (this.type == 'img-list' || this.type == 'audio-list' || this.type == 'video-list' || this.type == 'file-list' || this.type == 'img-video-list') {
        this.value_to_arr(this.value); 
      }
    },
  },
  methods: {
    // 解析枚举 
    parseJv: function() {
      for (let key in this.jv) {
        let value = this.jv[key];
        let color = '';
        // 
        if (value.indexOf('[') != -1 && value.endsWith(']')) {
          let index = value.indexOf('[');
          color = value.substring(index + 1, value.length - 1);
          value = value.substring(0, index);
          // console.log(color + ' --- ' + value);
        }
        // 
        if (isNaN(key) == false) {
          key = parseInt(key);
        }
        // 
        this.jvList.push({
          key: key,
          value: value,
          color: color
        })
      }
    },
    // 解析 value 为 value_arr
    value_to_arr: function(value) {
      this.value_arr = sa.isNull(value) ? [] : value.split(',');		
      for (var i = 0; i < this.value_arr.length; i++) {
        if (this.value_arr[i] == '' || this.value_arr[i].trim() == '') {
          sa.arrayDelete(this.value_arr, this.value_arr[i]);
          i--;
        }
      }
      console.log('长度：' + this.value_arr.length);
    },
			
  },
  created() {
    // console.log(this.br);
    if (this.type == 'fast-btn') {
      this.showBtns = this.show.split(',');
      for (var i = 0; i < this.showBtns.length; i++) {
        this.showBtns[i] = this.showBtns[i].trim();
      }
    }
    // 如果是枚举
    if (this.type == 'enum' || this.type == 'j' || this.type == 'switch') {	
      this.parseJv();
      console.log(this.jvList);
    }
    // 如果是 img-list 等 
    if (this.type == 'img-list' || this.type == 'audio-list' || this.type == 'video-list' || this.type == 'file-list' || this.type == 'img-video-list') {
      this.value_to_arr(this.value);
    }
  }
}
</script>

<style scoped>
</style>
