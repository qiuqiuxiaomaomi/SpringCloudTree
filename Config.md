###配置中心

分布式配置中心，支持GIT，SVN等模式

<pre>
     在分布式系统中，每一个功能模块都能拆分成一个独立的服务，一次请求的完成，可能会调用很
     多个服务协调来完成，为了方便服务配置文件统一管理，更易于部署、维护，所以就需要分布式
     配置中心组件了，在spring cloud中，有分布式配置中心组件spring cloud config，它支
     持配置文件放在在配置服务的内存中，也支持放在远程Git仓库里。引入spring cloud 
     config后，我们的外部配置文件就可以集中放置在一个git仓库里，再新建一个
     config server，用来管理所有的配置文件，维护的时候需要更改配置时，只需要在本地更改
     后，推送到远程仓库，所有的服务实例都可以通过config server来获取配置文件，这时每个服
     务实例就相当于配置服务的客户端config client,为了保证系统的稳定，配置服务端
     config server可以进行集群部署，即使某一个实例，因为某种原因不能提供服务，也还有其他
     的实例保证服务的继续进行
</pre>

![](https://i.imgur.com/0GaRVjF.png)

![](https://i.imgur.com/v6pHeD4.png)

<pre>
配置刷新流程

      1、提交配置触发post请求给server端的bus/refresh接口

      2、server端接收到请求并发送给Spring Cloud Bus总线

      3、Spring Cloud bus接到消息并通知给其它连接到总线的客户端

      4、其它客户端接收到通知，请求Server端获取最新配置

      5、全部客户端均获取到最新的配置
</pre>