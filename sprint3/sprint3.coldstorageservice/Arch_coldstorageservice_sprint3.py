### conda install diagrams
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
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctx_coldstorage', graph_attr=nodeattr):
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          alarmdevice=Custom('alarmdevice','./qakicons/symActorSmall.png')
          warningdevice=Custom('warningdevice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          sonar=Custom('sonar(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
          ledMQTTSender=Custom('ledMQTTSender(coded)','./qakicons/codedQActor.png')
     alarmdevice >> Edge( label='alarm', **eventedgeattr, fontcolor='red') >> sys
     alarmdevice >> Edge( label='resume', **eventedgeattr, fontcolor='red') >> sys
     warningdevice >> Edge( label='ledoff', **eventedgeattr, fontcolor='red') >> sys
     warningdevice >> Edge( label='ledon', **eventedgeattr, fontcolor='red') >> sys
     warningdevice >> Edge( label='ledblink', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='magenta', style='solid', decorate='true', label='<engage &nbsp; moverobot &nbsp; >',  fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='magenta', style='solid', decorate='true', label='<chargedeposited &nbsp; >',  fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid',  label='<gotoindoor &nbsp; >',  fontcolor='blue') >> transporttrolley
     transporttrolley >> Edge(color='blue', style='solid',  label='<setdirection &nbsp; disengage &nbsp; >',  fontcolor='blue') >> basicrobot
     transporttrolley >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> warningdevice
     transporttrolley >> Edge(color='blue', style='solid',  label='<chargetaken &nbsp; >',  fontcolor='blue') >> coldstorageservice
diag
