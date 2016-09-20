/**
 * add auto hide when mouse moveout
 * 
 * @version 1.0.1 
 * @date 2010-11-23
 * @author coraldane@gmail.com
*/

/**   
 * Date Picker
 * @param   inputObj  The input object want to contain date.
 * @param   dateFormatStyle  Default Date Formatter is "yyyy-MM-dd", you could use your own defined format.
 * @param   beginDate Default value is 1990-01-01
 * @param   endDate   Default value is 2020-01-01
 * @param   lang      0(English)|1(Chinese)  Default Language is 0(English).
 */
function setday(inputObj,dateFormatStyle,beginDate,endDate,lang){
	if(null == inputObj){return null;}
	new Calendar(inputObj,dateFormatStyle,beginDate,endDate,lang).show();
}

/**   
 * Month Picker
 * @param   inputObj  The input object want to contain date.
 * @param   dateFormatStyle  Default Date Formatter is "yyyy-MM", you could use your own defined format.
 * @param   beginDate Default value is 1990-01
 * @param   endDate   Default value is 2020-01
 * @param   lang      0(English)|1(Chinese)  Default Language is 0(English).
 */
function setmonth(inputObj,dateFormatStyle,beginDate,endDate,lang){

	 $("#calendarIframe").height("100%");   
	 $("#calendarIframe").width("100%");
	if(null == inputObj){return null;}
	
	new Calendar(inputObj,dateFormatStyle,beginDate,endDate,lang,"m").show();
}

