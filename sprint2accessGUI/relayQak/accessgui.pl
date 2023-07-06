%====================================================================================
% accessgui description   
%====================================================================================
context(ctx_gui, "localhost",  "TCP", "8084").
context(ctx_coldstorage, "127.0.0.1",  "TCP", "11802").
 qactor( accessgui_proxy, ctx_coldstorage, "external").
  qactor( gui_relay, ctx_gui, "it.unibo.gui_relay.Gui_relay").
