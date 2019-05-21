## 备注
>这是学习一些简单的，没用过的东西
###1 redis
install redis:
> https://github.com/microsoftarchive/redis/releases
<br/>pom.xml里面添加：
>
	<dependency>
		<groupId>redis.clients</groupId>
		<artifactId>jedis</artifactId>
		<version>版本</version>
	</dependency>
> >start redis server: redis-server.exe redis.windows.conf  
> >redis服务指定端口启动 redis-server.exe --port 8880&  
> >start redis client: redis-cli.exe -h 127.0.0.1 -p 6379  
> >启动服务：redis-server --service-start  
> >停止服务：redis-server --service-stop  
> >连接redis时需要做到两点 1： redis服务启动 2：redis连接的端口未被防火墙屏蔽  

###2 



