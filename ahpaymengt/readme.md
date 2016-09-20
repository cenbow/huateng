bestpay-portal
====
### 环境搭建
* zookeeper: 3.3.5
* dubbo Monitor: 2.4.10
* 绑定hosts: 192.168.227.99 repo.bestpay.com.cn

### 安装zookeeper
#### windows:
* 解压zookeeper-3.3.5.tar.gz
* zookeeper-3.3.5/conf目录下的zoo_sample.cfg文件拷贝一份，命名为为“zoo.cfg”
* 修改zoo.cfg配置文件  dataDir=E:/tmp/zookeeper 临时文件目录地址   clientPort=2181端口号
* 启动双击bin下的zkServer.cmd

#### linux:
* 解压tar -xf zookeeper-3.3.5.tar.gz
* 打开/etc/zookeeper/zoo.cfg
* 修改zoo.cfg配置文件  dataDir=/usr/local/zookeeper/data 临时文件目录地址   clientPort=2181端口号
* 启动./bin/zkServer.sh start

### 安装dubbo Monitor
* tar zxvf dubbo-monitor-simple-2.4.10-assembly.tar.gz  解压
* cd dubbo-monitor-simple-2.4.10  进入目录
* vi conf/dubbo.properties  配置端口 以及zookeeper地址
* ./bin/start.sh  启动

### 开发流程
* api中编写model模型，service接口
* 编写service实现，dao，xml
* resources/spring/mybatis.xml 注册新的mybatis映射
* resources/bestpay-service-provider.xml 注册新的service
* 编写单元测试类测试service接口
* 运行Bootstrap 查看是否报错，服务是否发布
* 提交版本

### 将后台应用打成jar包发布
* mvn clean package -Dmaven.test.skip  -U
* -Pproduction生产环境打包加入此参数
* 将本地打出来的jar包上传至服务器
* ps -ef|grep xxxxx.jar 查找原来启动的jar包进程
* kill -9  pid 杀掉原来的jar包进程
* ./xxxxxx.sh 启动jar包（第一次需建这个sh文件 内容为:nohup java -jar -Ddubbo.protocol.host=192.168.232.209 bestpay-payment.jar &）

### 编码注意事项
* 方法体尽量保持简洁
* 初始化List,Map 等数据结构时请指定初始大小
* 不要使用 StringBuffer 来记录日志
* Service方法使用try包围具体的业务逻辑，捕获到异常需要丢到response一起返回，并设定错误代码
* 区分Post与其他类型（如GET) 请求的差异，一般GET请求直接调用Service,POST
* 在需要的地方使用new构造对象,非必要时不要提前构造对象
* 防御式编程思想，尽早发现问题并返回错误编码

### 消除IDE的警告
* 没有用到的变量、方法去掉
* 没有用到注释请直接去掉
* List,Map 请指定具体的类型,如List<String>,Map<String,Integer>

### 完整的注释应该包含以下信息
* 方法说明
* 方法作者
* 参数说明，可选 or 必选，可选参数需要注明默认值
* 返回值类型

### 详尽文档
* [maven私有库使用图文文档](/bestpay-portal/blob/master/docs/mavensf.md)

### 外部资源
* maven私有库配置: [http://wenku.baidu.com/view/73f58535eefdc8d376ee32d4.html](http://wenku.baidu.com/view/73f58535eefdc8d376ee32d4.html)