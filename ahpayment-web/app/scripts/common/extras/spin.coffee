zIndex = 19999

spin = (falseToRemove) ->
  if falseToRemove == false
    $(".spin-disable-cover").remove()
  else
    return if $(".spin-disable-cover").length > 0
    $("<div class=\"spin-disable-cover\" style=\"height: #{$(document).height()}px;z-index: #{zIndex}\"><div class=\"spin-container\"></div></div>").appendTo("body").find(".spin-container").spin()

module.exports =
  spin: spin
