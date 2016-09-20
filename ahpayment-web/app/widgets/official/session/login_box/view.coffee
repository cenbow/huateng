module.exports =
  init: ->
    $(".login-header-title").each ->
      $(@).click ->
        $(".login-header-title").removeClass "active"
        $(@).addClass "active"
        curform = $(@).attr("data-ref")
        $("form").hide()
        $(curform).show()
    $("input[name=target]").val $.query.get("target")
    $("#signin-submit").click (evt) ->
      evt.preventDefault()
      require("common/util").create "/api/user/login", $("#signin-form").serialize(),
        success: (data) ->
          window.location = data.data
    $("#signin-card-submit").click (evt) ->
      evt.preventDefault()
      require("common/util").create "/api/wingcard/login", $("#signin-card-form").serialize(),
        success: (data) ->
          window.location = data.data
