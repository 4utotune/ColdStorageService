/* Generated by AN DISI Unibo */ 
package it.unibo.testserviceaccessgui

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Testserviceaccessgui ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var currentWeight: Float = 0.0f	
				var Timestamp: String = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outcyan("$name | init")
						 currentWeight = 0.0f  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="sendstore", cond=doswitch() )
				}	 
				state("sendstore") { //this:State
					action { //it:State
						CommUtils.outcyan("$name | invio store request")
						request("storerequest", "storerequest(30)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="handle_store_accepted",cond=whenReply("storeaccepted"))
					transition(edgeName="t01",targetState="handle_store_rejected",cond=whenReply("storerejected"))
				}	 
				state("handle_store_accepted") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("storeaccepted(TICKET)"), Term.createTerm("storeaccepted(TICKET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Timestamp = payloadArg(0)  
								CommUtils.outcyan("$name | Store Accepted. Received ticket $Timestamp")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="sendticket", cond=doswitch() )
				}	 
				state("handle_store_rejected") { //this:State
					action { //it:State
						CommUtils.outcyan("$name | Store Rejected")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("sendticket") { //this:State
					action { //it:State
						CommUtils.outcyan("$name | Sending ticket ")
						request("insertticket", "insertticket($Timestamp)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t02",targetState="handle_ticket_accepted",cond=whenReply("ticketaccepted"))
					transition(edgeName="t03",targetState="handle_ticket_rejected",cond=whenReply("ticketrejected"))
				}	 
				state("handle_ticket_accepted") { //this:State
					action { //it:State
						CommUtils.outcyan("$name | Ticket Accepted")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t04",targetState="handle_charge_taken",cond=whenDispatch("chargetaken"))
				}	 
				state("handle_ticket_rejected") { //this:State
					action { //it:State
						CommUtils.outcyan("$name | Ticket Rejected")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t05",targetState="handle_charge_taken",cond=whenDispatch("chargetaken"))
				}	 
				state("handle_charge_taken") { //this:State
					action { //it:State
						CommUtils.outblack("$name | charge taken! Il Fridge Truck si allontana")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="sendstore", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						CommUtils.outblack("$name | Richiesta di store rifiutata la Cold Room è piena")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
