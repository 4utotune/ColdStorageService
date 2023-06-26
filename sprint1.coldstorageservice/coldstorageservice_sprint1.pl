%====================================================================================
% coldstorageservice_sprint1 description   
%====================================================================================
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_coldstorage, "localhost",  "TCP", "11800").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( testserviceaccessgui, ctx_coldstorage, "it.unibo.testserviceaccessgui.Testserviceaccessgui").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
