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
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | init e engage basicrobot")
						request("engage", "engage(transporttrolley,330)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t022",targetState="engaged",cond=whenReply("engagedone"))
					transition(edgeName="t023",targetState="quit",cond=whenReply("engagerefused"))
				}	 
				state("engaged") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | basicrobot engaged")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						discardMessages = true
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t024",targetState="moveToIndoor",cond=whenDispatch("gotoindoor"))
				}	 
				state("moveToIndoor") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | vado all'INDOOR")
						request("moverobot", "moverobot(0,4)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t025",targetState="loadTheCharge",cond=whenReply("moverobotdone"))
				}	 
				state("loadTheCharge") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | sto caricando il carico presso l'INDOOR")
						delay(1000) 
						forward("chargetaken", "chargetaken(_)" ,"coldstorageservice" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="movetocoldroom", cond=doswitch() )
				}	 
				state("movetocoldroom") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | vado verso la cold room")
						request("moverobot", "moverobot(4,3)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t026",targetState="storethecharge",cond=whenReply("moverobotdone"))
				}	 
				state("storethecharge") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | scarico il carico presso la Cold Room")
						delay(1000) 
						request("chargedeposited", "chargedeposited(_)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t027",targetState="moveToIndoor",cond=whenReply("more"))
					transition(edgeName="t028",targetState="movetohome",cond=whenReply("gohome"))
				}	 
				state("movetohome") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | vado alla posizione HOME")
						request("moverobot", "moverobot(0,0)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t029",targetState="trolleyathome",cond=whenReply("moverobotdone"))
				}	 
				state("trolleyathome") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | trolley at HOME")
						forward("setdirection", "dir(down)" ,"basicrobot" ) 
						delay(500) 
						forward("next_test", "next_test(_)" ,"test_suite" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("quit") { //this:State
					action { //it:State
						forward("disengage", "disengage(transporttrolley)" ,"basicrobot" ) 
						 System.exit(0)  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}