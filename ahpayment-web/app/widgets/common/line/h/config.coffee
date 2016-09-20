module.exports =
  settings:
    resizableHandles: "e,w"
    loadConfig: false

  configInit: ($panel)->
    styleUtil = require "design/style"

    $t = $(@)
    styleUtil.load_value_to_argument "border-top"
    $(".js-arguments", $panel).each ->
      styleUtil.init_slider this
      styleUtil.init_color_picker this
