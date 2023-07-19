package rx

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils


class DistanceFilter(name: String) : ActorBasic(name) {
    private val DLIMIT = 10

    private var lastMeasurement = 0;

    override suspend fun actorBody(msg: IApplMessage) {
        //print(msg)
        if (msg.msgId() != "sonarcleaned") return

        if (msg.msgSender() == name) return //AVOID to handle the event emitted by itself
        if (msg.msgSender() == "TESTJUNIT") elabTest(msg) //utile a soli fini di test
        else elabData(msg)
    }

    private suspend fun elabTest(msg: IApplMessage){ //solo fini test
        //print(msg.msgContent())
        //val data = (Term.createTerm(msg.msgContent()) as Struct).getArg(0).toString()

        val distanceint = Integer.parseInt(msg.msgContent())
        if (distanceint > 0 && distanceint < DLIMIT) {
            //System.out.println("[distanceFilter] ho emesso obstacle")
            val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($distanceint)")
            CommUtils.outblack("$name |  emitLocalStreamEvent m1= $m1")
            emitLocalStreamEvent(m1) //propagate event obstacle
        } else {
            //System.out.println("[distanceFilter] ho emesso obstaclefree")
            val m2 = MsgUtil.buildEvent(name, "obstaclefree", "obstaclefree($distanceint)")
            CommUtils.outblack("$name |  emitLocalStreamEvent m2= $m2")
            emitLocalStreamEvent(m2) //propagate event obstacle
        }
    }

    private suspend fun elabData(msg: IApplMessage) {
        // if( msg.msgId() == "sonardata" ) return; //avoid ...
        //System.out.println(msg.msgContent())
        val data = (Term.createTerm(msg.msgContent()) as Struct).getArg(0).toString()

        //System.out.println("[distancefilter] "+data)

        val distance = Integer.parseInt(data)

        //System.out.println("[distanceFilter] "+distance)
        if (distance > 0 && distance < DLIMIT) {
            //System.out.println("[distanceFilter] ho emesso obstacle")
            val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($distance)")
            if (distance != lastMeasurement) CommUtils.outblack("$name |  emitLocalStreamEvent m1= $m1")
            emitLocalStreamEvent(m1) //propagate event obstacle
        } else {
            //System.out.println("[distanceFilter] ho emesso obstaclefree")
            val m2 = MsgUtil.buildEvent(name, "obstaclefree", "obstaclefree($distance)")
            if (distance != lastMeasurement) CommUtils.outblack("$name |  emitLocalStreamEvent m2= $m2")
            emitLocalStreamEvent(m2) //propagate event obstacle
        }
        lastMeasurement = distance
    }
}