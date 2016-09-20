oldParseJSON = $.parseJSON
$.extend
  parseJSON:(json) ->
    if !json and json != null
      null
    else
      oldParseJSON.apply(this, arguments)
# reuse the patched parseJSON
$.ajaxSetup
  converters:
    "text json": $.parseJSON
