# RabbitMQ

### 
#### 
rabbitmqctl.bat list_queues
rabbitmqctl.bat list_queues name messages_ready messages_unacknowledged
rabbitmqctl stop
rabbitmqctl start
rabbitmqctl list_bindings

####



rabbitmq启动方式有2种

1、以应用方式启动

rabbitmq-server -detached 后台启动

Rabbitmq-server 直接启动，如果你关闭窗口或者需要在改窗口使用其他命令时应用就会停止

 关闭:rabbitmqctl stop

2、以服务方式启动（安装完之后在任务管理器中服务一栏能看到RabbtiMq）

rabbitmq-service install 安装服务

rabbitmq-service start 开始服务

Rabbitmq-service stop  停止服务

Rabbitmq-service enable 使服务有效

Rabbitmq-service disable 使服务无效

rabbitmq-service help 帮助

当rabbitmq-service install之后默认服务是enable的，如果这时设置服务为disable的话，rabbitmq-service start就会报错。

当rabbitmq-service start正常启动服务之后，使用disable是没有效果的

  关闭:rabbitmqctl stop

3、Rabbitmq 管理插件启动，可视化界面

rabbitmq-plugins enable rabbitmq_management 启动

rabbitmq-plugins disable rabbitmq_management 关闭

 

4、Rabbitmq节点管理方式

Rabbitmqctl
