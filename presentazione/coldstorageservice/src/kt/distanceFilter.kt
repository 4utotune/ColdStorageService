package kt

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils.outyellow


class DistanceFilter(name: String, scope: CoroutineScope = GlobalScope, confined: Boolean = false) : ActorBasic(name, scope, confined) {
    private val DLIMIT = 8

    private var lastMeasurement = 0
    private var lastEvent = ""

    override suspend fun actorBody(msg: IApplMessage) {
        if (msg.msgSender() == name) return //AVOID to handle the event emitted by itself
        if (msg.msgId() != "sonardata") return
        elabData(msg)
    }

    private suspend fun elabData(msg: IApplMessage) {
        var distanza = "-1"
        val data = (Term.createTerm(msg.msgContent()) as Struct).getArg(4).toString()
        val inizioParentesi = data.indexOf('(')
        val fineParentesi = data.indexOf(')')
        if (inizioParentesi != -1 && fineParentesi != -1 && inizioParentesi < fineParentesi) {
            distanza = data.substring(inizioParentesi + 1, fineParentesi)
        }

        val distance = Integer.parseInt(distanza)

        var event = ""
        if (distance in 1 until DLIMIT) {
            event = "obstacle"
        } else if (distance != -1) {
            event = "obstaclefree"
        }
        if (event != "" && distance != lastMeasurement) {
            if (event != lastEvent) {
                outyellow("[distanceFilter] $event (distanza $distance)")
            }
            val m = MsgUtil.buildEvent(name, event, "$event($distance)")
            emitLocalStreamEvent(m) //propaga evento
            lastMeasurement = distance
            lastEvent = event
        }
    }
}