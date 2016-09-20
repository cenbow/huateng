### jenkins安装
* jenkins版本: 1.528-1.1
* 下载jenkins: http://pkg.jenkins-ci.org/redhat/
* 上传jenkins至服务器
* sudo wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins.repo 获取资源文件
* sudo rpm --import http://pkg.jenkins-ci.org/redhat/jenkins-ci.org.key  导入jenkins库的 key
* sudo yum install jenkins-1.528-1.1.noarch.rpm  安装
* sudo vi /etc/sysconfig/jenkins 修改JENKINS_PORT="8080"端口  
* sudo service jenkins start/stop/restart  启动/关闭/重新启动
* 安装jenkins git插件
* sudo -u jenkins -H ssh-keygen -t rsa 生成git密钥
* 复制密钥至 /var/lib/jenkins/.ssh下
* 将这个密钥赋予git下载代码的权限
* sudo -u jenkins -H git clone git@bestpay.com:bestpay-portal.git  尝试是否可以成功拉取代码

### 外部资源
* jenkins官网: [http://jenkins-ci.org/](http://jenkins-ci.org/)