%====================================================================================
% coldstorageservice_sprint4 description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_coldstorage, "localhost",  "TCP", "11802").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( engager, ctxbasicrobot, "external").
  qactor( planexec, ctxbasicrobot, "external").
  qactor( robotpos, ctxbasicrobot, "external").
  qactor( sonar, ctx_coldstorage, "rx.SonarMQTTReceiver").
  qactor( datacleaner, ctx_coldstorage, "rx.DataCleaner").
  qactor( distancefilter, ctx_coldstorage, "rx.DistanceFilter").
  qactor( ledmqttsender, ctx_coldstorage, "rx.ledMQTTSender").
  qactor( statusgui_proxy, ctx_coldstorage, "it.unibo.statusgui_proxy.Statusgui_proxy").
  qactor( accessgui_proxy, ctx_coldstorage, "it.unibo.accessgui_proxy.Accessgui_proxy").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( alarmdevice, ctx_coldstorage, "it.unibo.alarmdevice.Alarmdevice").
  qactor( warningdevice, ctx_coldstorage, "it.unibo.warningdevice.Warningdevice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
