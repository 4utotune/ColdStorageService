%====================================================================================
% coldstorageservice_sprint2 description   
%====================================================================================
context(ctx_coldstorage, "localhost",  "TCP", "11802").
context(ctx_prova, "localhost",  "TCP", "8085").
 qactor( prova, ctx_prova, "external").
  qactor( accessgui_proxy, ctx_coldstorage, "it.unibo.accessgui_proxy.Accessgui_proxy").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
