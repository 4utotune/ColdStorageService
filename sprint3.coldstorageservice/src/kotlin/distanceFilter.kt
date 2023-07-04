package rx

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils


class DistanceFilter(name: String) : ActorBasic(name) {
    private val DLIMIT = 10

    override suspend fun actorBody(msg: IApplMessage) {
        if (msg.msgId() != "sonardata") return
        if (msg.msgSender() == name) return //AVOID to handle the event emitted by itself
        elabData(msg)
    }

    private suspend fun elabData(msg: IApplMessage) {
        // if( msg.msgId() == "sonardata" ) return; //avoid ...
        val data = (Term.createTerm(msg.msgContent()) as Struct).getArg(0).toString()
        val distance = Integer.parseInt(data)

        if (distance > 0 && distance < DLIMIT) {
            val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($data)")
            CommUtils.outgreen("$tt $name |  emitLocalStreamEvent m1= $m1")
            emitLocalStreamEvent(m1) //propagate event obstacle
        } else {
            //println("$tt $name | DISCARDS $Distance ")
        }
    }
}