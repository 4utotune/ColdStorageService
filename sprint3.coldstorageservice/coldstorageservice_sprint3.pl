%====================================================================================
% coldstorageservice_sprint3 description   
%====================================================================================
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_coldstorage, "localhost",  "TCP", "11802").
 qactor( sonar, ctx_coldstorage, "sonarSimulator").
  qactor( datacleaner, ctx_coldstorage, "rx.DataCleaner").
  qactor( distancefilter, ctx_coldstorage, "rx.DistanceFilter").
  qactor( basicrobot, ctx_basicrobot, "external").
  qactor( test_suite, ctx_coldstorage, "it.unibo.test_suite.Test_suite").
  qactor( accessguimock, ctx_coldstorage, "it.unibo.accessguimock.Accessguimock").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( alarmdevice, ctx_coldstorage, "it.unibo.alarmdevice.Alarmdevice").
  qactor( warningdevice, ctx_coldstorage, "it.unibo.warningdevice.Warningdevice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
