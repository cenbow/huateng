module.exports = {
  init: function() {

    
 require("common/extras/checkLogin").checkLogin();





    //点查询时把月份转成日期段
    $('#search').click(function(){  
      if ($("#months").val()==""){

         initDate();
      }else{
      monthtodate();

      }

      $("#searchform").submit();

    })
    /*
    $('#months').datepicker({  
         changeMonth : true,  
         changeYear : true,  
         dateFormat : 'yy-mm',  
                 showButtonPanel: true,  
                 onClose: function(dateText, inst) {     
            month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();  
            year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
             

                        
         }    
       
        }); 

    //“Done”按钮的click事件  
    $(".ui-datepicker-close").live("click", function ()  
     {  
         $('#months').datepicker("setDate", new Date(year, month, 1));   
         parsemonth();
     });  */
           
        
  	 //菜单进入时，墨认选本月
   initDate();

   




     function monthtodate(){
  var months=$('#months').val();
  var arr=months.split("-");
  var newdate=new Date(arr[0], arr[1], 0); 
  $("#startDate").val(months+"-01");
  $("#endDate").val(formatDate(newdate));

    

   }
//格式化日期
function formatDate(date){
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  var formatedDate = "";
  formatedDate += year + "-";    
  if(month >= 10 ){
    formatedDate += month + "-";
  }
  else{
    formatedDate += "0" + month + "-";
  }
  if(day >= 10 ){
    formatedDate += day ;
  }
  else{
    formatedDate += "0" + day ;
  }
  return formatedDate;
}

 function initDate(){
 	if ($('#startDate').val()=="" && $('#endDate').val()==""){
 		var myDate = new Date();
  var year = myDate.getFullYear();
  var month = myDate.getMonth()+1;
    if (month<10){
        month = "0"+month;
    }
  var firstDay = year+"-"+month+"-"+"01";
  var today = new Date();
  var today_f = formatDate(today);
  $("#startDate").val(firstDay);
  $("#endDate").val(today_f);
   $("#crrentmonth").addClass("ui-sertime-sel");
  $("#months").val(year+"-"+month);

 	}else{
   //查询7天或本月时要清空月份值
   if($("#last7day").attr("class")=="ui-ser-time ui-sertime-sel portal-button-last7"
    || $("#crrentmonth").attr("class")=="ui-ser-time  ui-sertime-sel portal-button-curmonth"){

    $("#months").val("");
    $("#startDate").val("");
    $("#endDate").val("");

   }

  }




    }

   

  }
};
