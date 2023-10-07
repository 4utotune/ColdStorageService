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
			
				val MaxWeightDDR = 50.0f
				val MaxWeightcoldroom = 200.0f
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
						CoapObserverSupport(myself, "localhost","11802","ctx_coldstorage","transporttrolley")
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
						 
									for (ticket in ticketManager.tickets.values) {
										if (!ticket.isValid && !ticket.isExpired && !ticket.isApproved) {
											ReservedWeight -= ticket.weight
											ticket.hasExpired()
											println("Riduco peso")
										}
									}	
						CommUtils.outgreen("$name | Idle. Current: $CurrentWeight, Reserved: $ReservedWeight")
						updateResourceRep( "'weight(cur,$CurrentWeight,res,$ReservedWeight,max,$MaxWeightcoldroom)'"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="handle_store",cond=whenRequest("storerequest"))
					transition(edgeName="t01",targetState="handle_ticket",cond=whenRequest("insertticket"))
					transition(edgeName="t02",targetState="handle_update",cond=whenDispatch("coapUpdate"))
					transition(edgeName="t03",targetState="handle_charge_taken",cond=whenReply("chargetaken"))
				}	 
				state("handle_store") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("storerequest(FW)"), Term.createTerm("storerequest(FW)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outgreen("$name | Received store request for ${payloadArg(0)} kg")
								 val FW = payloadArg(0).toFloat()  
								if(  (FW > MaxWeightDDR)  
								 ){ RejectedRequests++  
								updateResourceRep( "'rejected($RejectedRequests)'"  
								)
								answer("storerequest", "storerejected", "storerejected(tooheavy)"   )  
								}
								else
								 {if(  ((CurrentWeight + ReservedWeight + FW) > MaxWeightcoldroom)  
								  ){ RejectedRequests++  
								 updateResourceRep( "'rejected($RejectedRequests)'"  
								 )
								 answer("storerequest", "storerejected", "storerejected(full)"   )  
								 }
								 else
								  {
								  						val TICKET = ticketManager.newTicket(FW)
								  					  	ReservedWeight += FW
								  					  	val TicketId = TICKET.id
								  CommUtils.outgreen("$name | Store request accepted. Ticket: [ $TicketId ]")
								  answer("storerequest", "storeaccepted", "storeaccepted($TicketId)"   )  
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
				state("handle_ticket") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("insertticket(TICKET)"), Term.createTerm("insertticket(TICKET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												val Received = payloadArg(0).toString()
												val TICKET = ticketManager.getTicket(Received)
								if(  (TICKET != null)  
								 ){if(  (TICKET.isValid && !TICKET.isExpired)  
								 ){if( (!TICKET.isApproved()) 
								 ){ TICKET.approve()  
								if(  (ticketManager.isWaiting)  
								 ){CommUtils.outgreen("$name | Rejected ticket [ $Received ] - service full. Waiting for [ ${ticketManager.waiting} ] to be handled")
								answer("insertticket", "ticketrejected", "ticketrejected(full)"   )  
								}
								else
								 {if(  (ticketManager.isWorking)  
								  ){CommUtils.outgreen("$name | Approved ticket [ $Received ]. Currenty working on [ ${ticketManager.working} ]")
								 answer("insertticket", "ticketaccepted", "ticketaccepted(wait)"   )  
								 }
								 else
								  {CommUtils.outgreen("$name | Approved ticket [ $Received ]. Requesting charge")
								  answer("insertticket", "ticketaccepted", "ticketaccepted(ok)"   )  
								  request("gotoindoor", "gotoindoor(_)" ,"transporttrolley" )  
								  }
								   ticketManager.setWaiting(Received)  
								 }
								}
								else
								 {CommUtils.outgreen("$name | Rejected ticket [ $Received ] - already inserted")
								 answer("insertticket", "ticketrejected", "ticketrejected(duplicate)"   )  
								 }
								}
								else
								 {
								 						ticketManager.remove(TICKET)
								 CommUtils.outgreen("$name | Rejected ticket [ $Received ] - timedout")
								 answer("insertticket", "ticketrejected", "ticketrejected(timedout)"   )  
								 }
								}
								else
								 {CommUtils.outgreen("$name | Rejected ticket [ $Received ] - doesn't exist")
								 answer("insertticket", "ticketrejected", "ticketrejected(invalid)"   )  
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
						if( checkMsgContent( Term.createTerm("chargetaken(_)"), Term.createTerm("chargetaken(_)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 			
												val TicketId = ticketManager.waitingNowWorking()
								CommUtils.outgreen("$name | Charge taken for ticket [ $TicketId ]")
								updateResourceRep( "'chargetaken($TicketId)'"  
								)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_update") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("coapUpdate(RESOURCE,VALUE)"), Term.createTerm("coapUpdate(transporttrolley,deposited)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												if (ticketManager.isWorking) {  
								CommUtils.outgreen("$name | Deposit confirmation received")
								
													CurrentWeight += ticketManager.workingTicket().weight
													ReservedWeight -= ticketManager.workingTicket().weight
													ticketManager.stopWorking()	// also removes ticket	
												}
								if(  (ticketManager.isWaiting)  
								 ){CommUtils.outgreen("$name | Next ticket: [ ${ticketManager.waiting} ]")
								request("gotoindoor", "gotoindoor(_)" ,"transporttrolley" )  
								}
								else
								 {CommUtils.outgreen("$name | No tickets are waiting. Sending trolley home")
								 forward("gohome", "gohome(_)" ,"transporttrolley" ) 
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
