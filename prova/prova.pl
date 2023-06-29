%====================================================================================
% prova description   
%====================================================================================
context(ctx_prova, "127.0.0.1",  "TCP", "8020").
context(ctx_signora, "localhost",  "TCP", "11800").
 qactor( signore, ctx_signora, "it.unibo.signore.Signore").
  qactor( signora, ctx_prova, "it.unibo.signora.Signora").
