module.exports =
  settings:
    loadConfig: false

  configInit: ($panel) ->
    viewUtil = require "common/util"

    $target = $(this)

    $("#js-video-src").val $("iframe", $target).attr("src")
    $("#js-video-ok").click (evt) ->
      video_source = $("#js-video-src").val()
      viewUtil.update "/design/widget/param/" + $target.attr("id"),
        data: viewUtil.stringify(src: video_source)
      , ->
        $("iframe", $target).attr "src", video_source
        $panel.remove()
