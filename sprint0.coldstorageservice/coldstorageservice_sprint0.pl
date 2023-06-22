%====================================================================================
% coldstorageservice_sprint0 description   
%====================================================================================
context(ctx_basicrobot, "localhost",  "TCP", "8020").
context(ctx_coldstorage, "localhost",  "TCP", "11800").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( serviceaccessgui, ctx_coldstorage, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
  qactor( sonar, ctx_coldstorage, "it.unibo.sonar.Sonar").
  qactor( led, ctx_coldstorage, "it.unibo.led.Led").
  qactor( servicestatusgui, ctx_coldstorage, "it.unibo.servicestatusgui.Servicestatusgui").
