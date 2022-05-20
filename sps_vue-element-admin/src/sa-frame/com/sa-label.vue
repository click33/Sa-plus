<template>
  <div :class="{br: br}" class="c-item">
    <label class="c-label">{{ name }}：</label>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "SaLabel",
  props: {
    // 是否换行
    br: {
      type: Boolean,
      default: false
    },
    // label提示文字
    name: {
      type: String
    }
  }
}
</script>
