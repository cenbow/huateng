styleUtil = require("design/style")
designEnv = require("design/environment")
compManage = require("sys/toolbar/comp/view")

key('up, down, left, right, shift+up, shift+down, shift+left, shift+right', 'control', function(event, handler){
  var dom = document.getElementById(designEnv.beforeSelectedId);
  if ($(dom) == undefined || $(dom).hasClass('page_part')){
    return false;
  };
  var increment = 8
  if (key.shift) {
    increment = 1
  }
  if (handler.shortcut.indexOf("up") != -1) {
    dom.style.top = dom.offsetTop - increment + "px";
    styleUtil.save(designEnv.beforeSelectedId,'top',dom.style.top);
  } else if(handler.shortcut.indexOf("down") != -1) {
    dom.style.top = dom.offsetTop + increment + "px";
    styleUtil.save(designEnv.beforeSelectedId,'top',dom.style.top);
  } else if(handler.shortcut.indexOf("left") != -1) {
    dom.style.left = dom.offsetLeft - increment + "px";
    styleUtil.save(designEnv.beforeSelectedId,'left',dom.style.left);
  } else {
    dom.style.left = dom.offsetLeft + increment + "px";
    styleUtil.save(designEnv.beforeSelectedId,'left',dom.style.left);
  }
  return false;
});

key('⌘+c, ctrl+c', 'control', function(event, handler){
  if(compManage.is_not_page_part()){
    designEnv.beforeCopiedId = designEnv.beforeSelectedId;
  }
  return false;
})

key('⌘+v, ctrl+v', 'control', function(event, handler){
  if(compManage.is_not_page_part()){
    compManage.clone_widget(designEnv.beforeCopiedId);
  }
  return false;
})

key('⌘+s, ctrl+s', 'control', function(event, handler){
  $('#js-page-structure-save').trigger('click');
  return false;
})

key('⇧+-, ⇧+=', 'control', function(event, handler){
  if(compManage.is_not_page_part()){
    if (handler.shortcut == "⇧+-"){
      compManage.change_z_index(designEnv.beforeSelectedId, -1);
    }else {
      compManage.change_z_index(designEnv.beforeSelectedId, 1);
    }
  }
  return false;
})

key('⌘+o, ctrl+o', 'control', function(event, handler){
  $('.comp-menu').trigger('click');
  return false;
})

key('backspace, del, delete', 'control', function(event, handler){
  if(compManage.is_not_page_part()){
    compManage.del_widget(designEnv.beforeSelectedId);
  }
  return false;
})

key('esc, escape', 'control', function(event, handler){
  $('.cancel, .close').trigger('click');
  return false;
})

key.setScope('control')