/**
Calendar Style
*/
/**
Calendar.prototype.style = function(){
	var strStyle = "<style type='text/css'>";
	strStyle += ".calendar {font-size:12px; margin:0;padding:0px;border:1px solid #397EAE;background-color:#DBE7F2;}";
	strStyle += ".calendar ul {list-style-type:none; margin:0; padding:0;vertical-align:middle;}";
	strStyle += ".calendar li {float:left;}.calendar b{font-weight:bold;}";
	strStyle += ".calendar .day li {height:20px;}";
	strStyle += ".calendar .day li,.calendar .date li{float:left;width:14.13%;height:20px;line-height:20px;text-align:center;}";
	strStyle += ".calendar .day li{font-weight:bold;} .calendar .date li{background-color:#EDF3F9;}";
	strStyle += ".calendar .month li{float:left;width:24.8%;height:20px;line-height:20px;text-align:center;background-color:#EDF3F9;}";
	strStyle += ".calendar li a{ text-decoration:none; font-family:Tahoma; font-size:11px; color:#333}";
	strStyle += ".calendar li:hover {cursor:pointer;color:#f30; text-decoration:none;background-color:#EDF3F9;}";
	strStyle += ".calendar .date li:hover, .calendar .month li:hover{cursor:pointer;color:#f30; text-decoration:none;background-color:#DBE7F2;}";
	strStyle += ".calendarlihover {color:#f30;text-decoration:none;background-color:#E8F2FE;}";
	strStyle += ".calendar li a.hasArticle {font-weight:bold; color:#f60 !important}";
	strStyle += ".lastMonthDate, .nextMonthDate {color:#bbb;font-size:11px}";
	strStyle += ".selectThisYear, .selectThisMonth{text-decoration:none; margin:0px; color:#000; font-weight:bold}";
	strStyle += ".calendar .LastMonth, .calendar .NextMonth{text-decoration:none; color:#000; font-size:18px; font-weight:bold; line-height:16px;}";
	strStyle += ".calendarTitle{background:#EDF3F9;text-align:center;height:20px;line-height:20px;clear:both;width:100%;}";
	strStyle += ".calendarTitle .mark{text-decoration:none;color:#000;font-family:Tahoma;font-size:18px;font-weight:normal;}";
	strStyle += ".today{ background-color:#ffffaa;border:1px solid #f60;padding:0 1px;}";
	strStyle += ".today a { color:#f30; }";
	strStyle += ".calendarBottom {text-align:center;height:20px;line-height:20px;clear:both;width:100%;border-top:1px solid #ddd;}";
	strStyle += ".calendarBottom li{float:left;height:20px;line-height:20px;font-weight:bold;text-align:center;}";
	strStyle += "</style>";
	return strStyle;
}
*/
//**
//Classic Style
Calendar.prototype.style = function(){
var strStyle = "<style type='text/css'>";
strStyle += ".calendar {width:180px;display:block; border:1px solid #C8C8C8; position:absolute; background-color:#f0f6f6; font-size:14px; color:#515151;}";
strStyle += ".calendar ul {list-style-type: none;margin: 0;padding: 0;vertical-align: middle;}";
strStyle += "#PrevYear{width:34px; height:14px; font-size:14px; line-height:14px; display:block; float:left; position:absolute; background-image: url(/assets/sutongpay/inside-main/topbaraggim01.png);left:10px; top:5px; cursor:pointer; background-repeat:no-repeat;background-position:center; }";
strStyle += "#NextYear{width:34px; height:14px; font-size:14px; line-height14px; display:block; float:right; position:absolute; right:10px; top:5px; cursor:pointer;background-position:center;background-image: url(/assets/sutongpay/inside-main/topbaraggim02.png);background-repeat:no-repeat;	 }";
strStyle += ".ui-date-note{ width:170px; height:18px; display:block; margin:0 auto; background-color:#2589cf; text-align:center; line-height:18px; margin-left:5px;}";
strStyle += ".ui-date-note span{ color:#FFF; font-size:12px;}";
strStyle += ".selectThisYear{  font-weight:bold;  float:left; width:100px; text-align:right; }";
strStyle += ".calendar li {float: left;}";
strStyle += ".calendar b {font-weight: bold;}";
strStyle += ".calendar .day li {height: 20px;}";
strStyle += ".calendar .day li, .calendar .date li {}";
strStyle += ".calendar .day li {font-weight: bold;}";
strStyle += ".calendar .date li {}";
strStyle += ".calendar li a {text-decoration: none;color: #666;font-family: Tahoma;font-size: 11px;}";
strStyle += ".calendar li:hover { cursor: default;color: #2a9cd8;text-decoration: none;}";
strStyle += ".calendar .date li:hover, .calendar .month li:hover {cursor: pointer;color: #2a9cd8;text-decoration: none;background-color: #f0f6f6;}";
strStyle += ".calendarlihover {color: #f30;text-decoration: none;background-color: #E8F2FE;}";
strStyle += ".calendar li a.hasArticle {font-weight: bold;color: #f60 !important}";
strStyle += ".lastMonthDate, .nextMonthDate {color: #bbb;font-size: 11px}";
strStyle += ".disabled{display: block;font-family: 宋体;float: left;border: 1px #909090 solid;background: #ddd;color: #ccc;";



strStyle += "line-height:22px; width:38px; height:24px; margin: 0 3px 5px 0;text-align: center;font-size:12px; position:relative;}";
strStyle += ".selectThisMonth {margin: 0px;border: 1px solid #006efe;color: #006efe;font-weight: bold;}";
strStyle += ".calendar .LastMonth, .calendar .NextMonth {text-decoration: none;color: #000;font-size: 18px;font-weight: bold;line-height: 16px;}";
strStyle += ".calendarTitle {text-align: center;height: 20px;line-height: 20px;clear: both;width: 100%;}";
strStyle += ".thisMonth .today {color: #2589cf; font-weight:bold;}";
strStyle += ".calendarBottom {text-align: center;height: 20px;line-height: 20px;clear: both;width: 100%;border-top: 1px solid #ddd;}";
strStyle += ".calendarBottom li {float: left;height: 20px;line-height: 20px;font-weight: bold;text-align: center;}";
strStyle += ".calendarBody{ margin-left:5px; margin-top:5px;}";
strStyle += ".thisMonth{display: block;font-family: 宋体;background: #fff;float: left;border: 1px #909090 solid;";
strStyle += "line-height:22px; width:38px; height:24px; margin: 0 3px 5px 0;text-align: center;cursor: pointer; font-size:12px; position:relative;}";
strStyle += "#emptyCalendar{ float:left; display:inline-block; margin-left:12px;}";
strStyle += "#selectCurrent{ float:left; display:inline-block; margin-left:30px;}";
strStyle += "#closeCalendar{ float:left; display:inline-block; margin-left:30px;}";
strStyle += "</style>";
	return strStyle;
	
}
//*/

