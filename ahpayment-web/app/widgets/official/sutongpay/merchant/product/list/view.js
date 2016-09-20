module.exports = {
  init: function () {

    $('#js-clear-filter').click(function(){
      $(this).closest("form").find("input:text").val("")
    })

    // checkall box
    $('th input[type=checkbox]').on('change', function(){
      if($(this).prop('checked')){
        $('.d-check input').attr('checked', true)
      } else {
        $('.d-check input').attr('checked', false)
      }
    })

    // item offShelf
    var $statusBtn = $('#js-item-status-btn')
    var $statusTitle = $('#js-item-status-title')
    var status = $.query.get("status");
    if(status !== 1){
      $(".my-onsell-tab a:first").addClass("current")
      $statusBtn.text("上架")
      $statusTitle.html("未上架的商品")
      status = 1
    } else {
      $(".my-onsell-tab a:last").addClass("current")
      status = -1
    }

    $statusBtn.click(function (evt) {
      evt.preventDefault();
      var ids = _.map($('.d-check input:checked'), function (check) {
        return $(check).val()
      }).join(',')
      if(ids == ""){
         humane.log('请选择商品');
      }else{
        $.ajax({
        url: "/api/mall/items/updateStatus",
        type: 'PUT',
        cache:false,
        data: {status: status ,
               ids  :  ids},
        success: function() {
          humane.log('修改商品状态成功');
          $('.d-check input:checked').parents('tr').fadeOut();
        }
      }) 
     }
      
    })


    // sort logic
    //2_1_1_1,price,quantity,sold-quantity  up/down
    var sort = $.query.get('sort');
    if (sort !== undefined && sort.length >= 6) {

      sort = sort.split('_');
      var targets = $('.js-item-sort i');
      if (sort.length >= 3 && targets.length === 4) {
        $(targets[0]).removeClass().addClass('sorting-icon-' + sort[0]);
        $(targets[1]).removeClass().addClass('sorting-icon-' + sort[1]);
        $(targets[2]).removeClass().addClass('sorting-icon-' + sort[2]);
        $(targets[3]).removeClass().addClass('sorting-icon-' + sort[3]);
      }
    }
  }
}
