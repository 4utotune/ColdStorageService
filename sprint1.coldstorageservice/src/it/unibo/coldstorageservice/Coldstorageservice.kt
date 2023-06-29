/* Generated by AN DISI Unibo */ 
package it.unibo.coldstorageservice

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Coldstorageservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
			
				val MaxWeightcoldroom = 100.0f
				val TicketTimeout = 20000
				val TicketFormat = "yyyyMMddHHmmss"; // yyyy.MM.dd.HH.mm.ss
				
				var ticketManager = coldstorageservice.TicketManager(TicketTimeout, TicketFormat);
				
				var CurrentWeight = 0.0f
				var ReservedWeight = 0.0f
				var RejectedRequests = 0 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						 
									ticketManager = coldstorageservice.TicketManager(TicketTimeout, TicketFormat);
									CurrentWeight = 0.0f 
									ReservedWeight = 0.0f
									RejectedRequests = 0
						CommUtils.outgreen("$name | init")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						CommUtils.outgreen("$name | Idle. Current: $CurrentWeight, Reserved: $ReservedWeight")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t017",targetState="handle_store",cond=whenRequest("storerequest"))
					transition(edgeName="t018",targetState="handle_ticket",cond=whenRequest("insertticket"))
					transition(edgeName="t019",targetState="handle_deposited",cond=whenRequest("chargedeposited"))
					transition(edgeName="t020",targetState="handle_charge_taken",cond=whenDispatch("chargetaken"))
				}	 
				state("handle_store") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("storerequest(FW)"), Term.createTerm("storerequest(FW)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outgreen("$name | Received store request for ${payloadArg(0)} kg")
								 val FW = payloadArg(0).toFloat()  
								if(  ((CurrentWeight + ReservedWeight + FW) > MaxWeightcoldroom)  
								 ){ RejectedRequests++  
								answer("storerequest", "storerejected", "storerejected(full)"   )  
								}
								else
								 {
								 					val TICKET = ticketManager.newTicket(FW)
								 				  	ReservedWeight += FW
								 				  	val Timestamp = TICKET.timestamp
								 answer("storerequest", "storeaccepted", "storeaccepted($Timestamp)"   )  
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_ticket") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("insertticket(TICKET)"), Term.createTerm("insertticket(TICKET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												val Received = payloadArg(0).toString()
												val TICKET = ticketManager.getTicket(Received)
								if(  (TICKET != null && TICKET.isValid)  
								 ){if(  (ticketManager.isWaiting)  
								 ){answer("insertticket", "ticketrejected", "ticketrejected(queuefull)"   )  
								}
								else
								 {if(  (!ticketManager.isWorking)  
								  ){CommUtils.outgreen("$name | Approved ticket [ $Received ]. Requesting charge")
								 forward("gotoindoor", "gotoindoor(_)" ,"transporttrolley" ) 
								 }
								 else
								  {CommUtils.outgreen("$name | Approved ticket [ $Received ]. Currenty working on [ ${ticketManager.working} ]. Please wait")
								  }
								   ticketManager.setWaiting(Received)  
								 answer("insertticket", "ticketaccepted", "ticketaccepted(_)"   )  
								 }
								}
								else
								 {if( (TICKET != null) 
								  ){
								 						ticketManager.remove(TICKET)
								 						ReservedWeight -= TICKET.weight
								 CommUtils.outgreen("$name | Rejected ticket [ $Received ] - timedout")
								 answer("insertticket", "ticketrejected", "ticketrejected(timedout)"   )  
								 }
								 else
								  {CommUtils.outgreen("$name | Rejected ticket [ $Received ] - doesn't exist")
								  answer("insertticket", "ticketrejected", "ticketrejected(invalid)"   )  
								  }
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_charge_taken") { //this:State
					action { //it:State
						 			
									val Timestamp = ticketManager.waitingNowWorking()
						CommUtils.outgreen("$name | Charge taken for ticket [ $Timestamp ]")
						forward("chargetaken", "chargetaken(_)" ,"accessguimock" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_deposited") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("chargedeposited(_)"), Term.createTerm("chargedeposited(_)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												if (ticketManager.isWorking) {
													CurrentWeight += ticketManager.workingTicket().weight
													ReservedWeight -= ticketManager.workingTicket().weight
												}
												ticketManager.stopWorking()
								CommUtils.outgreen("$name | Deposit confirmation received")
								if(  (ticketManager.isWaiting)  
								 ){CommUtils.outgreen("$name | Next ticket: [ ${ticketManager.waiting} ]")
								answer("chargedeposited", "more", "more(_)"   )  
								}
								else
								 {answer("chargedeposited", "gohome", "gohome(_)"   )  
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
			}
		}
}
