import it.unibo.kactor.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils

/*
-------------------------------------------------------------------------------------------------
sonarSimulator.kt
-------------------------------------------------------------------------------------------------
 */

class sonarSimulator ( name : String ) : ActorBasic( name ) {
	init{
		//autostart
		runBlocking{  autoMsg("simulatorstart","do") }
	}
//@kotlinx.coroutines.ObsoleteCoroutinesApi

    override suspend fun actorBody(msg : IApplMessage){
  		println("$tt $name | received  $msg "  )
		if( msg.msgId() == "simulatorstart") startDataReadSimulation(   )
     }
  	
//@kotlinx.coroutines.ObsoleteCoroutinesApi

	suspend fun startDataReadSimulation(    ){
		/*val data = sequence<Int>{
			var v0 = 75
			yield(v0)
			var flag = false
			while(true){
				if(v0==0) flag=true
				if(v0==200) flag=false

				if (flag){
					v0 = v0 - 5
				}else{
					v0 = v0 + 5
				}
				yield( v0 )
			}
		}*/
		val data = sequence<Int> {
			var v0 = 110
			yield(v0)

			while (true) {
				if (v0 > 0) {
					v0 -= 5
				} else if (v0 == 0) {
					v0 = 90
				} else {
					v0 = 0
				}

				yield(v0)
			}
		}
		var i = 0
			while( true ){
 	 			val m1 = "sonardata( ${data.elementAt(i)} )"
				i++
 				val event = CommUtils.buildEvent( name,"sonardata",m1)
  				emitLocalStreamEvent( event )
 				println("$tt $name | generates $event")
 				//emit(event)  //APPROPRIATE ONLY IF NOT INCLUDED IN A PIPE
 				delay( 500 )
  			}			
			terminate()
	}

} 

//@kotlinx.coroutines.ObsoleteCoroutinesApi
//
//fun main() = runBlocking{
// //	val startMsg = MsgUtil.buildDispatch("main","start","start","datasimulator")
//	val consumer  = dataConsumer("dataconsumer")
//	val simulator = sonarSimulator( "datasimulator" )
//	val filter    = dataFilter("datafilter", consumer)
//	val logger    = dataLogger("logger")
//	simulator.subscribe( logger ).subscribe( filter ).subscribe( consumer ) 
//	MsgUtil.sendMsg("start","start",simulator)
//	simulator.waitTermination()
// } 