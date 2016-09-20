util = require("/common/util")
url = "/api/captcha/get"

_init = ($input, $img, $keyupImg, $btn) ->
  debugger
  return false  if $input is `undefined`
  return false  if $img is `undefined`
  codekey = $img.attr("data-key") || "default"
  _refresh($img,codekey)

  $img.on "click", (evt) ->
    _refresh($img,codekey)

_refresh = ($img,key) ->
  url += '?key=' + key
  rnd = new Date().getTime()
  url += '&rnd=' + rnd
  $img.attr("src",url)
  $img.css("cursor","pointer")

captcha = ($input, $img, $btn) ->
  debugger
  _init($input, $img, $btn)

module.exports =
  captcha: captcha
