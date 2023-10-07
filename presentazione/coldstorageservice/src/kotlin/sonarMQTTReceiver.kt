package rx

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils


class SonarMQTTReceiver(name: String) : ActorBasic(name) {
    private val brokerip = "tcp://mqtt.eclipseprojects.io"
    private val sonartopic = "unibo/sonar/events"
    private val clientId = "sonarReceiver"
    private lateinit var client: MqttClient

    init {
        runBlocking { autoMsg("sonarstart", "do") } // autostart
    }

    override suspend fun actorBody(msg: IApplMessage) {
        if (msg.msgId() == "sonarstart")
            startMqttReceiver()
    }

    private fun startMqttReceiver() {
        try {
            client = MqttClient(brokerip, clientId)
            val opt = MqttConnectOptions()
            opt.connectionTimeout = 5
            opt.isCleanSession = true

            client.connect(opt)

            CommUtils.outblue("sonarMQTTReceiver | Connected to MQTT client")
            client.subscribe(sonartopic)
            client.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable) {
                    CommUtils.outred("sonarMQTTReceiver | Lost Connection ")
                }

                override fun messageArrived(topic: String, message: MqttMessage) {
                    try {
                        val data = String(message.payload, charset("UTF-8"))
                        //println(data)
                        val event = CommUtils.buildEvent(name, "sonardata", data)

                        GlobalScope.launch {
                            emitLocalStreamEvent(event)
                        }
                    } catch (e: Exception) {
                        CommUtils.outred("sonarMQTTReceiver | Error on message arrival ")
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken) {
                    // Acknowledgement on delivery complete
                }
            })
        } catch (e: Exception) {
            CommUtils.outred("sonarMQTTReceiver | Failed to connect to MQTT client")
        }
    }
}


