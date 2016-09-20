module.exports =
  registerInitialize: (type, func) ->
    $(document).on "initialize", "[data-init-type~=#{type}]", ->
      return if _.contains($(this).data("initedType"), type)
      func.apply this, Array.prototype.slice.call(arguments, 1)
      if not $(this).data("initedType")
        $(this).data("initedType", [])
      $(this).data("initedType").push(type)

  initializeAll: ->
      $("[data-init-type]").trigger "initialize", arguments
