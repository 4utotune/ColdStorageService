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
		 
				var TrolleyState: String
				var TrolleyPosition: (Int,Int)
				var CurrentWeight: Float
				var RejectedRequest: Int
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						CommUtils.outblack("[ServiceStatusGui] Init")
						 
									TrolleyState = "HOME"
									TrolleyPosition = (0,0)
									CurrentWeight = 0.0f
									RejectedRequest = 0
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						CommUtils.outblack("[ServiceStatusGui] Wait for updates...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t016",targetState="handle_trolley_update",cond=whenDispatch("updatetrolleystatus"))
					transition(edgeName="t017",targetState="handle_coldstorage_update",cond=whenDispatch("updatecoldstoragestatus"))
				}	 
				state("handle_trolley_update") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handle_coldstorage_update") { //this:State
					action { //it:State
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
