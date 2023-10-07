%====================================================================================
% coldstorageservice_sprint1 description   
%====================================================================================
request( engage, engage(OWNER,STEPTIME) ).
dispatch( disengage, disengage(ARG) ).
dispatch( cmd, cmd(MOVE) ).
dispatch( end, end(ARG) ).
request( step, step(TIME) ).
request( doplan, doplan(PATH,OWNER,STEPTIME) ).
request( moverobot, moverobot(TARGETX,TARGETY) ).
dispatch( setrobotstate, setpos(X,Y,D) ).
dispatch( setdirection, dir(D) ).
request( getrobotstate, getrobotstate(ARG) ).
dispatch( chargetaken, chargetaken(_) ).
request( chargedeposited, chargedeposited(_) ).
dispatch( gotoindoor, gotoindoor(_) ).
request( storerequest, storerequest(FW) ).
request( insertticket, insertticket(TICKET) ).
dispatch( mock_store_request, mock_store_request(FW) ).
dispatch( mock_ticket_input, mock_ticket_input(TICKET) ).
dispatch( test_gotticket, test_gotticket(TICKET) ).
dispatch( next_test, next_test(_) ).
%====================================================================================
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_coldstorage, "localhost",  "TCP", "11802").
 qactor( basicrobot, ctx_basicrobot, "external").
  qactor( test_suite, ctx_coldstorage, "it.unibo.test_suite.Test_suite").
  qactor( accessguimock, ctx_coldstorage, "it.unibo.accessguimock.Accessguimock").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
