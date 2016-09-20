module.exports = {
  init: function() {
      var htmlText = $("#detailText").val();
       $(".spxq_tex").html(htmlText);

  	  var view = $("#skuDetail").val();
	    var objects = $.parseJSON(view);
	    var option = "";
	    for (var one in objects){  
	        var optionfirst = "";
	        var optionlast = "";
	        for(var key in objects[one])  
	         {    
	             if(key=="value"){
	                 optionfirst = "<div class='item'> <b></b><a data-id="+objects[one][key]+">";
	             }else{
	                 optionlast = objects[one][key]+"</a></div>";
	             }            

	         }
	        option = option + optionfirst + optionlast;   
	     }
       $(".smdd1").append(option);  
       $('.smdd1 .item a').on("click", function(evt){
  	      evt.preventDefault();
  	      // handle selected item
  	      var $item = $(this).parent();
  	      $item.siblings().removeClass("selected")
  	      $item.addClass("selected");

  	      // handle selected text
  	      var text = _.map($('.payment-reChargeCard-detail .item.selected'), function(i){
  	                       return $("a", i).text()
  	                }).join(", ");
  	      if(!isEmptySku()){
  	        $('.jg_r').text((chooseSku()[0].price / 100*parseInt($("#buy-num").val())).formatMoney(2, ".", ","))
  	      }
	      });
  	   
  	   $("#buy-num").blur(function(){
  	   	 var number = $("#buy-num").val();
         if(!number.match(/^\d+(\.\d+)?$/)){
         	$("#buy-num").val(1);
         }
  	   });
       // handle amount
      var $chooseAmount = $('#choose-amount')

      $chooseAmount.find('.btn-add').on('click', function(){
          var $buyNumber = $("#buy-num")
          if(isNaN($buyNumber.val()) || "" == $buyNumber.val() || $buyNumber.val() <= 0){
            $buyNumber.val('1');
          }else{
            handleAmount("add");
          }  
       })

      $chooseAmount.find('.btn-reduce').on('click', function(){
          var $buyNumber = $("#buy-num")
          if(isNaN($buyNumber.val()) || "" == $buyNumber.val() || $buyNumber.val() <= 0){
            $buyNumber.val('1');
          }else{
            handleAmount("reduce");
          }  
       })
    
      $chooseAmount.find('#buy-num').on('blur', function(){
          var $buyNumber = $("#buy-num")
          if(isNaN($buyNumber.val()) || "" == $buyNumber.val() || $buyNumber.val() <= 0){
            $buyNumber.val('1');
          }
      })
      
      $("#js-fast-buy").on("click", function(evt){
        require("common/extras/checkLogin").checkLogin();
      if(isNaN($("#buy-num").val()) || "" == $("#buy-num").val() || $("#buy-num").val() <= 0){
        require("common/extras/popup").alert("", "error", "商品购买数量非法.", 5);
        return;
      }
      evt.preventDefault();
      if(isEmptySku()){
        require("common/extras/popup").alert("", "error", "请选择唯一的一种商品.", 5);
        return;
      }
      $("input[name='skuId']").val(chooseSku()[0].id);
      $("input[name='count']").val(parseInt($("#buy-num").val()));
      $("#reChargeForm").submit();
    })
      $("#js-fast-addcart").on("click", function(evt){
        require("common/extras/checkLogin").checkLogin();
        if(isNaN($("#buy-num").val()) || "" == $("#buy-num").val() || $("#buy-num").val() <= 0){
          require("common/extras/popup").alert("", "error", "商品购买数量非法.", 5);
          return;
        }
        evt.preventDefault();
        if(isEmptySku()){
          require("common/extras/popup").alert("", "error", "请选择唯一的一种商品.", 5);
          return;
        }
        $.ajax({
        type:"POST",
        url:"/api/cart/addCart",
        data: {skuId: chooseSku()[0].id, quantity: parseInt($("#buy-num").val())},
        cache:false,
        async:false
        }).done(function(data){
          if(data.data == "000000"){
            require("common/extras/popup").alert("", "success", "购物车添加成功", 5);
          }else{
            require("common/extras/popup").alert("", "error", "购物车添加失败", 5);
          }
        })
    })

  	  var isEmptySku = function(){
          return $('#choose').find('li.sku-choose').length !== $('.item.selected').length;
      }

      var chooseSku = function(){
          var skus = $('#choose').data('skus')
          var ids = _.map($('.payment-reChargeCard-detail .item.selected a'), function(data){
            return $(data).data("id")
          })
          if(ids.length == 1){
            return _.select(skus, function(sku){
              return sku.attributeValue1 == ids[0]
            })
          }else {
            return _.select(skus, function(sku){
              var values = [sku.attributeValue1, sku.attributeValue2]
              return _.contains(values, ids[0] + "") && _.contains(values, ids[1] + "")
            })
          }
      }
      var handleAmount = function(type){
          var $buyNumber = $("#buy-num")
          var number = parseInt($buyNumber.val())
          if(type == "add"){
            $buyNumber.val( number + 1)
            
          }else {
            if(number == 1){
              $buyNumber.val(1)
            }else {
              $buyNumber.val( number - 1)
            }
          }
          if(!isEmptySku()){
                $('.jg_r').text((chooseSku()[0].price / 100*parseInt($buyNumber.val())).formatMoney(2, ".", ","))
            }
       }
      // initialize sku
      _.each($('.sku-choose'), function(sku){
        $(sku).find('a:first').trigger('click')
      })
  	   
  }
}