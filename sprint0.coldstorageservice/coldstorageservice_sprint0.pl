%====================================================================================
% coldstorageservice_sprint0 description   
%====================================================================================
context(ctx_coldstorage, "localhost",  "TCP", "8020").
context(ctx_accessgui, "localhost",  "TCP", "8021").
context(ctx_basicrobot, "localhost",  "TCP", "8022").
context(ctx_statusgui, "localhost",  "TCP", "8023").
context(ctx_raspberry, "localhost",  "TCP", "8024").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( serviceaccessgui, ctx_accessgui, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
  qactor( sonar, ctx_raspberry, "it.unibo.sonar.Sonar").
  qactor( led, ctx_raspberry, "it.unibo.led.Led").
  qactor( servicestatusgui, ctx_statusgui, "it.unibo.servicestatusgui.Servicestatusgui").
