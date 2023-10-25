%====================================================================================
% coldstorageservice_sprint4 description   
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
dispatch( coapUpdate, coapUpdate(RESOURCE,VALUE) ).
dispatch( chargetaken, chargetaken(_) ).
request( chargedeposited, chargedeposited(_) ).
dispatch( gotoindoor, gotoindoor(_) ).
request( proxy_storerequest, proxy_storerequest(FW) ).
request( proxy_insertticket, proxy_insertticket(TICKET) ).
dispatch( gui_storerequest, gui_storerequest(FW,ID) ).
dispatch( gui_insertticket, gui_insertticket(TICKET,ID) ).
dispatch( coapUpdate, coapUpdate(SOURCE,ARG) ).
event( sonardata, distance(D) ).
event( obstacle, obstacle(D) ).
event( obstaclefree, obstaclefree(D) ).
event( alarm, alarm(X) ).
event( resume, resume(X) ).
event( ledoff, ledoff(_) ).
event( ledon, ledon(_) ).
event( ledblink, ledblink(_) ).
%====================================================================================
context(ctx_basicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_coldstorage, "localhost",  "TCP", "11802").
 qactor( basicrobot, ctx_basicrobot, "external").
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
