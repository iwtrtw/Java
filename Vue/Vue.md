## Vue

#### 概念

+ 构建用户界面的渐进式框架
+ 只关注视图层，采用自底向上增量开发的设计
+ 通过尽可能简单的API实现响应的数据绑定和组合

#### 指令

+ v-bind(**:**)：属性数据的双向绑定
+ v-on(**@**)：监听事件
+ v-show：不管初始条件是什么，元素总会被渲染（基于CSS切换），初始渲染开销高，适合频繁地切换
+ v-if：条件渲染，对组件适当进行销毁和重建，切换开销高，适合运行时条件很少改变
+ v-model：表单数据双向绑定

#### Class与Style

+ v-bind:style的值时JS对象
+ 值需要使用大括号{}，而CSS不用
+ 各值以逗号**，**分割，CSS以分号**;**分割
+ 对应CSS的属性采用驼峰式

#### 自定义组件

+ 自定义组件data应该返回一个函数，data函数内部的变量是局部变量（组件之间不会影响）

+ template：替换body自定义组件所在的位置

+ 自定义组件需先定义后使用

+ 可以在vue对象中使用自定义组件

+ 全局组件可以在任何Vue对象中使用多次

  + 全局组件与局部组件
  
  ```
  <div id ="app">
  	<button-conter></button-conter>	//<bttuon v-on:click="count++">{{count}}</button>
  </div>
  
  
  <div id="app2">
  	<myheader></myheader>	//<h4>局部定义组件</h4>
  </div>
  
  
  <script type="text/javascript">
   	// 自定义全局组件（先定义）
      Vue.component('button-conter',{
          data:function(){
              return{
                  count:'Hello world'
              }
          },
          //template自定义组件最终返回的结果
          template:'<bttuon v-on:click="count++">{{count}}</button>'
      })
  	//后使用
      new Vue({
          el:"#app"
      })
      
      
       //自定义内部组件
      var myvue = new Vue({
          el:"#app2",
          components:{
              'myheader':{
                  template:'<h4>局部定义组件</h4>'
              }
          }
      })
</script>
  ```
  
  + 组件嵌套<slot/>
  
    ```
    <div id ="app1">
    	<myheader>
    		<mytitle></mytitle>
    	</myheader>
    </div>
    
    <script type="text/javascript">
    	//组件嵌套
        var myvue = new Vue({
            el:"#app1",
            components:{
                'myheader':{
                    template:'<h4>This is a header<slot/></h4>'
                },
                'mytitle':{
                    template:'<h5>This is a title</h5>'
                }
            }
        })
    </script>
    ```
  
    