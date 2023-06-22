/* Generated by AN DISI Unibo */ 
package it.unibo.servicestatusgui

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Servicestatusgui ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
				var TrolleyState: String = "IDLE"
				var TrolleyPosition: String = "HOME"
				var CurrentWeight: Float = 0.0f
				var RejectedRequests: Int = 0
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						CommUtils.outred("[ServiceStatusGui] Init")
						 
									TrolleyState = "IDLE"
									TrolleyPosition = "HOME"
									CurrentWeight = 0.0f
									RejectedRequests = 0
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						CommUtils.outred("[ServiceStatusGui] Idle...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t019",targetState="handle_trolley_update",cond=whenDispatch("updatetrolleystatus"))
					transition(edgeName="t020",targetState="handle_coldstorage_update",cond=whenDispatch("updatestoragestatus"))
				}	 
				state("handle_trolley_update") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("updatetrolleystatus(STATE,POS)"), Term.createTerm("updatetrolleystatus(STATE,POS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												TrolleyState = payloadArg(0)
												TrolleyPosition = payloadArg(1)
						}
						CommUtils.outred("[ServiceStatusGui] Updated trolley -> State: $TrolleyState, Pos: $TrolleyPosition")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_coldstorage_update") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("updatestoragestatus(CURWEIGHT,REJREQUESTS)"), Term.createTerm("updatestoragestatus(CURWEIGHT,REJREQUESTS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												CurrentWeight = payloadArg(0).toFloat()
												RejectedRequests = payloadArg(1).toInt()
						}
						CommUtils.outred("[ServiceStatusGui] Updated coldroom -> CurWeight: $CurrentWeight, RejRequests: $RejectedRequests")
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
