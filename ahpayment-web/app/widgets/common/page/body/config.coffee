module.exports =
  settings:
    draggable: false
    resizable: false
    openConfig: false

  init: ->
    designEnv = require "design/environment"

    require("design/interactions/mouse_controller").droppable $(this)
    $(this).click ->
      if designEnv.beforeSelectedId
        require("design/util").remove_handler()
        designEnv.beforeSelectedId = null
