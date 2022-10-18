(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["users"],{"4cab":function(e,t,a){"use strict";var l=a("9267"),i=a.n(l);i.a},9267:function(e,t,a){},ed81:function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("section",{staticClass:"users"},[a("el-form",{staticClass:"actions",attrs:{model:e.filter,"label-position":"top",inline:""}},[a("el-form-item",{attrs:{label:"手机号"}},[a("el-input",{attrs:{placeholder:"请输入手机号"},model:{value:e.filter.username,callback:function(t){e.$set(e.filter,"username",t)},expression:"filter.username"}})],1),a("el-form-item",{attrs:{label:"注册时间"}},[a("el-date-picker",{attrs:{type:"daterange","start-placeholder":"开始日期","end-placeholder":"结束日期","range-separator":"至","picker-options":e.pickerOptions},model:{value:e.filter.resTime,callback:function(t){e.$set(e.filter,"resTime",t)},expression:"filter.resTime"}})],1),a("el-form-item",[a("el-button",{on:{click:e.handleFilter}},[e._v("查询")])],1)],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.users,"element-loading-text":"数据加载中..."}},[a("el-table-column",{attrs:{prop:"id",label:"用户ID",width:"100"}}),a("el-table-column",{attrs:{label:"头像","min-width":"50"},scopedSlots:e._u([{key:"default",fn:function(e){return[a("img",{staticClass:"avatar",attrs:{src:e.row.portrait||"//www.lgstatic.com/thumbnail_100x100/i/image2/M01/5E/65/CgotOVszSAOANi0LAAAse2IVWAE693.jpg",alt:e.row.name}})]}}])}),a("el-table-column",{attrs:{prop:"name",label:"用户名","min-width":"150"}}),a("el-table-column",{attrs:{prop:"phone",label:"手机号","min-width":"150"}}),a("el-table-column",{attrs:{prop:"createTime",label:"注册时间","min-width":"180",formatter:e.dateFormatter}}),a("el-table-column",{attrs:{prop:"status",label:"状态",align:"center","min-width":"120"},scopedSlots:e._u([{key:"default",fn:function(t){return["ENABLE"===t.row.status?a("i",{staticClass:"status status-success",attrs:{title:"正常"}}):"DISABLE"===t.row.status?a("i",{staticClass:"status status-danger",attrs:{title:"禁用"}}):e._e()]}}])}),a("el-table-column",{attrs:{label:"操作",align:"center","min-width":"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){return e.handleToggleStatus(t.row)}}},[e._v(e._s("ENABLE"==t.row.status?"禁用":"启用"))]),a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){return e.handleSelectRole(t.row)}}},[e._v("分配角色")])]}}])})],1),e.users.length?a("el-pagination",{attrs:{layout:"total, sizes, prev, pager, next, jumper","current-page":e.page,"page-sizes":[10,20,30],"page-size":e.size,total:e.total},on:{"size-change":e.handlePageSizeChange,"current-change":e.handleCurrentPageChange}}):e._e(),a("el-dialog",{attrs:{title:"分配角色",visible:e.allocDialogVisible,width:"30%"},on:{"update:visible":function(t){e.allocDialogVisible=t}}},[a("el-select",{staticStyle:{width:"80%"},attrs:{multiple:"",placeholder:"请选择",size:"small"},model:{value:e.allocRoleIds,callback:function(t){e.allocRoleIds=t},expression:"allocRoleIds"}},e._l(e.allRoleList,(function(e){return a("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})})),1),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{size:"small"},on:{click:function(t){e.allocDialogVisible=!1}}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(t){return e.handleAllocRole()}}},[e._v("确 定")])],1)],1)],1)},i=[],s=(a("4de4"),a("d81d"),a("b0c0"),a("ed08"));a("e199");var n=a("c1df"),o=a.n(n),r={name:"Users",title:"用户管理",data:function(){var e={shortcuts:[{text:"最近一周",onClick:function(e){var t=new Date,a=new Date;a.setTime(a.getTime()-6048e5),e.$emit("pick",[a,t])}},{text:"最近一个月",onClick:function(e){var t=new Date,a=new Date;a.setTime(a.getTime()-2592e6),e.$emit("pick",[a,t])}},{text:"最近三个月",onClick:function(e){var t=new Date,a=new Date;a.setTime(a.getTime()-7776e6),e.$emit("pick",[a,t])}}]},t={username:"",resTime:null};return{pickerOptions:e,total:0,size:10,page:1,filter:t,users:[],loading:!1,allocAdminId:"",allocDialogVisible:!1,allocRoleIds:[],allRoleList:[],listQuery:{}}},created:function(){this.loadUsers()},methods:{loadUsers:function(){var e=this;this.loading=!0;var t={currentPage:this.page,pageSize:this.size};return this.filter.username&&(t.username=this.filter.username),this.filter.resTime&&(t.startCreateTime=this.filter.resTime[0],t.startCreateTime.setHours(0),t.startCreateTime.setMinutes(0),t.startCreateTime.setSeconds(0),t.endCreateTime=this.filter.resTime[1],t.endCreateTime.setHours(23),t.endCreateTime.setMinutes(59),t.endCreateTime.setSeconds(59)),s["b"].post("/user/findAllUserByPage",t).then((function(t){e.users=t.data.content.list,e.total=t.data.content.total,e.loading=!1})).catch((function(t){e.$message("获取数据失败! ! !")}))},handleToggleStatus:function(e){var t=this;return s["b"].get("/user/updateUserStatus",{params:{id:e.id,status:e.status}}).then((function(t){e.status=t.data.content})).catch((function(e){t.$message("获取数据失败! ! !")}))},handleFilter:function(){this.loadUsers()},handleCurrentPageChange:function(e){this.page=e,this.loadUsers()},handlePageSizeChange:function(e){this.size=e,this.loadUsers()},dateFormatter:function(e,t,a,l){return o()(a).format("YYYY-MM-DD HH:mm:ss")},handleSelectRole:function(e){this.allocAdminId=e.id,this.getRoleList(),this.getUserRoleById(e.id),this.allocDialogVisible=!0},getRoleList:function(e){var t=this;return s["b"].post("/role/findAllRole",this.listQuery).then((function(e){console.log(e.data.content),t.allRoleList=e.data.content.map((function(e){return{id:e.id,name:e.name}}))})).catch((function(e){}))},getUserRoleById:function(e){var t=this;return s["b"].get("/user/findUserRoleById?id="+e).then((function(e){var a=e.data.content;if(t.allocRoleIds=[],null!=a&&a.length>0)for(var l=0;l<a.length;l++)t.allocRoleIds.push(a[l].id)})).catch((function(e){}))},handleAllocRole:function(){var e=this,t={userId:this.allocAdminId,roleIdList:this.allocRoleIds};return s["b"].post("/user/userContextRole",t).then((function(t){e.allocDialogVisible=!1})).catch((function(e){}))}}},c=r,u=(a("4cab"),a("2877")),d=Object(u["a"])(c,l,i,!1,null,null,null);t["default"]=d.exports}}]);