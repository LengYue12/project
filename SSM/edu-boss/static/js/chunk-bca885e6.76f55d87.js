(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-bca885e6"],{"2a73":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("el-card",{staticClass:"operate-container",attrs:{shadow:"never"}},[a("el-button",{staticClass:"btn-add",attrs:{size:"mini"},on:{click:function(e){return t.handleAdd()}}},[t._v("添加广告位")])],1),a("div",{staticClass:"table-container"},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],ref:"homeAdvertiseTable",staticStyle:{width:"100%"},attrs:{data:t.list,border:""}},[a("el-table-column",{attrs:{label:"spaceKey",width:"120",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.spaceKey))]}}])}),a("el-table-column",{attrs:{label:"广告位名称",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.name))]}}])}),a("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",formatter:t.dateFormatter}}),a("el-table-column",{attrs:{label:"更新时间",align:"center",prop:"updateTime",formatter:t.dateFormatter}}),a("el-table-column",{attrs:{label:"操作",width:"120",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){return t.handleUpdate(e.$index,e.row)}}},[t._v("编辑")])]}}])})],1)],1)],1)},i=[],o=a("ed08"),r=a("c1df"),l=a.n(r),s={name:"homeAdvertiseList",title:"广告位管理",data:function(){return{list:null,listLoading:!1}},created:function(){this.loadPromotionSpace()},methods:{loadPromotionSpace:function(){var t=this;this.listLoading=!0,o["b"].get("/PromotionSpace/findAllPromotionSpace").then((function(e){t.list=e.data.content,t.listLoading=!1})).catch((function(e){t.$message("加载数据失败")}))},handleAdd:function(){this.$router.push({path:"/addAdvertiseSpace"})},handleUpdate:function(t,e){this.$router.push({path:"/updateAdvertiseSpace",query:{id:e.id}})},dateFormatter:function(t,e,a,n){return l()(a).format("YYYY-MM-DD HH:mm:ss")}}},c=s,d=(a("be7a"),a("2877")),u=Object(d["a"])(c,n,i,!1,null,"fbd90bba",null);e["default"]=u.exports},be7a:function(t,e,a){"use strict";var n=a("d45a"),i=a.n(n);i.a},d45a:function(t,e,a){}}]);