%====================================================================================
% coldstorageservice_sprint3 description   
%====================================================================================
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_coldstorage, "localhost",  "TCP", "11802").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( sonar, ctx_coldstorage, "rx.SonarMQTTReceiver").
  qactor( datacleaner, ctx_coldstorage, "rx.DataCleaner").
  qactor( distancefilter, ctx_coldstorage, "rx.DistanceFilter").
  qactor( ledmqttsender, ctx_coldstorage, "rx.ledMQTTSender").
  qactor( accessgui_proxy, ctx_coldstorage, "it.unibo.accessgui_proxy.Accessgui_proxy").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( alarmdevice, ctx_coldstorage, "it.unibo.alarmdevice.Alarmdevice").
  qactor( warningdevice, ctx_coldstorage, "it.unibo.warningdevice.Warningdevice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
