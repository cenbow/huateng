### maven
* maven: 3.1
* 下载maven: http://maven.apache.org/download.cgi
* 上传maven至服务器
* tar -xvzf apache-maven-3.0-bin.tar.gz 解压
* 配置环境变量，设置M2_HOME 环境变量指向符号链接apache-maven-3.1.0即可；    export M2_HOME=/usr/local/maven/apache-maven-3.1.0    export PATH=$PATH:$M2_HOME/bin 
* mvn –version 检测有没有安装好。


### 详尽文档
* [maven私有库使用图文文档](/core/accountcenter/blob/master/develop/mavensf.md)

### 外部资源
* maven官网: [http://maven.apache.org/](http://maven.apache.org/)
* maven私有库配置: [http://wenku.baidu.com/view/73f58535eefdc8d376ee32d4.html](http://wenku.baidu.com/view/73f58535eefdc8d376ee32d4.html)