function getFrameDocument(frame){
	if ( frame.contentDocument ) { // DOM
        var doc = frame.contentDocument;
    } else if (frame.contentWindow) { // IE win
        var doc = frame.contentWindow.document;
    }
    return doc;
}

/**   
 * Parse Date value from String   
 * @param format the pattern of date   
 */   
String.prototype.toDate = function(format){
	if(null == format) format="yyyy-MM-dd";
	var pattern = format.replace("yyyy", "(\\~1{4})").replace("yy", "(\\~1{2})")
		.replace("MM", "(\\~1{2})").replace("M", "(\\~1{1,2})")
		.replace("dd", "(\\~1{2})").replace("d", "(\\~1{1,2})").replace(/~1/g, "d");
	
	var returnDate;
	if (new RegExp(pattern).test(this)) {
	    var yPos = format.indexOf("yyyy");
	    var mPos = format.indexOf("MM");
	    var dPos = format.indexOf("dd");
	    if (mPos == -1) mPos = format.indexOf("M");
	    if (yPos == -1) yPos = format.indexOf("yy");
	    if (dPos == -1) dPos = format.indexOf("d");
	    var pos = new Array(yPos + "y", mPos + "m", dPos + "d");
	    var data = { y: 0, m: 0, d: 1};
	    var m = this.match(pattern);
	    for (var i = 1; i < m.length; i++) {
	        if (i == 0) return;
	        var flag = pos[i - 1].split('')[1];
	        data[flag] = m[i];
	        //alert(pos[i-1] + ",flag:"+flag + ",i:" + i + "," + data[flag]);
	    };
		
	    if (data.y.toString().length == 2) {
	        data.y = parseInt("20" + data.y);
	    }
	    data.m = data.m - 1;
	    returnDate = new Date(data.y, data.m, data.d);
	}
	if (returnDate == null || isNaN(returnDate)) returnDate = new Date();
	return returnDate;
 
};

/**   
 * Date Format 
 * @param style date format like 'yyyyMMdd'
 */   
Date.prototype.format = function(style) {
  var o = {   
    "M+" : this.getMonth() + 1, //month   
    "d+" : this.getDate(),      //day   
    "h+" : this.getHours(),     //hour   
    "m+" : this.getMinutes(),   //minute   
    "s+" : this.getSeconds(),   //second   
    "w+" : "日一二三四五六".charAt(this.getDay()),   //week   
    "q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter   
    "S"  : this.getMilliseconds() //millisecond   
  }   
  if(/(y+)/.test(style)) {   
	style = style.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));   
  }
  for(var k in o){
    if(new RegExp("("+ k +")").test(style)){   
      style = style.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));   
    }
  } 
  return style;
};

/**
Date add by interval
@param interval y  Year,m  Month,d  Day,w  Week
@param number

*/
Date.prototype.dateAdd = function(interval, number) {
	switch (interval) {
	  case "y":
		return new Date(this.getFullYear() + number, this.getMonth(), this.getDate());
		break;
	  case "m":
		return new Date(this.getFullYear(), this.getMonth() + number, checkDate(this.getFullYear(), this.getMonth() + number, this.getDate()));
		break;
	  case "d":
		return new Date(this.getFullYear(), this.getMonth(), this.getDate() + number);
		break;
	  case "w":
		return new Date(this.getFullYear(), this.getMonth(), 7 * number + this.getDate());
		break;
	}
}

