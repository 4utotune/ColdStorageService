%====================================================================================
% coldstorageservice_sprint4 description   
%====================================================================================
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagerefused, engagerefused(ARG) ).  %%for engage
dispatch( disengage, disengage(ARG) ).
dispatch( cmd, cmd(MOVE) ).
dispatch( end, end(ARG) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
request( doplan, doplan(PATH,OWNER,STEPTIME) ).
reply( doplandone, doplandone(ARG) ).  %%for doplan
reply( doplanfailed, doplanfailed(ARG) ).  %%for doplan
request( moverobot, moverobot(TARGETX,TARGETY) ).
reply( moverobotdone, moverobotok(ARG) ).  %%for moverobot
reply( moverobotfailed, moverobotfailed(PLANDONE,PLANTODO) ).  %%for moverobot
dispatch( setrobotstate, setpos(X,Y,D) ).
dispatch( setdirection, dir(D) ).
request( getrobotstate, getrobotstate(ARG) ).
reply( robotstate, robotstate(POS,DIR) ).  %%for getrobotstate
dispatch( coapUpdate, coapUpdate(RESOURCE,VALUE) ).
request( gotoindoor, gotoindoor(_) ).
reply( chargetaken, chargetaken(_) ).  %%for gotoindoor
dispatch( gohome, gohome(_) ).
request( storerequest, storerequest(FW) ).
reply( storeaccepted, storeaccepted(TICKET) ).  %%for storerequest
reply( storerejected, storerejected(REASON) ).  %%for storerequest
request( insertticket, insertticket(TICKET) ).
reply( ticketaccepted, chargetaken(INFO) ).  %%for insertticket
reply( ticketrejected, ticketrejected(REASON) ).  %%for insertticket
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
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctx_coldstorage, "localhost",  "TCP", "11802").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( sonar, ctx_coldstorage, "rx.SonarMQTTReceiver").
  qactor( distancefilter, ctx_coldstorage, "rx.DistanceFilter").
  qactor( ledmqttsender, ctx_coldstorage, "rx.ledMQTTSender").
  qactor( coldstorageservice, ctx_coldstorage, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( alarmdevice, ctx_coldstorage, "it.unibo.alarmdevice.Alarmdevice").
  qactor( warningdevice, ctx_coldstorage, "it.unibo.warningdevice.Warningdevice").
  qactor( transporttrolley, ctx_coldstorage, "it.unibo.transporttrolley.Transporttrolley").
