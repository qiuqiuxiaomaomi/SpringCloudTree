### Zipkin

链路追踪


![](https://i.imgur.com/f3Hg8MT.png)

<pre>
分布式链路追踪系统

      分布式链路追踪系统起源于Google的论文Dapper，Twitter的Zipkin是基于此论文上线较早的分布式链路追踪系统。

      结构比较简单，大概流程为：

          1：Trace数据的收集至Scribe(Facebook开源的日志传输通路)或Kafka(Apache分布式消息系统)。
          2：Scribe/Kafaka中的数据由控制器存入数据库中。
          3：最后由UI和Query查询展示。

      Zipkin 提供了自己的UI，应用将自己的监控数据报告给zipkin，由Zipkin 汇集并提供关联图展示，Zipkin可以追踪请求调用链路。
      Zipkin 以 Trace 的结构表示一次请求的追踪，又把每个Trace拆分为若干个有依赖关系的 Span，在微服务架构中，一次用户的请求可能
      会被后台的若干个服务处理，这完整的一次用户请求可以一条调用链路Trace，每个调用处理请求的服务可以理解为一个Span(如API服务)，
      这个服务也可能继续调用其他的服务，因此形成一个Span的树形结构，以体现服务间的调用关系。

      Spring Cloud Sleuth是对Zipkin的一个封装，对于Span、Trace等信息的生成、接入HTTP Request，以及向Zipkin Server发送采集信
      息等全部自动完成。
</pre>