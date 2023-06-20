%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxcoldstorage, "localhost",  "TCP", "8021").
 qactor( basicrobot, ctxcoldstorage, "external").
  qactor( transporttrolley, ctxcoldstorage, "it.unibo.transporttrolley.Transporttrolley").
  qactor( servicestatusgui, ctxcoldstorage, "it.unibo.servicestatusgui.Servicestatusgui").
  qactor( serviceaccessgui, ctxcoldstorage, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( appl, ctxcoldstorage, "it.unibo.appl.Appl").
  qactor( sonar, ctxcoldstorage, "it.unibo.sonar.Sonar").
  qactor( led, ctxcoldstorage, "it.unibo.led.Led").
