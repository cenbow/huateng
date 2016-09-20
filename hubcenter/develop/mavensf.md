maven私有库使用图文文档
====
### 准备工作
##### c:/windows/system32/drivers/etc/hosts 加上116.228.55.189 repo.bestpay.com.cn
##### 复制\\192.168.106.99\学习文档\maven\settings.xml到我的文档\.m2\settings.xml

### 配置公网maven库JAR包
##### 查询网址http://mvnrepository.com/
![](/core/accountcenter/raw/master/develop/screenshots/1.jpg)
##### 在搜索框查询你需要的jar包名字如junit,然后点击search
![](/core/accountcenter/raw/master/develop/screenshots/2.jpg)
##### 选择搜索相关项，然后选择jar包的版本，如4.8.2，尽量选择release版本的稳定jar包
##### 复制下图红框内容进自己的maven项目 ，pom.xml文件中
![](/core/accountcenter/raw/master/develop/screenshots/3.jpg)
<scope>test</scope>表示这个jar包在最后打成war包时不会载入，因为这个是单元测试用的所以这么配置，普通jar不写就行。
![](/core/accountcenter/raw/master/develop/screenshots/4.jpg)

### 配置私有JAR包，如bestpay.jar
##### 进入网址http://116.228.55.189:8085/nexus/ 登录用户
##### 选择3rd party ，然后选择artifact upload
![](/core/accountcenter/raw/master/develop/screenshots/5.jpg)
##### 如图填写
![](/core/accountcenter/raw/master/develop/screenshots/6.jpg)
##### 上传jar包
![](/core/accountcenter/raw/master/develop/screenshots/7.jpg)
##### 根据上传时填入的内容编写pom文件导入jar包，也可以查询
![](/core/accountcenter/raw/master/develop/screenshots/8.jpg)
![](/core/accountcenter/raw/master/develop/screenshots/9.jpg)
##### 查询一下jar包是否导入了
![](/core/accountcenter/raw/master/develop/screenshots/10.jpg)
