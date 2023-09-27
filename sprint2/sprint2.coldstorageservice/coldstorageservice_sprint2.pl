%====================================================================================
% coldstorageservice_sprint2 description   
%====================================================================================
context(ctx_coldstorage, "localhost",  "TCP", "11802").
 qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( transporttrolley_mock, ctx_coldstorage, "it.unibo.transporttrolley_mock.Transporttrolley_mock").
