<template>
  <div class="iframe-view-box">
    <iframe class="iframe-view" :src="sa.cfg.api_url + '/druid/sql.html'" />
  </div>
</template>

<script>
export default {
  name: 'sql-console',
  data() {
    return {

    }
  },
  created() {

  },
  methods: {

  }
}
</script>

<style scoped>
  .iframe-view-box,.iframe-view{
    width: 100%;
    height: calc(100vh - 84px);
  }
  .iframe-view{border: 0px;}
</style>
