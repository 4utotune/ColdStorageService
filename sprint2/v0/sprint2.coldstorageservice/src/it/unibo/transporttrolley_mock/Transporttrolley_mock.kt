/* Generated by AN DISI Unibo */ 
package it.unibo.transporttrolley_mock

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Transporttrolley_mock ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | init")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="engaged", cond=doswitch() )
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
					 transition(edgeName="t012",targetState="moveToIndoor",cond=whenDispatch("gotoindoor"))
				}	 
				state("moveToIndoor") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | vado all'INDOOR")
						delay(5000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="loadTheCharge", cond=doswitch() )
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
						delay(5000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="storethecharge", cond=doswitch() )
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
					 transition(edgeName="t013",targetState="moveToIndoor",cond=whenReply("more"))
					transition(edgeName="t014",targetState="movetohome",cond=whenReply("gohome"))
				}	 
				state("movetohome") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | vado alla posizione HOME")
						delay(5000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="trolleyathome", cond=doswitch() )
				}	 
				state("trolleyathome") { //this:State
					action { //it:State
						discardMessages = true
						CommUtils.outmagenta("$name | trolley at HOME")
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
