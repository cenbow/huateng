var jq=jQuery.noConflict()
window.onTrackerLoaded =  function() {
  new Tracker().watch();
};

var clickNav=0
var liks=['about','start','carnival']
for(var aa=0;aa<liks.length;aa++){
  if(contains(location.href,liks[aa])){
    clickNav=aa
  }
}

jq(document).ready(function(e) {

for(var i=0;i<jq('#J-Nav li').length;i++){
    jq('.tt'+i).bind('click',function (e){
      pid=jq(this).attr('class').replace('tt','')

    })
    jq('.tt'+i).bind('mouseover',function (e){
      id=jq(this).attr('class').replace('tt','')
      TweenMax.killTweensOf(jq('.tt'+clickNav))
      TweenMax.killTweensOf(jq('#Nav'+clickNav))
      for(var ii=0;ii<jq('#J-Nav li').length;ii++){
        if(ii!==Number(id)){
          if(jq.browser.mozilla||jq.browser.msie && (jq.browser.version == "6.0" || jq.browser.version == "7.0" || jq.browser.version == "8.0")){
            TweenMax.to(jq('.tt'+ii),.18,{css:{width:52}})
            jq('.tt'+ii).css({'background-position':'0px '+(-63)*ii+'px'},500)
          }else{
            TweenMax.to(jq('.tt'+ii),.18,{css:{width:52,'background-position':'0px '+(-63)*ii+'px'}})
          }
          TweenMax.to(jq('#Nav'+ii),.2,{css:{right:0,width:52}})
          jq('#Nav'+ii).css({'z-index':0})
          jq('#Nav'+ii).removeClass('main-nav_yy')
        }else{

          if(jq.browser.mozilla||jq.browser.msie && (jq.browser.version == "6.0" || jq.browser.version == "7.0" || jq.browser.version == "8.0")){
            TweenMax.to(jq('.tt'+ii),.2,{css:{width:100}})
            jq('.tt'+ii).css({'background-position':'-50px '+(-63)*ii+'px'},500)
          }else{
            TweenMax.to(jq('.tt'+ii),.2,{css:{width:100,'background-position':'-50px '+(-63)*ii+'px'}})
          }
          if(jq.browser.msie && jq.browser.version == "6.0"){
            TweenMax.to(jq('#Nav'+ii),.12,{css:{right:-20,width:80}})
          }else{
            TweenMax.to(jq('#Nav'+ii),.12,{css:{right:0,width:80}})
          }

          jq('#Nav'+ii).css({'z-index':100})
          jq('#Nav'+ii).addClass('main-nav_yy')
        }
      }
    })
    jq('.tt'+i).bind('mouseout',function (e){
      overid=jq(this).attr('class').replace('tt','')
      NavAciton()
    })
  }
  NavAciton()
})
var NavAciton=function (){
  for(var ii=0;ii<liks.length;ii++){
    if(ii!==clickNav){
      jq('#Nav'+ii).css({'z-index':0})
      jq('#Nav'+clickNav).css({'z-index':100})
      if(jq.browser.mozilla||jq.browser.msie && (jq.browser.version == "6.0" || jq.browser.version == "7.0" || jq.browser.version == "8.0")){
        jq('.tt'+ii).css({'background-position':'0px '+(-63)*ii+'px'})
        jq('.tt'+clickNav).css({'background-position':'-50px '+(-63)*clickNav+'px'})
        TweenMax.to(jq('.tt'+ii),.18,{css:{width:52}})
        TweenMax.to(jq('.tt'+clickNav),.2,{delay:.5,css:{width:100}})
      }else{
        TweenMax.to(jq('.tt'+ii),.18,{css:{width:52,'background-position':'0px '+(-63)*ii+'px'}})
        TweenMax.to(jq('.tt'+clickNav),.2,{delay:.5,css:{width:100,'background-position':'-50px '+(-63)*clickNav+'px'}})
      }

      TweenMax.to(jq('#Nav'+ii),.2,{css:{right:0,width:52}})
      if(jq.browser.msie && jq.browser.version == "6.0"){
        TweenMax.to(jq('#Nav'+clickNav),.12,{delay:.5,css:{right:-20,width:80}})
      }else{
        TweenMax.to(jq('#Nav'+clickNav),.12,{delay:.5,css:{right:0,width:80}})
      }

      jq('#Nav'+ii).removeClass('main-nav_yy')
      jq('#Nav'+clickNav).addClass('main-nav_yy')
    }
  }

}
function contains(string,substr)
{
  var startChar=substr.substring(0,1);
  var strLen=substr.length;
    for(var j=0;j<string.length-strLen+1;j++)
    {
       if(string.charAt(j)==startChar)
       {
          if(string.substring(j,j+strLen)==substr)
          {
              return true;
           }
       }
    }
   return false;
}
