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
import it.unibo.kactor.sysUtil.createActor   //Sept2023
class Alarmdevice ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 var MINT = 5000L  
				return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						 subscribeToLocalActor("distancefilter").subscribeToLocalActor("sonarreceiver")  
						CommUtils.outyellow("$name | init")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						CommUtils.outyellow("$name | attendo...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t0",targetState="handleobstacle",cond=whenEvent("obstacle"))
				}	 
				state("handleobstacle") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						if( checkMsgContent( Term.createTerm("obstacle(D)"), Term.createTerm("obstacle(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								CommUtils.outyellow("$name | obstacle -> ALARM")
								emit("alarm", "alarm(_)" ) 
								updateResourceRep( "azione(STOPPED)"  
								)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t01",targetState="handleobstaclefree",cond=whenEvent("obstaclefree"))
				}	 
				state("handleobstaclefree") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("obstaclefree(D)"), Term.createTerm("obstaclefree(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								emit("resume", "resume(_)" ) 
								CommUtils.outyellow("$name | obstaclefree -> RESUME. Aspetto MINT prima di gestire un successivo stop")
								 delay(MINT)  
								CommUtils.outyellow("$name | sono di nuovo pronto per poter gestire uno stop")
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
