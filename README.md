# Kafka-ACLs发送工具

## 概要信息

* 加载本地/static/kafka/目录下*.properties文件

## 指南

### 目录

* config 配置加载
* controller 控制器
* service kafka发送

# navigator 快速入门

## 本地开发流程

### 配置说明

* 添加配置
  * 手动添加
    * 复制本地/static/kafka/template.properties文件
    * 修改bootstrap.servers
    * 修改sasl.jaas.config中用户名和密码
  * 页面配置
    * 访问[localhost:8081/create(http://localhost:8081/create)
* 启动服务，访问[localhost:8081](http://localhost:8081)
* 如需要其他功能请自行添加