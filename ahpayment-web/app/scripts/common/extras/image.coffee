imageSelectorDialogContent= """
  <ul class="image-selector-list">
  </ul>
  <div class="popup-dialog-extra">
    <span class="btn js-btn-upload" style="position: relative; overflow: hidden;">
      上传图片
      <input id="js-image-upload" style="position: absolute; top: 0; right: 0; opacity: 0; filter: alpha(opacity=0); cursor: pointer;" type="file" name="file">
    </span>
    <div class="pagination-dd" id="js-list-pagination" style="float: right;"></div>
  </div>
"""

module.exports =
  selector: (rootStyle) ->
    popup = require "common/extras/popup"

    config =
      rootStyle: rootStyle
      title: "选择图片"
      content: imageSelectorDialogContent
    popup.show config, ($floatDialog, deferred) ->
      $imageList = $(".image-selector-list", $floatDialog)
      refreshList = (pageNo) ->
        pageNo = $imageList.data("pageNo") unless pageNo
        $imageList.spin()
        $.ajax
          url: "/images/user?size=15&p=#{pageNo}"
          type: "GET"
          cache: false
          success: (response) ->
            $imageList.spin(false).data("pageNo", pageNo)
            $("#js-list-pagination", $floatDialog).pagination response.data.total,
              num_display_entries: 3
              num_edge_entries: 1
              current_page: pageNo - 1
              prev_text: "上一页"
              next_text: "下一页"
              items_per_page: 15
              link_to: "javascript:void(0)"
              callback: (pageId) ->
                refreshList(pageId + 1)
            $imageList.empty()
            $.each response.data.data, ->
              $imageList.append("<li data-url=\"#{@url}\"><img src=\"#{@url}\"/><button class=\"btn btn-mini btn-trash\" data-id=\"#{@id}\"><i class=\"icon-trash\"></button></li>")
            $("li:first", $imageList).click()

      refreshList(1)

      $imageList.on
        click: ->
          $(this).addClass("selected").siblings().removeClass("selected")
        mouseenter: ->
          $(this).addClass("hover")
        mouseleave: ->
          $(this).removeClass("hover")
      , "li"

      $imageList.on "click", ".btn-trash", (evt) ->
        evt.stopPropagation()
        popup.confirm(rootStyle, "warn", "确认删除吗？")
          .done =>
            $.ajax
              url: "/images/#{$(@).data('id')}"
              type: "DELETE"
              success: ->
                refreshList()

      $("#js-image-upload", $floatDialog).fileupload
        url: "/images/upload"
        dataType: "html"
        start: ->
          $(".js-btn-upload", $floatDialog).addClass("disabled").spin()
        done: ->
          refreshList(1)
        always: ->
          $(".js-btn-upload", $floatDialog).removeClass("disabled", false).spin(false)

      $(".js-btn-ok", $floatDialog).off("click").click ->
        if $(".selected", $floatDialog).length == 0
          popup.alert rootStyle, "warn", "请先点击并选中一个图片"
          return
        popup.close()
        deferred.resolve $(".selected", $floatDialog).data("url")
