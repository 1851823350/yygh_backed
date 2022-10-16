# 预约挂号平台
## 项目介绍
    网上预约挂号是近年来开展的一项便民就医服务，旨在缓解看病难、挂号难的就医难题，许多患者为看一次病要跑很多次医院，最终还不一定能保证看得上医生。网上预约挂号全面提供的预约挂号业务从根本上解决了这一就医难题。随时随地轻松挂号！不用排长队！
此项目包含三个系统：
1. 预约挂号前台
2. 预约挂号后台
3. 外部医院管理后台
如图：
![输入图片说明](https://foruda.gitee.com/images/1665918156924728162/19c0ae38_10883935.png "屏幕截图")
## 项目功能
### 预约挂号用户前台
1. 登陆
2. 首页信息展示
3. 预约挂号
4. 支付订单
### 预约挂号管理员后台
1. 数据字典
- easy excel导入导出
2. 医院管理
3. 会员管理
4. 订单（预约管理）管理
5. 统计订单
### 模拟医院平台
相当于医院自己维护的系统，通过签名校验的方式调用预约平台系统的接口
## 技术栈
### 后端
- SpringBoot：简化新Spring应用的初始搭建以及开发过程
- SpringCloud：基于Spring Boot实现的云原生应用开发工具，SpringCloud使用的技术：（SpringCloudGateway、Spring Cloud Alibaba Nacos、Spring Cloud Alibaba                 
  Sentinel、SpringCloud Task和SpringCloudFeign等）
- MyBatis-Plus：持久层框架
- Redis：内存缓存，存储验证码
- RabbitMQ：消息中间件，应用解耦，消息通知
- HTTPClient: Http协议客户端
- Swagger2：Api接口文档工具（测试各个Api接口）
- Nginx：负载均衡
- Lombok
- Mysql：关系型数据库
- MongoDB：面向文档的NoSQL数据库，存储医院信息（性能更高）
- Maven父子多模块
### 前端
- Vue.js：web 界面的渐进式框架
- Node.js： JavaScript 运行环境
- Axios：Axios 是一个基于 promise 的 HTTP 库
- NPM：包管理器
- Babel：转码器
- Webpack：打包工具
- Nuxt：前端服务器渲染技术
- vue-admin-template管理员模板
- element ui饿了么组件库
- VueQriously二维码生成库
### 其他
docker：容器技术
git：代码管理工具
easy excel：读写Excel文件
Joda time：日期时间操作工具    
## 业务流程
![输入图片说明](https://foruda.gitee.com/images/1665917705715109206/426c53ed_10883935.png "屏幕截图")
## 服务架构
![输入图片说明](https://foruda.gitee.com/images/1665917762364836838/2317d6ac_10883935.png "屏幕截图")



