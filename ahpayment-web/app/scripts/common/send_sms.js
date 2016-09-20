var sendCodeId;
var url;
var mobilePhone;
var sendType;
var type;
var imgFlagId;
var noticeType;
var checkType;


var secs = 59;
var wait = secs * 1000;
getParam = function(sendC, urlVal, mobileP, sendT, typeVal, imgFlagIdVal, notice, checkType) {
  sendCodeId = sendC;
  url = urlVal;
  mobilePhone = mobileP;
  sendType = sendT;
  type = typeVal;
  imgFlagId = imgFlagIdVal;
  noticeType = notice;
  checkType = checkType;

}

sendClickFun = function() {

  if (checkType = "checkType") {
    if ($("#checkCode").val() == "") {
      $("#checkCodMsg").html("请输入验证码");
      return;
    }
  }

  if ($("#" + imgFlagId).val() != "true") {
    return;
  }
  
  $("#" + sendCodeId).unbind('click');
  $("#" + sendCodeId).css('cursor', 'default');

  $.ajax({
    url: url,
    type: 'GET',
    dataType: 'json',
    cache: false,
    data: {
      uuord: '',
      mobile: mobilePhone,
      sendType: sendType,
      type: type
    }
  })
 
    .done(function(data) { 
      if (data.data == '1') {
        if (noticeType == "notice") {
          $("#uitext").html("验证码发送到你的联系手机，请注意查收。");
          $("#shortMsg").css('display', 'block');
        } else {
          require("common/extras/popup").alert("", "success", "验证码发送到你的联系手机，请注意查收。", 5);
        }

      } else if (data.data.status == 'ok') {
        if (noticeType == "notice") {
          $("#uitext").html("验证码发送到你的联系手机，请注意查收。" + data.data.msg);
          $("#shortMsg").css('display', 'block');
        } else {
          require("common/extras/popup").alert("", "success", "验证码发送到你的联系手机，请注意查收。" + data.data.msg, 5);
        }

      } else {
        if (noticeType == "notice") {
          alert(data.data.status);
          $("#uitext").html("获取验证码失败，请点击重新获取。");
          $("#shortMsg").css('display', 'block');
        } else {
          require("common/extras/popup").alert("", "success", "获取验证码失败，请点击重新获取。", 5);
        }
      }
    }).fail(function() {
      if (noticeType == "notice") {
        $("#shortMsg").css('display', 'block');
        $("#uitext").html("获取验证码失败，请点击重新获取。");
      } else {
        require("common/extras/popup").alert("", "success", "获取验证码失败，请点击重新获取。", 5);
      }
    });
  $("#" + sendCodeId).removeClass('ui-button ui-button-sermwhite');
  $("#" + sendCodeId).html("59秒后可重发送");
  for (var i = 1; i <= secs; i++) {
    setTimeout(_update(i), i * 1000);
  }
  setTimeout(_timer(), wait);

}

_timer = function() {
  return function() {
    timer();
  }
}

timer = function() {
  $("#" + sendCodeId).addClass('ui-button ui-button-sermwhite');
  $("#" + sendCodeId).css('cursor', 'pointer');
  $("#" + sendCodeId).html("重新发送");
  $("#" + sendCodeId).bind('click', sendClickFun);
  if (noticeType == "notice") {
    $("#shortMsg").css('display', 'none');
  }
}

_update = function(num) {
  return function() {
    update(num);
  }
}
update = function(num) {
  if (num == (wait / 1000)) {
    $("#" + sendCodeId).html("重新发送");
  } else {
    printnr = (wait / 1000) - num;
    $("#" + sendCodeId).html(printnr + "秒后可重发送");
  }
}

//获取时间
get_time = function() {
  return new Date().getTime();
}
var checkCodeId;
var checkImgId;
var imageCodeFlagId;
var imageCodeId;
genImgCodeFun = function() {
  var imageId = imageCodeId;
  $("#" + imageId).attr("src", "/api/captcha/get?" + get_time());
  $("#" + imageId).css({
    height: 30,
    width: 60
  });
}
gemCheCodParFun = function(checkCo, checkImg, imgCodFlId, imgCdIdVal) {
  checkCodeId = checkCo;
  checkImgId = checkImg;
  imageCodeFlagId = imgCodFlId;
  imageCodeId = imgCdIdVal;
}
cleanImgCodeFun = function() {
  $("#" + checkImgId).html("");
  $("#" + checkCodeId).val("");
  genImgCodeFun();
  $("#" + imageCodeFlagId).val("false");

}

checkCodeFun = function() {
  var checkCode = $("#" + checkCodeId).val();
  $("#" + checkImgId).html("");
  if (checkCode.length == '4') {
    $.ajax({
      url: '/api/captcha/match',
      type: 'GET',
      dataType: 'json',
      data: {
        actualToken: checkCode
      }
    })
      .done(function(data) {
        if (data.data) {
          $("#" + checkImgId).html("<img src='/assets/sutongpay/se.png' />");
          $("#" + imageCodeFlagId).val("true");
        } else {
          $("#" + checkImgId).html("<img src='/assets/sutongpay/er.png' />");
        }
      })
      .fail(function() {});

  }
}

genImgCodeFor3GAnd18 = function(checkControlsId, checkImgId, vryCodeId, flagId) {
  $("#" + checkImgId).html("");
  $("#" + vryCodeId).val("");
  $("#" + flagId).val("false");
  $("#" + checkControlsId + " img").attr("src", "/api/captcha/get?" + get_time());
  $("#" + checkControlsId + " img").css({
    height: 30,
    width: 60
  });
}


module.exports = {
  checkCodeFun: checkCodeFun,
  genImgCodeFun: genImgCodeFun,
  getParam: getParam,
  sendClickFun: sendClickFun,
  gemCheCodParFun: gemCheCodParFun,
  cleanImgCodeFun: cleanImgCodeFun,
  genImgCodeFor3GAnd18: genImgCodeFor3GAnd18
}