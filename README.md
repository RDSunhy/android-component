# talking-android

#### 介绍

talking-android
Kotlin 组件化基础学习实践项目

#### 说明

目录说明：
/app 壳工程
/basics 目录下都是一些基础组件 和 公共组件 （基类，包括一些databinding、viewmodel、popup、MultiType等等封装）
/basics/base 项目基类、基本依赖，集成了一些各个组件需要的第三方库等
/basics/ktx Kotlin扩展函数
/basics/database room本地数据库模块 为了方便 api请求结果实体类 LiveEventbus实体类也放在这里了
/basics/routers ARouter各个模块路由配置 抽出来方便各个组件互相跳转
/basics/common 公共组件，集成了所有的基础组件
/libs 目录下都是独立能力组件 （图片加载、网络请求等）
/libs/imageLoader 图片加载模块 参阅大佬博客封装的glide 可以像Coil一样方便的加载图片
/libs/network 网络请求模块 参阅google官方github浏览器项目实现
/widgets 目录下都是 View、资源、recyclerview items
/widgets/item_delegates 存放项目用到的全部的recyclerview item （本项目的recyclerview多类型采用MultiType实现 不用写大量的adapter 集中关注item的逻辑即可）
/widgets/resource 全局共用的图标、颜色、字符串、drawable等等资源文件
/widgets/views 自定义view
/components 目录下都是具有可独立运行能力的组件 （修改根目录下gradle.properties中的 debugXXX=true 即可独立运行）

#### Demo示例


