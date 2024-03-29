from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('coldstorageservice_sprint3Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctx_basicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctx_coldstorage', graph_attr=nodeattr):
          accessgui_proxy=Custom('accessgui_proxy','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          alarmdevice=Custom('alarmdevice','./qakicons/symActorSmall.png')
          warningdevice=Custom('warningdevice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          sonar=Custom('sonar(coded)','./qakicons/codedQActor.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
          ledMQTTSender=Custom('ledMQTTSender(coded)','./qakicons/codedQActor.png')
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> accessgui_proxy
     accessgui_proxy >> Edge(color='magenta', style='solid', xlabel='proxy_storerequest', fontcolor='magenta') >> coldstorageservice
     accessgui_proxy >> Edge(color='magenta', style='solid', xlabel='proxy_insertticket', fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='gotoindoor', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> accessgui_proxy
     sys >> Edge(color='red', style='dashed', xlabel='obstacle', fontcolor='red') >> alarmdevice
     alarmdevice >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='obstaclefree', fontcolor='red') >> alarmdevice
     alarmdevice >> Edge( xlabel='resume', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> warningdevice
     warningdevice >> Edge( xlabel='ledoff', **eventedgeattr, fontcolor='red') >> sys
     warningdevice >> Edge( xlabel='ledon', **eventedgeattr, fontcolor='red') >> sys
     warningdevice >> Edge( xlabel='ledblink', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='alarm', fontcolor='red') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> coldstorageservice
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='chargedeposited', fontcolor='magenta') >> coldstorageservice
     transporttrolley >> Edge(color='blue', style='solid', xlabel='setdirection', fontcolor='blue') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='resume', fontcolor='red') >> transporttrolley
     transporttrolley >> Edge(color='blue', style='solid', xlabel='disengage', fontcolor='blue') >> basicrobot
diag
