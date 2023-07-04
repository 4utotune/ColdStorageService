package rx

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils


class DataCleaner(name: String) : ActorBasic(name) {
    private val LimitLow = 2
    private val LimitHigh = 150

    override suspend fun actorBody(msg: IApplMessage) {
        if (msg.msgId() != "sonardistance") return
        if (msg.msgSender() == name) return //AVOID to handle the event emitted by itself
        elabData(msg)
    }

    private suspend fun elabData(msg: IApplMessage) { //OPTIMISTIC
        val data = (Term.createTerm(msg.msgContent()) as Struct).getArg(0).toString()
        CommUtils.outyellow("$tt $name |  data = $data ")
        val distance = Integer.parseInt(data)
        if (distance > LimitLow && distance < LimitHigh) {
            emitLocalStreamEvent(msg) //propagate
            val m0 = MsgUtil.buildEvent(name, "sonardata", "distance($data)")
            CommUtils.outgreen("$tt $name |  emits = $m0 ")
            emit(m0)
        } else {
            CommUtils.outmagenta("$tt $name |  DISCARDS $distance ")
        }
    }
}