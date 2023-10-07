%====================================================================================
% coldstorageservice_sprint2 description   
%====================================================================================
dispatch( coapUpdate, coapUpdate(RESOURCE,VALUE) ).
dispatch( chargetaken, chargetaken(_) ).
request( chargedeposited, chargedeposited(_) ).
dispatch( gotoindoor, gotoindoor(_) ).
request( storerequest, storerequest(FW) ).
request( insertticket, insertticket(TICKET) ).
%====================================================================================
context(ctx_coldstorage, "localhost",  "TCP", "11802").
 qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( transporttrolley_mock, ctx_coldstorage, "it.unibo.transporttrolley_mock.Transporttrolley_mock").
