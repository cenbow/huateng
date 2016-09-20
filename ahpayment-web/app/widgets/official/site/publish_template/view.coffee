module.exports =
  init: ->
    viewUtil = require "common/util"
    original_color = $("input[name=color]", "#publish-site-form").val()
    $("div[data-id=" + original_color + "]", ".color-pick").addClass("selected").append "<span class='icon-selected'></span>"
    $("#publish-site-form #js-change-image").click (evt) ->
      evt.preventDefault()
      BATMAN.common_image_selector_config.show_change_image evt, (image_url) ->
        $("#publish-site-form #param-image-url").attr "src", image_url
        $("#publish-site-form input[name='image']").val image_url


    $(document).on "click", "#publish-site-cancel, .close", ->
      $(this).parents(".dh-modal-next:first").fadeOut ->
        $(this).remove()


    $("div", ".color-pick").click ->
      data_id = $(this).attr("data-id")
      $("input[name=color]", "#publish-site-form").val data_id
      $("div", ".color-pick").removeClass "selected"
      $("div", ".color-pick").children("span").remove()
      $("div[data-id=" + data_id + "]", ".color-pick").addClass("selected").append "<span class='icon-selected'></span>"

    $(document).on "click", "#publish-site-submit", (evt) ->
      evt.preventDefault()
      that = this
      origin_description = $(".dh-modal-next .modal-body textarea").val()
      origin_content = $(".dh-modal-next .modal-body").html()
      $.ajax
        type: "POST"
        url: "/api/site/publish"
        data: $("#publish-site-form").serialize()
        beforeSend: ->
          viewUtil.get_template "official/site/publish_template/publishing", (template) ->
            content = $(template({})).hide().fadeIn()
            $(".dh-modal-next .modal-body").html content


        success: (data) ->
          viewUtil.get_template "official/site/publish_template/published", (template) ->
            content = $(template({})).hide().fadeIn()
            $(".dh-modal-next .modal-body").html content


        error: (data) ->
          humane.log data.responseText
          $(".dh-modal-next .modal-body").html origin_content
          $(".dh-modal-next .modal-body textarea").val origin_description

