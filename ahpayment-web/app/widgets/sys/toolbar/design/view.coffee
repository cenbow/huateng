show = (template, url) ->
  BATMAN.util.check_show ".toolbar", "#js-" + template + "-panel", "sys/toolbar/design/" + template, url, (configDialogId) ->
    BATMAN.scrollable "#js-" + template + "-panel .scrollable"

goBack = ->
  $("#js-bg-panel").hide()
  $("#js-font-panel").hide()
  $("#js-style-panel").hide()

changeBg = (evt) ->
  $("body").attr "style", "background-image:url(" + $(evt.target).attr("src") + ")"
  BATMAN.globalSettingChanged = true

$(document).on "click", "#js-show-bg", ->
  show "bg"

$(document).on "click", "#js-show-font", ->
  show "font"

$(document).on "click", "#js-design-go-back", ->
  goBack()

$(document).on "click", "#js-bg-panel img", ->
  changeBg evt
