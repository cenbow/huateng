module.exports = init: ->
  util = require("common/util")
  image = require("common/extras/image")
  spuId = $.query.get("spuId")
  spuId = $("input[name=spuId]").val() unless spuId
  spuTemplateName = "official/sutongpay/merchant/product/publish/spu_properties"
  skuTemplateName = "official/sutongpay/merchant/product/publish/sku_properties"
  tableTemplateName = "official/sutongpay/merchant/product/publish/table"
  singleColumnTableTemplateName = "official/sutongpay/merchant/product/publish/single_column_table"
  oldStockValues = {}
  oldPriceValues = {}
  oldOuterIdValues = {}

  # calc price
  $(".item-publish-box #input-price").val ($(".item-publish-box #input-price").val() / 100).toFixed(2)
  $(".item-publish-box #input-name").blur (evt) ->
    evt.preventDefault()
    if $(".item-publish-box  #input-name").val().length > 20
      humane.error "商品标题不能超过20个字."
      $(".item-publish-box  #input-name").val($(".item-publish-box  #input-name").val().substr(0,20))

  $(".item-publish-box #input-price").blur (evt) ->
    evt.preventDefault()
    if isNaN($(".item-publish-box #input-price").val())
      humane.error "商品一口价必须是数字."
      $(".item-publish-box #input-price").val(1)

  $.ajax(
    type: "GET"
    url: "/api/mall/spus/" + spuId
  ).done (data) ->
    spuAttributes = data.data?["spuAttributes"]
    skuAttributes = data.data?["skuAttributes"]

    # render spu properties
    util.get_template spuTemplateName, (template) ->
      content = template(entries: spuAttributes)
      $(".spu").append content

    # render sku properties
    util.get_template skuTemplateName, (template) ->
      content = template(entries: skuAttributes)
      $(".skus").append content
      selectedSkus = $(".item-publish-box .control-group .skus").data("skus")
      _(selectedSkus).each (sku) ->
        $(".sku-value[data-id=" + sku.attributeValue1 + "]").prop "checked", true
        $(".sku-value[data-id=" + sku.attributeValue2 + "]").prop "checked", true

        # init stock & price
        realId = undefined
        if not sku.attributeValue2? or sku.attributeValue2 is `undefined`
          realId = sku.attributeValue1.toString()
        else
          realId = _([sku.attributeValue1, sku.attributeValue2]).sortBy((num) ->
            num
          ).join(",")
        oldStockValues[realId] =
          id: sku.id
          stock: sku.stock

        oldPriceValues[realId] = (sku.price / 100).toFixed(2)
        oldOuterIdValues[realId] = sku.outerId

      if $(".item-publish-box input[name=itemId]").length is 1
        $(".sku-value").prop "disabled", true
        $(".skus input:first").change()
      _.defer ->
        util.elasticHeight ".page_body"

  $(document).on "blur",".skus-table input", (evt) ->
    _($(".skus-table input[name=stock]")).each (e) ->
      if isNaN($(e).val())
        $(e).val(1)
    
    _($(".skus-table input[name=price]")).each (e) ->
      if isNaN($(e).val())
        $(e).val(1)

  # generate table when checkbox changes
  $(document).on "change", ".skus input", (evt) ->
    _($(".skus-table input[name=stock]")).each (e) ->
      if oldStockValues[$(e).data("realId")]
        oldStockValues[$(e).data("realId")].stock = $(e).val()
      else
        oldStockValues[$(e).data("realId")] =
          id: null
          stock: $(e).val()

    _($(".skus-table input[name=price]")).each (e) ->
      oldPriceValues[$(e).data("realId")] = $(e).val()

    _($(".skus-table input[name=outerId]")).each (e) ->
      oldOuterIdValues[$(e).data("realId")] = $(e).val()

    skuList = fetchSKUList()
    if skuList.length is 0
      $(".skus-table").empty()
      return
    if skuList.length is 1
      util.get_template singleColumnTableTemplateName, (template) ->
        $(".skus-table").empty().append template(entries: skuList[0])
        refillTable oldStockValues, oldPriceValues, oldOuterIdValues

    else
      util.get_template tableTemplateName, (template) ->
        entries = generateTables(skuList[0], skuList[1])
        $(".skus-table").empty().append template(entries: entries)
        refillTable oldStockValues, oldPriceValues, oldOuterIdValues


  $(".images li").click ->
    $(this).addClass "selected"
    $("a", this).removeClass "hide"
    $(this).siblings().removeClass "selected"
    $(this).siblings().find("a").addClass "hide"
    $(".main-image img").attr "src", $("img", this).attr("src")

  $(".images li a").click ->
    $image = $(this).siblings()
    $image.removeAttr("src").replaceWith $image.clone()

  $("#replace-image-button").click (evt) ->
    evt.preventDefault()
    image.selector().done (image_url) ->
      $(".images .selected img").attr "src", image_url
      $(".main-image img").attr "src", image_url


  $("#publish-item-button").click (evt) ->
    btn = this
    evt.preventDefault()
    theHTML = document.getElementById("noiseWidgIframe").contentWindow.document.getElementsByTagName("body")[0].innerHTML
    theHTML = theHTML.replace(/^\s+/, "");
    theHTML = theHTML.replace(/\s+$/, "");
    
    # Remove style attribute inside any tag */
    ###theHTML = theHTML.replace(/ style="[^"]*"/g, "");###

    # Replace improper BRs */
    theHTML = theHTML.replace(/<br>/g, "<br />");
    
    # Remove BRs right before the end of blocks */
    theHTML = theHTML.replace(/<br \/>\s*<\/(h1|h2|h3|h4|h5|h6|li|p)/g, "</$1");
    
    # Replace improper IMGs */
    theHTML = theHTML.replace(/(<img [^>]+[^\/])>/g, "$1 />");
    
    # Remove empty tags */
    theHTML = theHTML.replace(/(<[^\/]>|<[^\/][^>]*[^\/]>)\s*<\/[^>]*>/g, "");
    if $(".item-publish-box  #input-name").val().length is 0
      humane.error "商品标题不能为空"
      return
    if $(".item-publish-box  #input-price").val().length is 0
      humane.error "商品一口价不能为空"
      return
    item = item:
      spuId: spuId
      name: $(".item input[name=name]").val()
      price: ($(".item input[name=price]").val() * 100).toFixed()
      mainImage: $("#itemMainImage").attr("src")

    images =
      image1: $("#itemMainImage").attr("src")
      image2: $("#itemMainImage").attr("src")
      image3: $("#itemMainImage").attr("src")
      image4: $("#itemMainImage").attr("src")
      strDetail: theHTML
      

    skuList = fetchSKUList()
    list = []
    if skuList.length is 1
      _.each $(".skus-table tbody tr"), (tr) ->
        columnKey = $(".skus-table thead th:first").text()
        columnId = $("td.column", tr).data("id")
        columnName = $("td.column", tr).data("name")
        price = ($("td input[name=price]", tr).val() * 100).toFixed()
        stock = $("td input[name=stock]", tr).val()
        outerId = $("td input[name=outerId]", tr).val()
        dbId = $("td input[name=stock]", tr).data("dbId")
        list.push
          id: dbId
          attributeKey1: columnKey
          attributeName1: columnName
          attributeValue1: columnId
          price: price
          stock: stock
          outerId: outerId
    else
      mainColumnKey = undefined
      mainColumnName = undefined
      mainColumnId = undefined
      _.each $(".skus-table tbody tr"), (tr) ->
        unless $("td.main-column", tr).attr("rowspan") is `undefined`
          mainColumnKey = $(".skus-table thead th:first").text()
          mainColumnName = $("td.main-column", tr).data("name")
          mainColumnId = $("td.main-column", tr).data("id")
        columnKey = $(".skus-table thead th:nth-child(2)").text()
        columnId = $("td.column", tr).data("id")
        columnName = $("td.column", tr).data("name")
        price = ($("td input[name=price]", tr).val() * 100).toFixed()
        stock = $("td input[name=stock]", tr).val()
        outerId = $("td input[name=outerId]", tr).val()
        dbId = $("td input[name=stock]", tr).data("dbId")
        list.push
          id: dbId
          attributeKey1: mainColumnKey
          attributeName1: mainColumnName
          attributeValue1: mainColumnId
          attributeKey2: columnKey
          attributeName2: columnName
          attributeValue2: columnId
          price: price
          stock: stock
          outerId: outerId



    # imageDetail
    item.itemDetail = images

    # list
    item.skus = list
    if $(".item-publish-box input[name=itemId]").length is 1
      $.ajax(
        type: "PUT"
        url: "/api/mall/items/" + $(".item-publish-box input[name=itemId]").val()
        contentType: "application/json"
        data: JSON.stringify(item)
      ).done ->
        humane.log "修改商品成功!"
        window.location = $(btn).data("url")
    else
      # ajax post insert
      $.ajax(
        type: "POST"
        url: "/api/mall/items"
        contentType: "application/json"
        data: JSON.stringify(item)
      ).done ->
        humane.log "创建商品成功!"
        window.location = $(btn).data("url")


  fetchSKUList = ->
    skuList = []
    $.each $(".skus .sku-property"), ->
      keys = []
      $.each $(this).find("input"), ->
        if $(this).prop("checked")
          key = $(this).parents(".attr").find("label").data("name")
          id = $(this).data("id")
          value = $(this).data("value")
          keys.push
            id: id
            value: value
            key: key
      skuList.push keys  if keys.length isnt 0
    skuList

  generateTables = (skuList0, skuList1) ->
    entries = []
    if skuList0.length <= skuList1.length
      keys = skuList0
      values = skuList1
    else
      keys = skuList1
      values = skuList0
    _.each keys, (k) ->
      k["values"] = []
      _.each values, (v) ->
        realId = _([k.id, v.id]).sortBy((num) ->
          num
        ).join(",")
        k["values"].push
          realId: realId
          value: v
      entries.push k
    entries

  refillTable = (oldStockValues, oldPriceValues, oldOuterIdValues) ->
    _($(".skus-table input[name=stock]")).each (e) ->
      if oldStockValues[$(e).data("realId")]
        $(e).data "dbId", oldStockValues[$(e).data("realId")].id
        $(e).val oldStockValues[$(e).data("realId")].stock

    _($(".skus-table input[name=price]")).each (e) ->
      $(e).val oldPriceValues[$(e).data("realId")]

    _($(".skus-table input[name=outerId]")).each (e) ->
      $(e).val oldOuterIdValues[$(e).data("realId")]
  
