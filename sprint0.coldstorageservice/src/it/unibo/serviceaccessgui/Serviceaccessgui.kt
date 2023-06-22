/* Generated by AN DISI Unibo */ 
package it.unibo.serviceaccessgui

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Serviceaccessgui ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var currentWeight: Float	
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						CommUtils.outyellow("[ServiceAccessGui] Init")
						 currentWeight = 0.0f  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="update", cond=doswitch() )
				}	 
				state("update") { //this:State
					action { //it:State
						request("coldroomdatarequest", "coldroomdatarequest(_)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="handle_update_data",cond=whenReply("coldroomdata"))
				}	 
				state("handle_update_data") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("coldroomdata(WEIGHT)"), Term.createTerm("coldroomdata(WEIGHT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												currentWeight = payloadArg(0).toFloat()	
								CommUtils.outyellow("[ServiceAccessGui] Updating weight -> $currentWeight ")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						CommUtils.outyellow("[ServiceAccessGui] Idle...")
						delay(1000) 
						CommUtils.outyellow("[ServiceAccessGui] Sending request")
						request("storerequest", "storerequest(30)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t11",targetState="handle_store_accepted",cond=whenReply("storeaccepted"))
					transition(edgeName="t12",targetState="handle_store_rejected",cond=whenReply("storerejected"))
					transition(edgeName="t13",targetState="handle_charge_taken",cond=whenDispatch("chargetaken"))
				}	 
				state("handle_store_accepted") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("storeaccepted(TICKET)"), Term.createTerm("storeaccepted(TICKET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outyellow("[ServiceAccessGui] Store Accepted")
								delay(500) 
								CommUtils.outyellow("[ServiceAccessGui] Sending ticket")
								 val TIMESTAMP = payloadArg(0)  
								request("insertticket", "insertticket($TIMESTAMP)" ,"coldstorageservice" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t24",targetState="handle_ticket_accepted",cond=whenReply("ticketaccepted"))
					transition(edgeName="t25",targetState="handle_ticket_rejected",cond=whenReply("ticketrejected"))
				}	 
				state("handle_store_rejected") { //this:State
					action { //it:State
						CommUtils.outyellow("[ServiceAccessGui] Store Rejected")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_ticket_accepted") { //this:State
					action { //it:State
						CommUtils.outyellow("[ServiceAccessGui] Ticket Accepted")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_ticket_rejected") { //this:State
					action { //it:State
						CommUtils.outyellow("[ServiceAccessGui] Ticket Rejected")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_charge_taken") { //this:State
					action { //it:State
						CommUtils.outyellow("[ServiceAccessGui] Charge Taken")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="update", cond=doswitch() )
				}	 
			}
		}
}