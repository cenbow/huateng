viewUtil = require "common/util"
designUtil = require "design/util"
designEnv = require "design/environment"
mouseController = require "design/interactions/mouse_controller"

_save = (target, callback) ->
  viewUtil.update('/design/widget/param/' + $(target).attr("id"), {data: JSON.stringify({value: $(".text-content", target).html()})}, callback)

_editable = (target) ->
  that = this
  id = $(target).attr("id")
  $text = $(".text-content", target)

  _forceBlur = false

  # 在编辑状态下阻止click和mousedown事件的传播
  # click阻止resizable mousedown阻止draggable
  $text.click((evt) ->
    if $("#js-text-toolbar").length == 1
      evt.stopPropagation()
  )
  $text.mousedown((evt) ->
    if $("#js-text-toolbar").length == 1
      evt.stopPropagation()
  )

  # edit command some start
  makeCommand = (command, param) ->
    (userParam) ->
      p = param ? userParam
      p = p() if _.isFunction(p)
      document.execCommand(command, false, p)

  makeQuery = (command) ->
    ->
      value = document.queryCommandValue(command)
      value == true or value == "true"

  command = {
    bold: makeCommand("bold")
    italic: makeCommand("italic")
    strike: makeCommand("strikethrough")
    underline: makeCommand("underline")
    sub: makeCommand("subscript")
    sup: makeCommand("superscript")
    justifyfull: makeCommand("justifyfull")
    justifycenter: makeCommand("justifycenter")
    justifyleft: makeCommand("justifyleft")
    justifyright: makeCommand("justifyright")
  }

  state = {
    bold: makeQuery("bold")
    italic: makeQuery("italic")
    strike: makeQuery("strikethrough")
    underline: makeQuery("underline")
    sub: makeQuery("subscript")
    sup: makeQuery("superscript")
    justifyfull: makeQuery("justifyfull")
    justifycenter: makeQuery("justifycenter")
    justifyleft: makeQuery("justifyleft")
    justifyright: makeQuery("justifyright")
  }

  normalCommand = {
    link: makeCommand("createlink", -> prompt("请输入链接内容"))
    unlink: makeCommand("unlink")
  }

  dropDownCommand = {
    font: makeCommand("fontname")
    size: makeCommand("fontsize")
    color: makeCommand("forecolor")
  }

  customCallback = {
    size: (command, param) ->
      command(1)
      $text.html($text.html().replace(/font\-size: x-small;/g, "font-size: " + param + ";"))
  }

  controlCommand = {
    unformat: makeCommand("removeformat")
    undo: makeCommand("undo")
    redo: makeCommand("redo")
    ok: ->
      forceBlur()
    html: ->
      return if $("textarea", target).length > 0
      htmlStr = $.trim($text.html())
      $textArea = $("<textarea style='width:95%;height:90%;background-color:white'>" + htmlStr + "</textarea>")
      $text.hide()
      $text.after($textArea)
      $("#js-text-toolbar button").prop("disabled", true)
      $("#js-text-toolbar button[name=html]").html("ok").prop("disabled", false)
      $textArea.focus()

      $textArea.blur ->
        htmlStr = $.trim($textArea.val())
        $textArea.remove()
        $text.html(htmlStr).show()
        forceBlur()
  }
  # edit command some end

  $(target).dblclick (evt) ->
    evt.stopPropagation()

    # 已经是editable的情况下就啥也不干
    if $text.prop("isContentEditable")
      return

    $(designEnv.beforeConfigDialog).remove()
    designUtil.remove_handler()
    designEnv.beforeSelectedId = null

    $(target).css("cursor", "default")
    $text.prop("contenteditable", true)
    $text.css("cursor", "text")
    $text.focus()

    viewUtil.get_template("common/text/toolbar", (template) ->
      $(target).append(template({}))

      $toolbar = $("#js-text-toolbar", target)

      updateState = ->
        if $toolbar.length == 0
          return
        _(state).each((v, k) ->
          if v()
            $toolbar.find(".js-toggle-group button[name=" + k + "]").addClass("active")
          else
            $toolbar.find(".js-toggle-group button[name=" + k + "]").removeClass("active")
        )

      $(target).mouseup(updateState)
      $(target).keyup(updateState)

      _(command).each((v, k) ->
        $toolbar.find(".js-toggle-group button[name=" + k + "]").click(->
          v()
          updateState()
        )
      )

      _(normalCommand).each((v, k) ->
        $toolbar.find(".js-normal-group button[name=" + k + "]").click(v)
      )

      _(dropDownCommand).each((v, k) ->
        $btn = $toolbar.find(".js-dropdown-group button[name=" + k + "]")
        $btn.click(->
          $(this).siblings().removeClass("active")
          $(this).toggleClass("active")
          $(this).siblings().find(".dropdown-menu").hide()
          $(this).find(".dropdown-menu").toggle()
        )
        $btn.find(".dropdown-menu").click((evt) ->
          evt.stopPropagation()
        )
        $btn.find(".dropdown-menu a").click((evt) ->
          evt.stopPropagation()
          if $(this).hasClass("js-custom")
            customValue = prompt($(this).data("value"))
            if customCallback[k]
              customCallback[k](v, customValue)
            else
              v(customValue)
          else
            v($(this).data("value"))
        )
      )

      _(controlCommand).each((v, k) ->
        $toolbar.find(".js-control-group button[name=" + k + "]").click(v)
      )
    )

  # 阻止事件传播 避免触发全局热键
  $text.keydown (evt) ->
    evt.stopPropagation()

  # 绑定esc按键
  $text.keyup (evt) ->
    evt.stopPropagation()
    if evt.which == 27 # esc
      forceBlur()

  $text.blur (evt) ->
    force = _forceBlur
    _forceBlur = false
    $toolbar = $("#js-text-toolbar", target)
    if !force && $toolbar.length == 1 && $toolbar.has(":hover").length > 0
      $(this).focus()
      return true

    $(target).css("cursor", "")
    $text.prop("contenteditable", false)
    $text.css("cursor", "")

    $toolbar.remove()
    _save(target)

  forceBlur = ->
    _forceBlur = true
    $text.blur()

module.exports =
  settings:
    resizable: false
    openConfig: false

  init: ->
    document.execCommand("styleWithCSS", false, true)

    $(@).click((evt) ->
      if $("#js-text-toolbar").length == 1
        evt.stopPropagation()
      else
        mouseController.resizable(evt)
    )
    _editable(@)
