## 如何开发一个组件 ##

### 文件结构 ###

典型的组件由一个文件夹下的三种文件组成，包括`*.hbs`的模板文件、`*.js/*.coffee`的JS文件以及`*.css/*.sass/*.scss`的CSS文件。

下文提到`*.js`文件时指的是`*.js/*.coffee`文件的任意一种，因为他们最终产出的都是`*.js`文件。例如当我提到`view.js`可对应路径下只有`view.coffee`时，那么我指的就是`view.coffee`。
同样提到`*.css`文件时指的是`*.css/*.sass/*.scss`文件的任意一种，理由同上。

下图是一个 tab_switcher 组件的文件结构：

![](/pikachu/raw/master/docs/screenshots/widget_file_tree.png)

- `widgets`是所有组件的根目录
- `widgets/common`放置了图片、文本等一些通用组件
- `widgets/official`中放置一些业务相关的独立组件，我在其下建立了`widgets/official/sutongpay`子目录来放置翼支付相关的组件
- `widgets/official/sutongpay/tab_switcher`即 tab_switcher 组件的目录
    - `tab_switcher/view.hbs`是组件的主模板文件，`view.hbs`这个名字是约定的，引擎会寻找每一个组件根目录下的`view.hbs`进行渲染
    - 除`view.hbs`外还有一些文件。其中`view.css`是组件在展示时的 css ；`config.css`(可选)是在 design 模式下组件所需的 css ；`view.js`是组件在展示时的 js ；`config.js`(如果使用layout方式开发，则此文件也是可选的)是 design 模式下组件配置所需 JS 。
    - 组件的其余文件并无约束，都可以通过代理服务器访问

### 开发流程 ###

1. 首先在`widgets/official/sutongpay`目录下新建好组件的目录结构
2. 新建`view.hbs`文件，在 tab_switcher 中此文件如下：

    ```html
    {{#component "sutongpay-tab-switcher"}}
      <ul class="tab-list">
        <li class="active"><span class="tab-list-item-title">tab-title-1</span></li>
        <li><span class="tab-list-item-title">tab-title-2</span></li>
        <li><span class="tab-list-item-title">tab-title-3</span></li>
        <li><span class="tab-list-item-title">tab-title-4</span></li>
        <li><span class="tab-list-item-title">tab-title-5</span></li>
        <li><span class="tab-list-item-title">tab-title-6</span></li>
        <li><span class="tab-list-item-title">tab-title-7</span></li>
        <li><span class="tab-list-item-title">tab-title-8</span></li>
      </ul>
      <div class="tab-desc">
        <p class="active">some-tab-desc-1</p>
        <p>some-tab-desc-2</p>
        <p>some-tab-desc-3</p>
        <p>some-tab-desc-4</p>
        <p>some-tab-desc-5</p>
        <p>some-tab-desc-6</p>
        <p>some-tab-desc-7</p>
        <p>some-tab-desc-8</p>
      </div>
    {{/component}}
    ```

    `{{#component}}`这个 handlebars helper 实际上生成了一个`<div class="sutongpay-tab-switcher js-comp" id="{{_ID_}}" style="{{_STYLE_}}" data-comp-id="{{_COMP_ID_}}" data-comp-path="{{_COMP_PATH_}}"></div>`的包装结构，也就是说上面的内容等价于下面的内容：

    ```html
    <div class="sutongpay-tab-switcher js-comp" id="{{_ID_}}" style="{{_STYLE_}}" data-comp-id="{{_COMP_ID_}}" data-comp-path="{{_COMP_PATH_}}">
      <ul class="tab-list">
        <li class="active"><span class="tab-list-item-title">tab-title-1</span></li>
        <li><span class="tab-list-item-title">tab-title-2</span></li>
        <li><span class="tab-list-item-title">tab-title-3</span></li>
        <li><span class="tab-list-item-title">tab-title-4</span></li>
        <li><span class="tab-list-item-title">tab-title-5</span></li>
        <li><span class="tab-list-item-title">tab-title-6</span></li>
        <li><span class="tab-list-item-title">tab-title-7</span></li>
        <li><span class="tab-list-item-title">tab-title-8</span></li>
      </ul>
      <div class="tab-desc">
        <p class="active">some-tab-desc-1</p>
        <p>some-tab-desc-2</p>
        <p>some-tab-desc-3</p>
        <p>some-tab-desc-4</p>
        <p>some-tab-desc-5</p>
        <p>some-tab-desc-6</p>
        <p>some-tab-desc-7</p>
        <p>some-tab-desc-8</p>
      </div>
    </div>
    ```

    `{{#component}}`生成的包装内容对框架来说是必须的，为了方便记忆所以使用一个 helper 来简化，当然如果你想要添加更多的属性到组件的 html 上，你也可以手写整个结构并加上你自己的内容