function checkDate(year, month, date){
	var enddate = ["31", "28", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31"];
	var returnDate = "";
	if (year % 4 == 0) {
		enddate[1] = "29";
	}
	if (date > enddate[month]) {
		returnDate = enddate[month];
	} else {
		returnDate = date;
	}
	return returnDate;
}

/**
Calendar language pack
default support english and chinese,if you want to add some other language, please extend it.
*/
Calendar.language = {
	"title":[["",""],["年","月"]],
	"months":[["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"],
			["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
        	],
	"weeks":[["S","M","T","W","T","F","S"],
  			["日","一","二","三","四","五","六"]
			],
	weekday:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
	"clear":[["Clear"], ["清空"]],
	"today":[["Today","Current"], ["今天","当月"]],
	"close":[["Close"], ["关闭"]]  
};

/**   
 * Calendar class 
 * @param   beginDate 1990-01-01
 * @param   endDate   2020-01-01
 * @param   lang      0(English)|1(Chinese)  
 * @param   dateFormatStyle  "yyyy-MM-dd";
 * @param   type  d Date Picker/m Month Picker 
 * @version 2010-08-20
 * @author  Coral(coraldane@gmail.com) 
 * @update 
 */   
function Calendar(inputObj, dateFormatStyle, beginDate, endDate, lang, type) {   
  this.beginDate = "2005-01-01".toDate();
  this.endDate = "2030-12-01".toDate();
  this.lang = 1; //default language
  this.type = "d";
  this.dateFormatStyle = "yyyy-MM-dd";
  
  if(null != type){
  	this.type = type;
  	if("m" == this.type){
  		this.dateFormatStyle = "yyyy-MM";
  	}
  }
  
  if (dateFormatStyle != null){
    this.dateFormatStyle = dateFormatStyle;
  }
  
  this.currentDate = new Date();
  
  var currDate = new Date();
  if(null != inputObj.value && "" != inputObj.value){
  	currDate = inputObj.value.toDate(this.dateFormatStyle);
  }
  
  if(null != currDate){
  	this.date = currDate;
  }else{
  	this.date = new Date();
  }
  
  
  if (null != beginDate){
    this.beginDate = beginDate;
  }
  if(null != endDate){
  	this.endDate = endDate;
  }
  if (lang != null){
    this.lang = lang;
  }
  
  this.dateControl = inputObj;
  this.panel = document.getElementById("calendarPanel");
  this.iframe = document.getElementById("calendarIframe");
  this.isFocus = false;
  
  this.draw();
}

Calendar.prototype.draw = function() {   
  var currDate = this.date.format("yyyy-MM").toDate("yyyy-MM");
  if(currDate < this.beginDate){
  	this.date = this.beginDate;
  }
  
  if(currDate > this.endDate){
  	this.date = this.endDate;
  }
  
  this.year = this.date.getFullYear();
  this.month = this.date.getMonth();
  var head  = '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">' +
	'<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />' +
  this.style() + '</head><body style="padding:0px;margin:0px;">';   
  var thisMonthFirstDate = this.date.dateAdd("d",1-this.date.getDate());
  var lastMonthEndDate = thisMonthFirstDate.dateAdd("d",-1);
  var lastMonthDate =  thisMonthFirstDate.getDay();
  lastMonthEndDate = lastMonthEndDate.getDate();
  var thisMonthLastDate = thisMonthFirstDate.dateAdd("m",1).dateAdd("d",-1);
  var thisMonthEndDate = thisMonthLastDate.getDate();
  var thisMonthEndDay = thisMonthLastDate.getDay();
  
  var lis = "<div id='calendar' class='calendar' style='width:";
  if("d" == this.type){
  	lis += "180";
  }else{
  	lis += "180";
  }
  lis += "px;'>";
  lis += "<div class='calendarTitle'><ul>";
  lis += "<li id='PrevYear' class='mark'  title='上一年'></li>";
  if("d" == this.type){
  	lis += "<li id='PrevMonth' class='mark'  title='上一月'></li>";
    lis += "<li ><span id='selectThisYear' class='selectThisYear'>" + this.date.getFullYear() + "</span>" + Calendar.language["title"][this.lang][0] + "</li>";
  	if(0 == this.lang){
  		lis += "<li ><span id='selectThisMonth' class='selectThisMonth'>" + Calendar.language["months"][this.lang][this.date.getMonth()] + "</span></li>";
  	}else{
  		lis += "<li ><span id='selectThisMonth' class='selectThisMonth'>" + (this.date.getMonth() +1) + "</span>" + Calendar.language["title"][this.lang][1] + "</li>";
  	}
  	lis += "<li id='NextMonth' class='mark'  title='下一月'></li>";
  	lis += "<li id='NextYear' class='mark'  title='下一年'></li></ul></div>";
	//lis += "<div class='ui-date-note'><span>选择月份，只能单选</span></div>";
	lis += "<div class='calendarBody'>";
  	lis += "<ul class='day'>";
  	for(var i=0;i<Calendar.language.weeks[this.lang].length;i++){
  		lis += "<li title='" + Calendar.language.weekday[i] + "'>" + Calendar.language.weeks[this.lang][i] + "</li>";
  	}
  	lis += "</ul><ul class='date' id='thisMonthDate'>";
  	var lastMonthLis = "";
  	for (var i = 0; i < lastMonthDate; i++) { // Last Month's Date
  		//alert(lastMonthDate + "," + lastMonthEndDate);
		lastMonthLis = "<li class='lastMonthDate'>" + lastMonthEndDate + "</li>" + lastMonthLis;
		lastMonthEndDate--;
	}
	lis += lastMonthLis;
	for (i = 1; i <= thisMonthEndDate; i++) { // Current Month's Date
		var currentDate = thisMonthFirstDate.dateAdd("d",(i-1));
		if(currentDate < this.beginDate || currentDate > this.endDate){
			lis += "<li class='lastMonthDate'>" + i + "</li>";
  			continue;
		}
		lis += "<li class='thisMonth' title='" + currentDate.format("yyyy-MM-dd") + "'><a href='javascript:void(0);' ";
		if(currentDate.format("yyyy-MM-dd") == (this.date).format("yyyy-MM-dd")){
			lis += "class='today' ";
		}
		lis += ">" + i + "</a></li>";
	}
	var j = 1;
	for (i = thisMonthEndDay; i < 6; i++) { // Next Month's Date
		lis += "<li class='nextMonthDate'>" + j + "</li>";
		j++;
	}
  	lis += "</ul>"
  	
  	lis += "</div>";//close calendarBody
	lis += "<div class='calendarBottom'><ul>";
	lis += "<li id='emptyCalendar'  title='Clear'>" + Calendar.language.clear[this.lang] +"</li>";
	lis += "<li id='selectCurrent'  title='Today'>" + Calendar.language.today[this.lang][0] +"</li>";
  }else{
  	lis += "<li ><span id='selectThisYear' class='selectThisYear'>" + this.date.getFullYear() + "</span>" + Calendar.language["title"][this.lang][0] + "</li>";
  	lis += "<li id='NextYear' class='mark'  title='下一年'></li></ul></div>";
	lis += "<div class='ui-date-note'><span>选择月份，只能单选</span></div>";
  	lis += "<div class='calendarBody'>";
  	lis += "</ul><ul class='month' id='thisMonth'>";
	//月份
  	for(var i=1; i<=12; i++){
  		var currentDate = (this.year + "-" + (i>9?i:"0"+i)).toDate("yyyy-MM");
  		if(currentDate < this.beginDate || currentDate > this.endDate){
  			lis += "<li class='lastMonthDate'>" + i + "</li>";
  			continue;
  		}
		//if((this.year+"-"+(i>9?i:"0"+i)) >= (this.date).format("yyyy-MM")){
  			//alert(11111);
  		//}
		var myDates = new Date();
//myDate.getYear(); //获取当前年份(2位)
//myDate.getFullYear(); //获取完整的年份(4位,1970-????)
//myDate.getMonth(); //获取当前月份(0-11,0代表1月)
   var dateses=(this.date).format("yyyy")
   var yesms=myDates.getFullYear();
   var datess=myDates.getMonth()+1; 
   
   if(dateses<yesms || dateses==yesms){
   
   // alert(datese);
     if( dateses==yesms){
    if(datess>=i){
	//alert(dates);
	 lis += "<li class='thisMonth' ";
	
	}
	}else{
	 lis += "<li class='thisMonth' ";
	}
	   lis += "<li class='disabled' ";
	}else{
	   lis += "<li class='disabled' ";
	}
  		
		
		lis +="title='" + this.year + "-" + (i>9?i:"0"+i) + "-01'><a href='javascript:void(0);'";
  		if((this.year+"-"+(i>9?i:"0"+i)) == (this.date).format("yyyy-MM")){
		
  			lis += " class='today' ";
			
  		}
  		lis += ">" + i + "</a></li>";
  	}
  	lis += "</ul>"
  	
  	lis += "</div>";//close calendarBody
	lis += "<div class='calendarBottom'><ul>";
	lis += "<li id='emptyCalendar'  title='清空'>" + Calendar.language.clear[this.lang] +"</li>";
	lis += "<li id='selectCurrent'  title='当月'>" + Calendar.language.today[this.lang][1] +"</li>";
  }
  
  lis += "<li id='closeCalendar'  title='关闭'>" + Calendar.language.close[this.lang] +"</li>";
  lis += "</ul></div>";//close calendarBottom
  lis += "</div>";//close calendar
  lis += "</body></html>";
  var doc = getFrameDocument(this.iframe);
  doc.writeln(head);
  doc.writeln(lis);
  doc.close();
  this.document = doc;
  
  this.bingEvent();
}

/**
* Bind Click Event into Calendar
*/
Calendar.prototype.bingEvent = function(){
  var calendar = this;
  
  this.setAutoHeight();
    
  this.panel.onmouseover = function(){calendar.isFocus = true;}
  this.panel.onmouseout = function(){calendar.isFocus = false;}
  
  this.dateControl.onblur = function(){
  	if(!calendar.isFocus){
  		calendar.hide();
  	}
  }
  
  this.getElementById("selectCurrent").onclick = function(){
  	calendar.date = new Date();
  	calendar.valueSelected(calendar.date);
   
  	calendar.hide();
  }
  this.getElementById("emptyCalendar").onclick = function(){calendar.dateControl.value = "";calendar.hide();}
  this.getElementById("closeCalendar").onclick = function(){calendar.hide();}
  
  this.getElementById("PrevYear").onclick = function(){
  	calendar.date = calendar.date.dateAdd("y",-1);
  	calendar.draw();
  }
  
  if(this.getElementById("PrevMonth")){
	  this.getElementById("PrevMonth").onclick = function(){
	  	calendar.date = calendar.date.dateAdd("m",-1);
	  	calendar.draw();
	  }
	  this.getElementById("NextMonth").onclick = function(){
	  	calendar.date = calendar.date.dateAdd("m",1);
	  	calendar.draw();
	  }
  }
  
  this.getElementById("NextYear").onclick = function(){
  	calendar.date = calendar.date.dateAdd("y",1);
  	calendar.draw();
  }
  
  //this.getElementById("selectThisYear").onclick = function(){calendar.selectThisYear();}
  if("d" == this.type){
  	this.getElementById("selectThisMonth").onclick = function(){calendar.selectThisMonth();}
  }
  
  var elements = getElementsByClassName(this.document, "li", "thisMonth");
  for(var i=0; i<elements.length; i++){
    // alert((this.date).format("MM"));
	var myDate = new Date();
//myDate.getYear(); //获取当前年份(2位)
//myDate.getFullYear(); //获取完整的年份(4位,1970-????)
//myDate.getMonth(); //获取当前月份(0-11,0代表1月)
   var datese=(this.date).format("yyyy")
   var yesm=myDate.getFullYear();
   var dates=myDate.getMonth(); 
   
   if(datese<yesm || datese==yesm){
   
   // alert(datese);
     if( datese==yesm){
    if(dates>=i){
	//alert(dates);
	elements[i].onclick = function(){
	    
     
		calendar.date = this.title.toDate();
	  	calendar.valueSelected(calendar.date);
		
		  calendar.hide();
		
	  	}
		
	}
	}else{
	  elements[i].onclick = function(){
	    
     
		calendar.date = this.title.toDate();
	  	calendar.valueSelected(calendar.date);
		
		  calendar.hide();
		
	  	}
	}
	  
	}
  }
}

Calendar.prototype.selectThisYear = function(){
	var calendar = this;
	var curYear = this.date.getFullYear();
	var beginYear = this.beginDate.getFullYear();
	var endYear = this.endDate.getFullYear();
	var spanObj = this.getElementById("selectThisYear");
	var selectStr = "<select style='font-size:10px;'>";
	for(var i = endYear; i >= beginYear; i--){
		selectStr += "<option value='" + i + "'>" + i + "</option>";
	}
	selectStr += "</select>";
	spanObj.innerHTML = selectStr;
	var selectYearObj = spanObj.childNodes(0);
	selectYearObj.value = curYear;
	selectYearObj.onchange = function(){
		calendar.date.setFullYear(selectYearObj.value);
		calendar.draw();
	}
}

Calendar.prototype.selectThisMonth = function(){
	var calendar = this;
	var curMonth = this.date.getMonth() + 1;
	var curYear = this.date.getFullYear();
	var endYear = this.endDate.getFullYear();
	var endMonth = 12;
	if(curYear == endYear){
		endMonth = this.endDate.getMonth + 1;
	}
	var spanObj = this.getElementById("selectThisMonth");
	var selectStr = "<select style='font-size:10px;'>";
	for(var i = 1; i <= endMonth; i++){
		selectStr += "<option value='" + i + "'>" + Calendar.language["months"][this.lang][i-1] + "</option>";
	}
	selectStr += "</select>";
	spanObj.innerHTML = selectStr;
	var selectMonthObj = spanObj.childNodes(0);
	selectMonthObj.value = curMonth;
	selectMonthObj.onchange = function(){
		calendar.date.setMonth(selectMonthObj.value-1);
		calendar.draw();
	}
}

Calendar.prototype.valueSelected = function(date){
	this.dateControl.value = date.format(this.dateFormatStyle);
	//alert(date.format(this.dateFormatStyle)):
	
}

/**
* Set Auto Height for Calendar Panel Div
*/
Calendar.prototype.setAutoHeight = function(){
	var height = this.document.body.scrollHeight;
	var width = this.getElementById("calendar").style.width;
	width = (parseInt(width.substr(0,width.length-1)) + 2) + "px";
	this.iframe.style.height = '160px';
	this.panel.style.height = height;
	this.panel.style.width = width;
}

//Extend document.getElementById(id)
Calendar.prototype.getElementById = function(id){
  if (typeof(id) != "string" || id == "") return null;
  if(null == this.document) return null;
  if (this.document.getElementById) return this.document.getElementById(id);   
  if (this.document.all) return this.document.all(id);   
  try {return eval(id);} catch(e){ return null;}   
}
  
//Extend object.getElementsByTagName(tagName)   
Calendar.prototype.getElementsByTagName = function(tagName){
  if(null == this.document) return null;
  if (this.document.getElementsByTagName) return this.document.getElementsByTagName(tagName);   
  if (this.document.all) return this.document.all.tags(tagName);   
}   

/**
* Find a HTML Object by TagName and className
* @param oElm  parentNode Object
* @param strTagName tag name want to find
* @param strClassName class name
*/
function getElementsByClassName(oElm, strTagName, strClassName){  
    var arrElements = (strTagName == "*" && oElm.all)? oElm.all:oElm.getElementsByTagName(strTagName);  
    var arrReturnElements = new Array();  
    strClassName = strClassName.replace(/\-/g, "\\-");  
    var oRegExp = new RegExp("(^|\\s)" + strClassName + "(\\s|$)");  
    var oElement;  
    for(var i=0; i < arrElements.length; i++){  
        oElement = arrElements[i];  
        if(oRegExp.test(oElement.className)){  
            arrReturnElements.push(oElement);  
        }  
    }  
    return (arrReturnElements)  
} 


//find the absolute position
Calendar.prototype.getAbsPoint = function (e){   
  var x = e.offsetLeft;   
  var y = e.offsetTop;   
  while(e = e.offsetParent){   
    x += e.offsetLeft;   
    y += e.offsetTop;   
  }   
  return {"x": x, "y": y};   
}   
  
//显示日历   
Calendar.prototype.show = function () {
  var xy = this.getAbsPoint(this.dateControl);
  this.panel.style.left = xy.x + "px";
  this.panel.style.top = (xy.y + this.dateControl.offsetHeight) + "px";
  this.setDisplayStyle("select", "hidden");
  this.panel.style.visibility = "visible";
}

//Hide Calendar   
Calendar.prototype.hide = function() {
  this.setDisplayStyle("select", "visible");
  this.panel.style.visibility = "hidden";
  this.isFocus = false;
}
  
//Set Calendar Picker visible or invisible
Calendar.prototype.setDisplayStyle = function(tagName, style) {   
  var tags = this.getElementsByTagName(tagName)   
  for(var i = 0; i < tags.length; i++) {   
    if (tagName.toLowerCase() == "select" && 
       (tags[i].name == "calendarYear" ||   
      tags[i].name == "calendarMonth")){   
      continue;
    }
    tags[i].style.visibility = style;   
  }
}






