Nuwa
====

### 环境搭建:
    Ruby: ~2.0.0-p247
    Node.js: ~0.10.12

### 安装 Gem 依赖
    gem install linner

如果是 windows 环境，还需要：

    gem install wdm

### 开发流程:

* 在 `nuwa` 目录下执行 `linner watch`
* 在 `app/widgets` 目录添加组件, 组件遵循 `view.hbs`, `view.js`, `view.css` 的约定. 当组件有配置需求时, 添加 `config.hbs`, `config.js`, `config.css` 来编写组件的配置编写.
* 在 `admin.sutongpay.cn` 里面添加 components, 遵循已有的 components 路径规范.

### 开发类库依赖

* jquery
* jquery-ui
* underscore
* handlebars

### 外部资源

语言:

* sass: [http://sass-lang.com](http://sass-lang.com)
* coffee-script: [http://coffeescript.org](http://coffeescript.org)
* handlebars: [http://handlebarsjs.com](http://handlebarsjs.com)
* handlebars-java [http://github.com/jknack/handlebars.java]([http://github.com/jknack/handlebars.java])

环境:

* ruby [http://ruby-lang.org]([http://ruby-lang.org])
* ruby-installer [http://rubyinstaller.org](http://rubyinstaller.org)
* nodejs [http://nodejs.org]([http://nodejs.org])

### 详尽文档

* [图文文档](/nuwa/blob/master/docs/guide.md)
* [Windows 安装文档](/nuwa/blob/master/docs/windows.md)
