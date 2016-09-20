templatesCache = {}
tpl_root = "/templates/"

_debug_url = (url) ->
  if url.indexOf("?") isnt -1
    url += "&__debug="
  else
    url += "?__debug="
  url + Date.now()

module.exports =
  check_show: (target, selector, template_name, url, callback) ->
    if $(selector).length > 0
      $(selector).show()
    else
      @magic_render target, template_name, url, callback

  fetch: (url, callback) ->
    $.get _debug_url(url), callback

  create: (url, requestData, callback, method) ->
    _method = "POST"
    _method = method  if method
    success = undefined
    error = undefined
    if callback
      if _.isFunction(callback)
        success = callback
      else
        success = callback.success
        error = callback.error
    options =
      url: _debug_url(url)
      type: _method
      dataType: "json"
      data: requestData
      beforeSend: (XMLHttpRequest) ->


      #ShowLoading();
      complete: (XMLHttpRequest) ->


      #HideLoading();
      success: success
      error: error

    if _.isFunction(requestData)
      options.success = requestData
      options.data = {}
    $.ajax options

  update: (url, data, callback) ->
    @create url, data, callback, "PUT"

  del: (url, data, callback) ->
    @create url, data, callback, "DELETE"

  get_template_html: (template_name, callback, pass_engine) ->
    @fetch (if pass_engine then ("/api/template/" + template_name) else (tpl_root + template_name + ".hbs")), (html) ->
      callback html  if callback


  get_template: (template_name, callback, pass_engine) ->
    template = undefined
    if templatesCache[template_name]
      template = templatesCache[template_name]
      callback template  if callback
    else
      @get_template_html template_name, ((html) ->
        template = Handlebars.compile(html)
        templatesCache[template_name] = template
        callback template  if callback
      ), pass_engine

  magic_render: (selector, template_name, url, callback) ->
    that = this
    @get_template template_name, (template) ->
      if url is `undefined` or _.isFunction(url)
        $content = $(template({}))
        $(selector).append $content
        if _.isFunction(url)
          url $content.attr("id")
        else callback $content  if callback
      else
        that.fetch url, (data) ->
          data = {}  if data is `undefined` or not data?
          $content = $(template(data))
          $(selector).append $content
          callback $content, data  if callback



  fix_rgb2hex: (css) ->
    return null  if css is `undefined`
    rgb = css.match(/rgb\(.*\)/)
    if rgb?
      css = $.trim(css.replace(rgb, "")) #fuck ie
      css + " " + @rgb2hex(rgb[0])
    else
      css

  rgb2hex: (rgb) ->
    return rgb  if not rgb? or rgb.indexOf("#") is 0
    _rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/)
    return null  unless _rgb?
    "#" + ("0" + parseInt(_rgb[1], 10).toString(16)).slice(-2) + ("0" + parseInt(_rgb[2], 10).toString(16)).slice(-2) + ("0" + parseInt(_rgb[3], 10).toString(16)).slice(-2)

  rgb2Object: (rgb) ->
    return {}  if not rgb? or rgb.indexOf("#") is 0
    _rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/)
    unless _rgb?
      _rgb = rgb.match(/^rgba\((\d+),\s*(\d+),\s*(\d+),\s*([0,1].?\d*)\)$/)
      return null  unless _rgb?
    color: "#" + ("0" + parseInt(_rgb[1], 10).toString(16)).slice(-2) + ("0" + parseInt(_rgb[2], 10).toString(16)).slice(-2) + ("0" + parseInt(_rgb[3], 10).toString(16)).slice(-2)
    transparent: (if _rgb.length is 5 then (_rgb[4] * 10).toFixed(0) else 10)

  hex2Object: (hex) ->
    hex = hex.substr(1)  if hex[0] is "#"
    if hex.length is 3
      temp = hex
      hex = ""
      temp = /^([a-f0-9])([a-f0-9])([a-f0-9])$/i.exec(temp).slice(1)
      i = 0

      while i < 3
        hex += temp[i] + temp[i]
        i++
    triplets = /^([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})$/i.exec(hex).slice(1)
    r: parseInt(triplets[0], 16)
    g: parseInt(triplets[1], 16)
    b: parseInt(triplets[2], 16)

  stringify: (obj) ->
    t = typeof (obj)
    if t isnt "object" or obj is null

      # simple data type
      obj = "\"" + obj + "\""  if t is "string"
      String obj
    else

      # recurse array or object
      n = undefined
      v = undefined
      json = []
      arr = (obj and obj.constructor is Array)
      for n of obj
        v = obj[n]
        continue  if v is `undefined` or v is null or v is ""
        t = typeof (v)
        if t is "string"
          v = "\"" + v + "\""
        else v = JSON.stringify(v)  if t is "object" and v isnt null
        json.push ((if arr then "" else "\"" + n + "\":")) + String(v)
      ((if arr then "[" else "{")) + String(json) + ((if arr then "]" else "}"))

  serializeFormJSON: (form_selector) ->
    o = {}
    a = form_selector.serializeArray()
    $.each a, ->
      value = @value or ""
      if value is "on" or value is "true"
        value = true
      else value = false  if value is "false"
      isArray = false
      if _.str.endsWith(@name, "[]")
        isArray = true
        @name = @name.slice(0, -2)
      if o[@name]
        o[@name] = [o[@name]]  unless o[@name].push
        o[@name].push value
      else
        if isArray
          o[@name] = [value]
        else
          o[@name] = value
      true

    $(form_selector).find("input[type=checkbox]").each ->
      o[$(this).attr("name")] = false  unless $(this).prop("checked")
    o

  setFormWithJSON: (form_selector, json) ->
    return  if not json? or json is ""
    $("input, select", $(form_selector)).each ->
      value = json[$(this).attr("name")]
      if $(this).attr("type") is "checkbox"
        if value
          $(this).prop "checked", true
        else
          $(this).prop "checked", false
      else if $(@).is("select")
        value = value.toString()
        $(this).val value
      else
        $(this).val value  if value


  getComponentId: (target) ->
    $(target).parents(".js-comp:eq(0)").attr "data-comp-id"

  mod_8: (num) ->
    num - num % 8

  elasticHeight: (container) ->
    components = $(".js-comp", container)
    max_component_height = _.max(_.map(components, (component) ->
      $(component).position().top + $(component)[0].scrollHeight
    ))
    container_min_height = parseInt($(container).css("min-height"))
    if max_component_height > container_min_height
      $(container).height max_component_height
    else
      $(container).height container_min_height


  check_if_ie6: () ->
    return $.browser.msie && ($.browser.version == "6.0")
