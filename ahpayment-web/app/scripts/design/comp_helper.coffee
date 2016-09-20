designUtil = require "design/util"
mouseController = require "design/interactions/mouse_controller"
cmdHelper = require "common/commonjs_helper"

initComponentAll = (path, $comp, $container) ->
  initComponentView path + "/view", $comp
  initComponentConfig path + "/config", $comp, $container

initComponentView = (path, $comp) ->
  if cmdHelper.hasModule path
    comp = require path
    comp.init?.call($(@))

initComponentConfig = (path, $comp, $container) ->
  if cmdHelper.hasModule path
    comp = require path
    comp.init?.call($comp, $container)
    # prepare settings
    settings =
      draggable: true

      resizable: true
      resizableHandles: null
      resizableContainer: null

      openConfig: true
      configPath: path
      loadConfig: true
      configInitParams: null
    $.extend settings, comp.settings if comp.settings
    # draggable
    mouseController.draggable($comp) if settings.draggable
    # resizable
    if settings.resizable
      $comp.click (evt) ->
        mouseController.resizable evt, settings.resizableHandles, settings.resizableContainer
    # dbclick to open config
    if settings.openConfig
      $comp.dblclick (evt) ->
        openConfig evt, settings.configPath, settings.loadConfig, settings.configInitParams

openConfig = (evt, configPath, loadConfig, configInitParams) ->
  evt.stopPropagation()

  #close before config dialog
  $(designEnv.beforeConfigDialog).remove()

  #close image select dialog
  $("#js-image-select-panel").remove()
  url = "/design/widget/param/" + designEnv.beforeSelectedId  if loadConfig
  viewUtil.magic_render ".toolbar", configPath, url, ($configDialog, data) ->
    designEnv.beforeConfigDialog = $configDialog
    if cmdHelper.hasModule(configPath)
      widget = require(configPath)
      model = undefined
      if widget.model

        # init models map if not exist
        widget.models = {}  unless widget.models

        # init model if not exist
        widget.models[designEnv.beforeSelectedId] = $.extend(true, {}, widget.model)  unless widget.models[designEnv.beforeSelectedId]
        data = data or {}
        model = $.extend(true, widget.models[designEnv.beforeSelectedId], data["data"])
        rivets.bind $configDialog, model
        $(".modal-body", $configDialog).slimScroll height: $configDialog.height() - 60 + "px"
        $(".js-submit", $configDialog).click (evt) ->
          viewUtil.update "/design/widget/param/" + designEnv.beforeSelectedId,
            data: JSON.stringify(data: model)

          $configDialog.remove()

      widget.configInit.call $("#" + designEnv.beforeSelectedId), $configDialog, model, configInitParams  if widget.configInit
    mouseController.draggable $configDialog


module.exports =
  initComponentAll: initComponentAll
  initComponentView: initComponentView
  initComponentConfig: initComponentConfig
  openConfig: openConfig
