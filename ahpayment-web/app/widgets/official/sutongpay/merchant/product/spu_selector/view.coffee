module.exports =
  init: ->
    that = @
    s = (selector) ->
      $ selector, that

    category_column_template_name = "official/sutongpay/merchant/product/spu_selector/category_column"
    category_template_name = "official/sutongpay/merchant/product/spu_selector/category"
    spu_column_template_name = "official/sutongpay/merchant/product/spu_selector/spu_column"

    util = require("common/util")

    util.get_template_html category_template_name, (html) ->
      Handlebars.registerPartial "category", html


    # Render level 1 column
    util.magic_render s(".spu-selector"), category_column_template_name, "/api/mall/categories/0/children"

    $(that).on
      click: ->
        $(this).addClass "click-active"
        $(this).siblings().removeClass "click-active"
        s(".control a").removeClass("ui-button-serOrange").addClass("ui-button-hwhite")

        # remove next all category-column
        $(this).parent().parent().nextAll().remove()
        if $(this).attr("has-children") is "true"
          util.magic_render s(".spu-selector"), category_column_template_name, "/api/mall/categories/" + $(this).attr("id") + "/children"
        else
          util.magic_render s(".spu-selector"), spu_column_template_name, "/api/mall/categories/" + $(this).attr("id") + "/spus"

      mouseenter: ->
        $(this).addClass "hover-active"

      mouseleave: ->
        $(this).removeClass "hover-active"
    , ".category-column li"

    $(that).on
      click: ->
        $(this).addClass "click-active"
        $(this).siblings().removeClass "click-active"
        $(".control a").removeClass("ui-button-hwhite").addClass("ui-button-serOrange")

      mouseenter: ->
        $(this).addClass "hover-active"

      mouseleave: ->
        $(this).removeClass "hover-active"
    , ".spu-column li"

    s(".control a").click (evt) ->
      evt.preventDefault()
      if $(this).hasClass("ui-button-serOrange")
        spuId = s(".spu-column li.click-active").attr("id")
        window.location = "step2?spuId=" + spuId

