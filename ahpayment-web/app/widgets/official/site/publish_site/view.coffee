module.exports =
  init: ->
    viewUtil = require "common/util"

    $(document).on "click", "#publish-site-cancel, .close", ->
      $(this).parents(".dh-modal-next:first").fadeOut ->
        $(this).remove()


    $(document).on "click", "#publish-site-submit", (evt) ->
      evt.preventDefault()
      that = this
      $.ajax
        type: "POST"
        url: "/api/site/release"
        data:
          id: $("#siteId").val()

        beforeSend: ->
          viewUtil.get_template "official/site/publish_site/publishing", (template) ->
            origin_content = $(".dh-modal-next .modal-body").html()
            content = $(template({})).hide().fadeIn()
            $(".dh-modal-next .modal-body").html content


        success: (data) ->
          viewUtil.get_template "official/site/publish_site/published", (template) ->
            content = $(template({})).hide().fadeIn()
            $(".dh-modal-next .modal-body").html content


        error: (data) ->
          humane.log data.responseText
          $(".dh-modal-next .modal-body").html origin_content
