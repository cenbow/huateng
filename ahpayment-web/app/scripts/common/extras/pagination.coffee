require("common/initialize_helper").registerInitialize "pagination", ->
  pageSize = $("#js-list-size", this).val() or 20
  $("#js-list-pagination", this).pagination $("#js-list-total", this).text() or $("#js-list-total", this).val(),
    num_display_entries: 5
    num_edge_entries: 2
    current_page: if $.query.get('pageNo') then $.query.get('pageNo') - 1 else 0
    prev_text: "上一页"
    next_text: "下一页"
    items_per_page: pageSize
    link_to: "javascript:void(0)"
    callback: (page_id) ->
     
      window.location.search = $.query.set('pageNo', parseInt(page_id) + 1).set('size', pageSize).toString()
