
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils

class sonarTestSimulator(name : String) : ActorBasic(name) {

    private val DLIMIT = 10
    private var counterAlarm=0
    override suspend fun actorBody(msg: IApplMessage) {
        if ("alarm" !in msg.msgContent()) return
        if (msg.msgSender() == name) return //AVOID to handle the event emitted by itself
        elabData(msg)
    }

    private fun elabData(msg: IApplMessage) {
        // if( msg.msgId() == "sonardata" ) return; //avoid ...
        //System.out.println(msg.msgContent())
        //val data = (Term.createTerm(msg.msgContent()) as Struct).getArg(0).toString()
        if ("alarm" in msg.msgContent()){
            counterAlarm= counterAlarm+1
        }
    }

    public fun getCounterAlarm(): Int {
        return counterAlarm
    }
    fun getDLIMT(): Int {
        return DLIMIT
    }
    suspend fun emitSonarValue(distance: Int){
        if(distance>DLIMIT){
            val m2 = MsgUtil.buildEvent(name, "obstaclefree", "obstaclefree($distance)")
            CommUtils.outgreen("$tt $name |  emitLocalEvent m2= $m2")
            emitLocalEvent(m2) //emit event obstacle
        }else{
            val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($distance)")
            CommUtils.outgreen("$tt $name |  emitLocalEvent m1= $m1")
            emitLocalEvent(m1) //propagate event obstacle
        }
    }
}