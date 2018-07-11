# redis分布式锁
## 1. 使用配置

>配置文件为redis.properties，需要放在类路径下，编码格式UTF-8

### 1.2 配置项目
<table>
    <tr>
        <td>参数名称</td>
        <td>参数解释</td>
    </tr>
    <tr>
        <td>redis.max.total</td>
        <td>最大连接数</td>
    </tr>
    <tr>
        <td>redis.max.idle</td>
        <td>最大空闲连接数，在jedispool中最大的idle状态(空闲的)的jedis实例的个数</td>
    </tr>
    <tr>
        <td>redis.min.idle</td>
        <td>最小空闲连接数，在jedispool中最小的idle状态(空闲的)的jedis实例的个数</td>
    </tr>
    <tr>
        <td>redis.test.borrow</td>
        <td>在取连接时测试连接的可用性，在borrow一个jedis实例的时候，<br/>是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的</td>
    </tr>
    <tr>
        <td>redis.test.return</td>
        <td>再还连接时不测试连接的可用性，<br/>在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。</td>
    </tr>
    <tr>
        <td>redis.ip</td>
        <td>redis服务端ip</td>
    </tr>
    <tr>
        <td>redis.port</td>
        <td>redis服务端port</td>
    </tr>
    <tr>
        <td>redis.server.timeout</td>
        <td>redis连接超时时间</td>
    </tr>
    <tr>
        <td>redis.lock.timeout</td>
        <td>锁超时时间</td>
    </tr>
</table>