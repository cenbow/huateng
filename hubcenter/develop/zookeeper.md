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
