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
						forward("updateled", "updateled(_)" ,"led" ) 
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
						CommUtils.outblack("[transporttrolley] Idle...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t010",targetState="handle_load_request",cond=whenDispatch("gotoindoor"))
				}	 
				state("handle_load_request") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("gotoindoor(_)"), Term.createTerm("gotoindoor(_)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("chargetaken", "chargetaken(_)" ,"coldstorageservice" ) 
								request("chargedeposited", "chargedeposited(_)" ,"coldstorageservice" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t111",targetState="handle_load_request",cond=whenReply("more"))
					transition(edgeName="t112",targetState="handle_gohome",cond=whenReply("gohome"))
				}	 
				state("handle_gohome") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("gohome(_)"), Term.createTerm("gohome(_)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
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
