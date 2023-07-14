import time
import paho.mqtt.client as paho
import sys

brokerAddr="mqtt.eclipseprojects.io"  #"mqtt.eclipseprojects.io"  #"broker.hivemq.com"    
#msg      = "msg(sonardata,event,sonar,none,distance(10),1)"


    
def on_message(client, userdata, message) :   #define callback
    evMsg   = str( message.payload.decode("utf8"))
    status = int(evMsg)
    #print("RECEIVED ", evMsg)
    print(status) 
    sys.stdout.flush()



    
client= paho.Client("receiver")      
client.on_message=on_message            # Bind function to callback

client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
print("subscribing to unibo/led/events")
client.subscribe("unibo/led/events")      #subscribe

client.loop_forever()            #start loop to process received messages      