3. 新建`base/view.css`文件，在 tab_switcher 中我选择了`scss`格式，此文件如下：

    ```scss
    @mixin clearfix {
      *zoom: 1;

      &:before, &:after {
        display: table;
        line-height: 0;
        content: "";
      }

      &:after {
        clear: both;
      }
    }

    .sutongpay-tab-switcher {
      width: 1000px;

      .tab-list {
        list-style: none;
        padding: 0;
        margin: 0 0 0 3px;
        @include clearfix;

        > li {
          position: relative;
          display: block;
          float: left;
          width: 118px;
          height: 98px;
          margin: 20px 2px 0;
          background-color: #eee;
          border-radius: 5px 5px 0 0;
          border-top: 1px solid #ccc;
          border-left: 1px solid #ccc;
          border-right: 1px solid #ccc;
          box-shadow: inset 0 1px rgba(white, 0.8), inset 0 -10px 10px -5px rgba(black, 0.1);

          .tab-list-item-title {
            position: absolute;
            bottom: 0;
            display: none;
            text-align: center;
            color: red;
            width: 100%;
          }

          &.active {
            margin-top: 0;
            height: 118px;
            background-color: white;
            box-shadow: none;
            top: 1px;

            .tab-list-item-title {
              display: block;
            }
          }
        }
      }
      .tab-desc {
        border-radius: 5px;
        height: 90px;
        padding: 30px;
        border: 1px solid #ccc;
        box-shadow: 5px 5px 5px rgba(black, 0.1);

        > p {
          display: none;

          &.active {
            display: block;
          }
        }
      }
    }
    ```

    此文件没什么特殊的，需要注意的是在这个例子里我并没有为 IE 做兼容。

4. 新建`base/view.js`文件，在 tab_switcher 中我选择了`coffee`格式，此文件如下：

    ```coffee
    module.exports =
      init: ->
        $theComponent = $(@)
        $(".tab-list > li", $theComponent).mouseenter ->
          $(@).addClass("active").siblings().removeClass("active")
          index = $(".sutongpay-tab-switcher .tab-list > li").index($(@))
          $(".tab-desc > p:nth-child(#{index})", $theComponent).addClass("active").siblings().removeClass("active")
    ```

    此`coffee`文件对应的`js`代码为：

    ```js
    module.exports = {
      init: function() {
        var $theComponent;
        $theComponent = $(this);
        return $(".tab-list > li", $theComponent).mouseenter(function() {
          var index;
          $(this).addClass("active").siblings().removeClass("active");
          index = $(".sutongpay-tab-switcher .tab-list > li").index($(this));
          return $(".tab-desc > p:nth-child(" + index + ")", $theComponent).addClass("active").siblings().removeClass("active");
        });
      }
    };
    ```

    需要注意的是其中这个包装结构：

    ```js
    module.exports = {
      init: function() {
        // you init code here
      }
    };
    ```

    此包装结构是必须的，以 CMD 的形式暴露出一个`init()`方法供框架初始化组件使用。在此方法中的`this`为组件本身（意思是可以用`$(this)`来对组件进行操作）

5. 新建`config.js`文件，在 tab_switcher 中我选择了`coffee`格式，此文件如下：

    ```coffee
    module.exports =
      settings:
        resizable: false
        openConfig: false

      init: ->
        console.log $(@)

      configInit: ($dialog, modal, initParams) ->
        console.log $(@), arguments
    ```

    对应的`js`代码为：

    ```js
    module.exports = {
      settings: {
        resizable: false,
        openConfig: false
      },
      init: function() {
        return console.log($(this));
      },
      configInit: function($dialog, modal, initParams) {
        return console.log($(this), arguments);
      }
    };
    ```

    此文件向 CMD 暴露一个`settings`结构，存储了一些配置信息。在这个例子中将`resizable`和`openConfig`设为了`false`，框架会根据这些配置做相应的处理

    settings 可选的配置和默认值如下：

    ```coffee
    settings = {
      draggable: true, // 是否允许拖动
      resizable: true, // 是否允许缩放
      resizableHandles: null, // 哪些方向可以被缩放
      resizableContainer: null, // 允许在哪个容器中被缩放
      openConfig: true, // 是否在左键菜单点击设置的时候弹出对应的配置界面（同目录下的config.hbs和config.js）
      configPath: null, // 则此项可以指定点击设置的时候弹出的配置界面的位置（以便复用相似组件的配置方式）
      loadConfig: true, // 是否在弹出配置界面之前先去load当前保存的配置信息
      configInitParams: // 传递给configInit方法的初始参数（如果需要的话）
    };
    ```

    另外此文件还暴露了一个`init()`方法和一个`configInit()`方法。其中`init()`方法会在编辑模式下被调用，如果不需要可以不写，这个例子里只是简单的打了一行日志；而`configInit()`方法则会在配置界面被渲染到页面上后调用，其中参数`$dialog`代表配置界面本身，而`initParams`则是你配置在`settings.configInitParams`中的值（在多个组件复用一个组件的config时会有用）

    和`view.js`中的`init()`方法一样，`config.js`中的`init()`方法和`configInit()`方法中的`this`都是组件自身

6. 为了临时单独测试此组件的效果，我创建了`app/views/test/t.hbs`这个文件，并通过`{{inject}}`这个 helper 将组件引了进来

7. 启动应用，到`{www.domain.com}/test/t`看下效果吧
