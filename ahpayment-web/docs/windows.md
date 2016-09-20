# Windows 环境搭建文档:

## Ruby:

- 下载 [Ruby Installer](http://dl.bintray.com/oneclick/rubyinstaller/rubyinstaller-2.0.0-p247.exe), 并安装.
- 下载 [DevKit](https://github.com/downloads/oneclick/rubyinstaller/DevKit-tdm-32-4.5.2-20111229-1559-sfx.exe), 解压后遵循以下步骤安装:
    1. `cd <DEVKIT_INSTALL_DIR>`
    2. `ruby dk.rb init`
    3. `ruby dk.rb install`
- 执行 `gem install wdm` 来安装 linner 的一个依赖
- 执行 `gem install linner -v0.3.0` 来安装开发工具.
- 在 `nuwa` 目录下执行 `linner watch` 来实时编译.
- 执行 `linner help` 可以查看帮助
