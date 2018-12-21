### zuul

网关

![](https://i.imgur.com/AsSptrH.png)

流程图

![](https://i.imgur.com/b8Rgbx3.png)

<pre>
       zuul的核心是一系列的filters, 其作用可以类比Servlet框架的Filter，或者AOP.

       Zuul提供了一个框架，可以对过滤器进行动态的加载，编译，运行。

       Zuul的过滤器之间没有直接的相互通信，他们之间通过一个RequestContext的静态类来进行数据传递的。RequestContext类中
       有ThreadLocal变量来记录每个Request所需要传递的数据。

       Zuul的过滤器是由Groovy写成，这些过滤器文件被放在Zuul Server上的特定目录下面，Zuul会定期轮询这些目录，修改过的过滤器
       会动态的加载到Zuul Server中以便过滤请求使用
</pre>

<pre>
zuul功能：
      1）验证与安全保障。
      2）动态路由
</pre>