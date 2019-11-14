E:\tools\mq\rabbitmq_server-3.7.18\sbin

# RabbitMQ
### 参数说明文档
#### [1] 声明队列 持久化
boolean durable = true;
channel.queueDeclare("hello", durable, false, false, null);
###参数
channel.QueueDeclare(name, durable, autoDelete, exclusive, noWait, args):
* name:队列名字
* durable：是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
* autoDelete：是否自动删除队列，当最后一个消费者断开连接之后队列是否自动被删除，可以通过RabbitMQ Management，查看某个队列的消费者数量，当consumers = 0时队列就会自动删除
* exclusive：是否排外的，有两个作用，一：当连接关闭时connection.close()该队列是否会自动删除；二：该队列是否是私有的private，如果不是排外的，可以使用两个消费者都访问同一个队列，没有任何问题，如果是排外的，会对当前队列加锁，其他通道channel是不能访问的，如果强制访问会报异常：com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=405, reply-text=RESOURCE_LOCKED - cannot obtain exclusive access to locked queue 'queue_name' in vhost '/', class-id=50, method-id=20)一般等于true的话用于一个队列只能有一个消费者来消费的场景
* noWait:是否等待服务器返回
* args：相关参数，目前一般为nil
#### [2]发送消息 设置持久化
channel.basicPublish（“”，“ task_queue”，
MessageProperties.PERSISTENT_TEXT_PLAIN，
message.getBytes（））;
参数：
void basicPublish(String exchange, String routingKey, boolean mandatory, 
boolean immediate, BasicProperties props, byte[] body) throws IOException;
exchange：名称
routingKey：路由键，#匹配0个或多个单词，*匹配一个单词，在topic exchange做消息转发用

mandatory：为true时如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用
basic.return方法将消息返还给生产者。为false时出现上述情形broker会直接将消息扔掉
　
immediate：为true时如果exchange在将消息route到queue(s)时发现对应的queue上没有消费者，那么这条消息不会放入队列中。当与消息routeKey关联的所有queue(一个或多个)都没有消费者时，该消息会通过basic.return方法返还给生产者。

props：需要注意的是BasicProperties.deliveryMode，1:不持久化 2：持久化 这里指的是消息的持久化，配合channel(durable=true),queue(durable)可以实现，即使服务器宕机，消息仍然保留

body：要发送的信息

#### [3]消费者
channel.basicConsume(TASK_QUEUE_NAME, true, deliverCallback, consumerTag -> { });
* 第二个参数就是自定确认：设置为true就表示自动确认
#### [4]公平派遣 不要给一个工人多个消息，给不忙的工人
int prefetchCount=1;
channel.basicQos(prefetchCount)
#### [5]关于队列大小的注意事项
如果所有工作人员都忙，您的队列就满了。您将需要留意这一点

#### 参数说明
##BasicProperties
private String contentType;  
private String contentEncoding;  
private Map<String,Object> headers;   header类型的交换机可以用到
private Integer deliveryMode;  消息持久化 1 不持久化 2 持久化
private Integer priority;   优先级
private String correlationId;   用于将RPC响应与请求相关联
private String replyTo;  通常用于命名回调队列
private String expiration; 设置过期消息过期时间
private String messageId;
private Date timestamp;
private String type;
private String userId;
private String appId;
private String clusterId;



* [原文链接](https://blog.csdn.net/jj546630576/article/details/102498032)

* [参数](https://www.jianshu.com/p/537cb84ba72f)


