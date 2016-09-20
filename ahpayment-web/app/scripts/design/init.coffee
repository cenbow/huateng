$ ->
  require "common/init"

  designEnv = require "design/environment"
  designUtil = require "design/util"
  initHelper = require "common/initialize_helper"
  compHelper = require "design/comp_helper"
  cmdHelper = require "common/commonjs_helper"

  requireAll = cmdHelper.requireByRegex

  requireAll /^design\/extras\//

  require "design/interactions/key_controller"

  $(document).on "click", ".app-modal .close, .cancel", ->
    if $(this).hasClass("js-close-self")
      $(this).parents(".app-modal").remove()
    else
      $(".app-modal").hide()

  $(document).on "click", ".modal .close, .cancel", ->
    $(this).parents(".modal").remove()

  $(document).on "click", "#js-change-style", (evt) ->
    compHelper.openConfig evt, "sys/style/view"

  $(document).on "click", "#js-change-style-no-bg", (evt) ->
    compHelper.openConfig evt, "sys/style/no_bg"

  #disable all a-href and button in design mode
  $(document).on "click", "a", (evt) ->
    evt.preventDefault()

  initialize = ->
    $(".js-comp").each ->
      path = $(@).data("compPath") + "/config"
      compHelper.initComponentConfig path, $(@)

    designEnv.pageData = $.parseJSON($('#pageData').val()) || {};
    _.each $.parseJSON($('#headFootData').val()),  (v, k) ->
      designEnv.pageData[k] = v;

    initHelper.initializeAll()

    $(".page a, .page button").click (evt) ->
      evt.preventDefault()

  # Push State to History
  History.Adapter.bind window, 'statechange', ->
    State = History.getState();

    $.get '/design/body/' + State.data['id'], (data) ->
      $('.page_body').hide 'slide', direction: 'left', ->
        $(this).remove()
        $('.page_header').after(data).show 'slide', direction: 'right'
        initialize()

  initialize()
  # tools only initialize once
  _.each ['4bar', 'comp', 'design', 'page'],  (component) ->
    require "sys/toolbar/#{component}/view"
