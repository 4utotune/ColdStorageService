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
		 var allarme: Int = 0  
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
					 transition(edgeName="t025",targetState="engaged",cond=whenReply("engagedone"))
					transition(edgeName="t026",targetState="quit",cond=whenReply("engagerefused"))
				}	 
				state("engaged") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | basicrobot engaged")
						updateResourceRep( "$name(OFF)" 
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						 allarme = 1  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t027",targetState="moveToIndoor",cond=whenDispatch("gotoindoor"))
					transition(edgeName="t028",targetState="sonarobstacle",cond=whenEvent("alarm"))
				}	 
				state("moveToIndoor") { //this:State
					action { //it:State
						 allarme = 2  
						updateResourceRep( "$name(BLINK)" 
						)
						CommUtils.outmagenta("$name | vado all'INDOOR")
						request("moverobot", "moverobot(0,4)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t029",targetState="loadTheCharge",cond=whenReply("moverobotdone"))
					transition(edgeName="t030",targetState="sonarobstacle",cond=whenEvent("alarm"))
				}	 
				state("loadTheCharge") { //this:State
					action { //it:State
						 allarme = 3  
						updateResourceRep( "$name(ON)" 
						)
						CommUtils.outmagenta("$name | sto caricando il carico presso l'INDOOR")
						forward("chargetaken", "chargetaken(_)" ,"coldstorageservice" ) 
						updateResourceRep( "$name(BLINK)" 
						)
						CommUtils.outmagenta("$name | vado verso la cold room")
						request("moverobot", "moverobot(4,3)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t031",targetState="storethecharge",cond=whenReply("moverobotdone"))
					transition(edgeName="t032",targetState="sonarobstacle",cond=whenEvent("alarm"))
				}	 
				state("storethecharge") { //this:State
					action { //it:State
						 allarme = 4  
						updateResourceRep( "$name(ON)" 
						)
						CommUtils.outmagenta("$name | scarico il carico presso la Cold Room")
						delay(4000) 
						request("chargedeposited", "chargedeposited(_)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t033",targetState="moveToIndoor",cond=whenReply("more"))
					transition(edgeName="t034",targetState="movetohome",cond=whenReply("gohome"))
					transition(edgeName="t035",targetState="sonarobstacle",cond=whenEvent("alarm"))
				}	 
				state("movetohome") { //this:State
					action { //it:State
						 allarme = 5  
						updateResourceRep( "$name(BLINK)" 
						)
						CommUtils.outmagenta("$name | vado alla posizione HOME")
						request("moverobot", "moverobot(0,0)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t036",targetState="trolleyathome",cond=whenReply("moverobotdone"))
					transition(edgeName="t037",targetState="sonarobstacle",cond=whenEvent("alarm"))
				}	 
				state("trolleyathome") { //this:State
					action { //it:State
						updateResourceRep( "$name(OFF)" 
						)
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
				state("pathfailed") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | Errore | PATH FAILED | RITENTO")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="moveToIndoor", cond=doswitch() )
				}	 
				state("pathfailed2") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | Errore | PATH FAILED | RITENTO")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="movetohome", cond=doswitch() )
				}	 
				state("sonarobstacle") { //this:State
					action { //it:State
						CommUtils.outmagenta("$name | Sono fermo per ostacolo sonar")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t038",targetState="idle",cond=whenEventGuarded("resume",{ allarme == 1  
					}))
					transition(edgeName="t039",targetState="moveToIndoor",cond=whenEventGuarded("resume",{ allarme == 2  
					}))
					transition(edgeName="t040",targetState="loadTheCharge",cond=whenEventGuarded("resume",{ allarme == 3  
					}))
					transition(edgeName="t041",targetState="storethecharge",cond=whenEventGuarded("resume",{ allarme == 4  
					}))
					transition(edgeName="t042",targetState="movetohome",cond=whenEventGuarded("resume",{ allarme == 5  
					}))
				}	 
				state("quit") { //this:State
					action { //it:State
						updateResourceRep( "$name(OFF)" 
						)
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
