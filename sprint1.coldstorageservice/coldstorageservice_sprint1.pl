%====================================================================================
% coldstorageservice_sprint1 description   
%====================================================================================
context(ctx_coldstorage, "localhost",  "TCP", "11802").
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( test_suite, ctx_coldstorage, "it.unibo.test_suite.Test_suite").
  qactor( accessguimock, ctx_coldstorage, "it.unibo.accessguimock.Accessguimock").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
