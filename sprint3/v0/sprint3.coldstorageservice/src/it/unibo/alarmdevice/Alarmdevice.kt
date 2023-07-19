/* Generated by AN DISI Unibo */ 
package it.unibo.alarmdevice

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Alarmdevice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 var MINT = 10000L  
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						 subscribeToLocalActor("distancefilter").subscribeToLocalActor("datacleaner").subscribeToLocalActor("sonar")  
						CommUtils.outred("$name | init")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						CommUtils.outred("$name | attendo...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t012",targetState="handleobstacle",cond=whenEvent("obstacle"))
				}	 
				state("handleobstacle") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("obstacle(D)"), Term.createTerm("obstacle(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outred("$name | handleobstacle ALARM ${payloadArg(0)}")
								emit("alarm", "alarm(_)" ) 
								updateResourceRep( "$name(ON)" 
								)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t013",targetState="handleobstaclefree",cond=whenEvent("obstaclefree"))
				}	 
				state("handleobstaclefree") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("obstaclefree(D)"), Term.createTerm("obstaclefree(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outred("$name | obstaclefree RESUME ${payloadArg(0)}")
								emit("resume", "resume(_)" ) 
								CommUtils.outred("$name | aspetto DLMIT prima di poter gestire un successivo stop")
								 delay(MINT)  
								CommUtils.outred("$name | sono di nuovo pronto per poter gestire uno stop")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}
