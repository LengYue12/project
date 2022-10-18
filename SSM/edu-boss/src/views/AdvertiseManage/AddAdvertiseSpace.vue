<template>
  <home-advertise-detail :isEdit="false"></home-advertise-detail>
</template>

<script>
// 引入组件   添加或修改广告位页面
import HomeAdvertiseDetail from "./AdvertiseSpaceDetail";

export default {
  name: "addHomeAdvertise",
  title: "添加广告位",
  components: { HomeAdvertiseDetail }
};
</script>
<style></style>
