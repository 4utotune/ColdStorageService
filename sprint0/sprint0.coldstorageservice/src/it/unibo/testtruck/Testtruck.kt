/* Generated by AN DISI Unibo */ 
package it.unibo.testtruck

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Testtruck ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="loop", cond=doswitch() )
				}	 
				state("loop") { //this:State
					action { //it:State
						CommUtils.outyellow("[TestTruck] New cycle: Requesting store")
						emit("accessguicmd", "accessguicmd(store,30)" ) 
						delay(2000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_loop", 
				 	 					  scope, context!!, "local_tout_testtruck_loop", 6000.toLong() )
					}	 	 
					 transition(edgeName="t00",targetState="handle_timeout",cond=whenTimeout("local_tout_testtruck_loop"))   
					transition(edgeName="t01",targetState="handle_ticket",cond=whenDispatch("test_gotticket"))
				}	 
				state("handle_timeout") { //this:State
					action { //it:State
						CommUtils.outyellow("[TestTruck] Storage request got (probably) rejected")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="loop", cond=doswitch() )
				}	 
				state("handle_ticket") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("test_gotticket(TICKET)"), Term.createTerm("test_gotticket(TICKET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val Ticket = payloadArg(0)  
								CommUtils.outyellow("[TestTruck] Inputing ticket $Ticket")
								emit("accessguicmd", "accessguicmd(ticket,$Ticket)" ) 
								delay(2000) 
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="second_request_store", cond=doswitch() )
				}	 
				state("second_request_store") { //this:State
					action { //it:State
						CommUtils.outyellow("[TestTruck] Requesting second store")
						emit("accessguicmd", "accessguicmd(store,30)" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_second_request_store", 
				 	 					  scope, context!!, "local_tout_testtruck_second_request_store", 6000.toLong() )
					}	 	 
					 transition(edgeName="t12",targetState="handle_timeout",cond=whenTimeout("local_tout_testtruck_second_request_store"))   
					transition(edgeName="t13",targetState="handle_ticket_for_fail",cond=whenDispatch("test_gotticket"))
				}	 
				state("handle_ticket_for_fail") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("test_gotticket(TICKET)"), Term.createTerm("test_gotticket(TICKET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								delay(5000) 
								 val Ticket = payloadArg(0)  
								CommUtils.outyellow("[TestTruck] Inputing ticket $Ticket too late -> will fail")
								emit("accessguicmd", "accessguicmd(ticket,$Ticket)" ) 
								delay(2000) 
								CommUtils.outyellow("[TestTruck] Inputing non-existent ticket -> will fail")
								emit("accessguicmd", "accessguicmd(ticket,failfail)" ) 
								delay(2000) 
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="loop", cond=doswitch() )
				}	 
			}
		}
}