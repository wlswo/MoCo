let openTrigger=$(".menu-trigger"),openTriggerTop=openTrigger.find(".menu-trigger-bar.top"),openTriggerMiddle=openTrigger.find(".menu-trigger-bar.middle"),openTriggerBottom=openTrigger.find(".menu-trigger-bar.bottom"),closeTrigger=$(".close-trigger"),closeTriggerLeft=closeTrigger.find(".close-trigger-bar.left"),closeTriggerRight=closeTrigger.find(".close-trigger-bar.right"),logo=$(".logo"),menuContainer=$(".menu-container"),menu=$(".menu"),menuTop=$(".menu-bg.top"),menuMiddle=$(".menu-bg.middle"),menuBottom=$(".menu-bg.bottom"),tlOpen=new TimelineMax({paused:!0}),tlClose=new TimelineMax({paused:!0});tlOpen.add("preOpen").to(logo,.4,{scale:.8,opacity:0,ease:Power2.easeOut},"preOpen").to(openTriggerTop,.4,{x:"+80px",y:"-80px",delay:.1,ease:Power4.easeIn,onComplete:function(){closeTrigger.css("z-index","25")}},"preOpen").to(openTriggerMiddle,.4,{x:"+=80px",y:"-=80px",ease:Power4.easeIn,onComplete:function(){openTrigger.css("visibility","hidden")}},"preOpen").to(openTriggerBottom,.4,{x:"+=80px",y:"-=80px",delay:.2,ease:Power4.easeIn},"preOpen").add("open","-=0.4").to(menuTop,.8,{y:"13%",ease:Power4.easeInOut},"open").to(menuMiddle,.8,{scaleY:1,ease:Power4.easeInOut},"open").to(menuBottom,.8,{y:"-114%",ease:Power4.easeInOut},"open").fromTo(menu,.6,{y:30,opacity:0,visibility:"hidden"},{y:0,opacity:1,visibility:"visible",ease:Power4.easeOut},"-=0.2").add("preClose","-=0.8").to(closeTriggerLeft,.8,{x:"-=100px",y:"+=100px",ease:Power4.easeOut},"preClose").to(closeTriggerRight,.8,{x:"+=100px",y:"+=100px",delay:.2,ease:Power4.easeOut},"preClose"),tlClose.add("close").to(menuTop,.2,{backgroundColor:"#222222",ease:Power4.easeInOut,onComplete:function(){logo.css("z-index","26"),closeTrigger.css("z-index","5"),openTrigger.css("visibility","visible")}},"close").to(menuMiddle,.2,{backgroundColor:"#222222",ease:Power4.easeInOut},"close").to(menuBottom,.2,{backgroundColor:"#222222",ease:Power4.easeInOut},"close").to(menu,.6,{y:20,opacity:0,ease:Power4.easeOut,onComplete:function(){menu.css("visibility","hidden")}},"close").to(logo,.8,{scale:1,opacity:1,ease:Power4.easeInOut},"close","+=0.2").to(menuTop,.8,{y:"-113%",ease:Power4.easeInOut},"close","+=0.2").to(menuMiddle,.8,{scaleY:0,ease:Power4.easeInOut},"close","+=0.2").to(menuBottom,.8,{y:"23%",ease:Power4.easeInOut,onComplete:function(){menuTop.css("background-color","#80868b"),menuMiddle.css("background-color","#80868b"),menuBottom.css("background-color","#80868b")}},"close","+=0.2").to(closeTriggerLeft,.2,{x:"+=100px",y:"-=100px",ease:Power4.easeIn},"close").to(closeTriggerRight,.2,{x:"-=100px",y:"-=100px",delay:.1,ease:Power4.easeIn},"close").to(openTriggerTop,1,{x:"-=80px",y:"+=80px",delay:.2,ease:Power4.easeOut},"close").to(openTriggerMiddle,1,{x:"-=80px",y:"+=80px",ease:Power4.easeOut},"close").to(openTriggerBottom,1,{x:"-=80px",y:"+=80px",delay:.1,ease:Power4.easeOut},"close"),openTrigger.on("click",(function(){tlOpen.progress()<1?tlOpen.play():tlOpen.restart()})),closeTrigger.on("click",(function(){tlClose.progress()<1?tlClose.play():tlClose.restart()})),$(document).ready((function(){tlOpen.progress()<1?tlOpen.play():tlOpen.restart()}));