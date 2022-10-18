(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["menuAdd"],{"0ee3":function(e,n,t){"use strict";t.r(n);var r=function(){var e=this,n=e.$createElement,t=e._self._c||n;return t("menu-detail",{attrs:{"is-edit":!1}})},a=[],i=t("e6e7"),l={name:"addMenu",title:"添加菜单",components:{MenuDetail:i["a"]}},u=l,s=t("2877"),o=Object(s["a"])(u,r,a,!1,null,null,null);n["default"]=o.exports},e6e7:function(e,n,t){"use strict";var r=function(){var e=this,n=e.$createElement,t=e._self._c||n;return t("el-card",{staticClass:"form-container",attrs:{shadow:"never"}},[t("el-form",{ref:"form",attrs:{model:e.menu,rules:e.rules,"label-width":"150px"}},[t("el-form-item",{attrs:{label:"菜单名称：",prop:"name"}},[t("el-input",{model:{value:e.menu.name,callback:function(n){e.$set(e.menu,"name",n)},expression:"menu.name"}})],1),t("el-form-item",{attrs:{label:"菜单路径：",prop:"href"}},[t("el-input",{model:{value:e.menu.href,callback:function(n){e.$set(e.menu,"href",n)},expression:"menu.href"}})],1),t("el-form-item",{attrs:{label:"上级菜单："}},[t("el-select",{attrs:{placeholder:"请选择菜单"},model:{value:e.menu.parentId,callback:function(n){e.$set(e.menu,"parentId",n)},expression:"menu.parentId"}},e._l(e.selectMenuList,(function(e){return t("el-option",{key:e.id,attrs:{label:e.title,value:e.id}})})),1)],1),t("el-form-item",{attrs:{label:"描述：",prop:"description"}},[t("el-input",{model:{value:e.menu.description,callback:function(n){e.$set(e.menu,"description",n)},expression:"menu.description"}})],1),t("el-form-item",{attrs:{label:"前端图标：",prop:"icon"}},[t("el-input",{staticStyle:{width:"80%"},model:{value:e.menu.icon,callback:function(n){e.$set(e.menu,"icon",n)},expression:"menu.icon"}})],1),t("el-form-item",{attrs:{label:"是否显示："}},[t("el-radio-group",{model:{value:e.menu.shown,callback:function(n){e.$set(e.menu,"shown",n)},expression:"menu.shown"}},[t("el-radio",{attrs:{label:0}},[e._v("是")]),t("el-radio",{attrs:{label:1}},[e._v("否")])],1)],1),t("el-form-item",{attrs:{label:"排序："}},[t("el-input",{model:{value:e.menu.orderNum,callback:function(n){e.$set(e.menu,"orderNum",n)},expression:"menu.orderNum"}})],1),t("el-form-item",[t("el-button",{attrs:{type:"primary"},on:{click:function(n){return e.handleSave()}}},[e._v("提交")])],1)],1)],1)},a=[],i=(t("d81d"),t("b0c0"),t("ed08")),l={description:"",parentId:-1,name:"",icon:"",shown:0,orderNum:0},u={description:[{required:!0,message:"请输入菜单描述",trigger:"blur"},{min:2,max:140,message:"长度在 2 到 140 个字符",trigger:"blur"}],name:[{required:!0,message:"请输入菜单名称",trigger:"blur"},{min:2,max:140,message:"长度在 2 到 140 个字符",trigger:"blur"}],icon:[{required:!0,message:"请输入前端图标",trigger:"blur"},{min:2,max:140,message:"长度在 2 到 140 个字符",trigger:"blur"}]},s={name:"MenuDetail",props:{isEdit:{type:Boolean,default:!1}},data:function(){return{menu:l,selectMenuList:[],rules:u}},created:function(){if(this.isEdit){var e=this.$route.query.id;this.findMenuInfoById(e)}else this.menu=Object.assign({},l),this.findMenuInfoById(-1)},methods:{findMenuInfoById:function(e){var n=this;return i["b"].get("/menu/findMenuInfoById?id="+e).then((function(e){console.log(e.data),null!=e.data.content.menuInfo&&(n.menu=e.data.content.menuInfo),n.selectMenuList=e.data.content.parentMenuList.map((function(e){return{id:e.id,title:e.name}})),n.selectMenuList.unshift({id:-1,title:"无上级菜单"})})).catch()},handleSave:function(){var e=this;this.$refs.form.validate((function(n){return!!n&&i["b"].post("/menu/saveOrUpdateMenu",e.menu).then((function(n){e.$router.back()})).catch()}))}}},o=s,m=t("2877"),c=Object(m["a"])(o,r,a,!1,null,"d4a0f028",null);n["a"]=c.exports}}]);