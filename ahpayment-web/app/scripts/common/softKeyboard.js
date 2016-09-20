/** 软键盘 */
var keyboardId = "Layer_key";
var passwordId = "billSubmitVoBillPassword";
var formId = "form1";
var width = "216px";
var height = "106px";
var passLength = "";
var num = 2;

keyBoardIdSet = function(keyboardId1) {
    keyboardId = keyboardId1;
}

passwordIdSet = function(passwordId1) {
    passwordId = passwordId1;
}

formIdSet = function(formId1) {
    formId = formId;
}

afterCloseFunc = function() {
        if (formId != null && formId != "") {
            if ($.formValidator != null)
                $.formValidator.elemIsValidNoCallBack(formId, passwordId);
        }
    }
    /** 显示软键盘 */
showKeyboard = function(str) {

    if (keyboardId != null && keyboardId != "") {

        obj = $("#" + keyboardId);
        passwordObj = $("#" + passwordId);
        if (obj != null) {
            var top = passwordObj.offset().top - 5;
            var left = passwordObj.offset().left + passwordObj.width() + 10;
            style = "position:absolute;visibility:visible;";
            style += "width:" + width + "px;";
            style += "height:" + height + "px;";
            style += "top:" + top + "px;";
            style += "left:" + left + "px;";
            obj.attr("style", style);
        }
    }
}

softKeyboardCloseCallBack = function() {

}

/** 关闭软键盘 */
closeKeyboard = function() {
    if (keyboardId != null && keyboardId != "") {
        obj = $("#" + keyboardId);
        if (obj != null) {
            obj.attr("style", "position:absolute;visibility: hidden;top: 0px;left: 0px;");
        }
        $("body").focus();

        $("#" + passwordId).blur();

    }

    softKeyboardCloseCallBack();

    afterCloseFunc();
}

closeKeyboard_uncheck = function() {
        if (keyboardId != null && keyboardId != "") {
            obj = $("#" + keyboardId);
            if (obj != null) {
                obj.attr("style", "position:absolute;visibility: hidden;top: 0px;left: 0px;");
            }
            $("body").focus();
        }
    }
    /** 点击软件盘 */
clickKeyboard = function(str) {
    passwordObj = $("#" + passwordId);
    if (str == "bak") {
        var password = passwordObj.val();
        if (password.length > 0) {
            passwordObj.val(password.substring(0, password.length - 1));
        }
    } else if (str == "clear") {
        passwordObj.val("");
    } else {

        if (passLength == null || passLength == "") {

            if (passwordObj.val().length < 6) {
                passwordObj.val(passwordObj.val() + str);
            }

        } else {

            if (passwordObj.val().length < passLength) {
                passwordObj.val(passwordObj.val() + str);
            }
        }
    }
}

module.exports = {
  keyBoardIdSet: keyBoardIdSet,
  passwordIdSet: passwordIdSet,
  formIdSet: formIdSet,
  afterCloseFunc: afterCloseFunc,
  showKeyboard: showKeyboard,
  closeKeyboard_uncheck:closeKeyboard_uncheck,
  softKeyboardCloseCallBack: softKeyboardCloseCallBack,
  closeKeyboard: closeKeyboard,
  clickKeyboard:clickKeyboard
}