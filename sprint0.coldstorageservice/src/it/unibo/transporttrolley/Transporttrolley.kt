/* Generated by AN DISI Unibo */ 
package it.unibo.transporttrolley

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Transporttrolley ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						CommUtils.outmagenta("[TransportTrolley] Init")
						forward("updatetrolleystatus", "updatetrolleystatus(_)" ,"servicestatusgui" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						CommUtils.outmagenta("[transporttrolley] Idle...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t014",targetState="handle_load_request",cond=whenDispatch("gotoindoor"))
				}	 
				state("handle_load_request") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("gotoindoor(_)"), Term.createTerm("gotoindoor(_)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outmagenta("[transporttrolley] Going indoor")
								forward("updateled", "updateled(_)" ,"led" ) 
								forward("cmd", "cmd(_)" ,"basicrobot" ) 
								request("step", "step(_)" ,"basicrobot" )  
								delay(500) 
								CommUtils.outmagenta("[transporttrolley] Charge taken")
								forward("chargetaken", "chargetaken(_)" ,"coldstorageservice" ) 
								delay(500) 
								CommUtils.outmagenta("[transporttrolley] Charge deposited")
								request("chargedeposited", "chargedeposited(_)" ,"coldstorageservice" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t115",targetState="handle_load_request",cond=whenReply("more"))
					transition(edgeName="t116",targetState="handle_gohome",cond=whenReply("gohome"))
				}	 
				state("handle_gohome") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("gohome(_)"), Term.createTerm("gohome(_)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outmagenta("[transporttrolley] Going home")
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
