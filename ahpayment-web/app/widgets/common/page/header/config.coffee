module.exports =
  settings:
    resizableHandles: "s"
    resizableContainer: "parent"
    configPath: "sys/style/view"
    loadConfig: false

  init: ->
    designEnv = require "design/environment"
    require("design/interactions/mouse_controller").droppable $(this)
