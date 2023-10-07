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
with Diagram('coldstorageserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
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
     coldstorageservice >> Edge(color='magenta', style='solid', decorate='true', label='<gotoindoor<font color="darkgreen"> chargetaken</font> &nbsp; >',  fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', decorate='true', label='<engage<font color="darkgreen"> engagedone engagerefused</font> &nbsp; moverobot<font color="darkgreen"> moverobotdone moverobotfailed</font> &nbsp; >',  fontcolor='magenta') >> basicrobot
     coldstorageservice >> Edge(color='blue', style='solid',  label='<gohome &nbsp; >',  fontcolor='blue') >> transporttrolley
     transporttrolley >> Edge(color='blue', style='solid',  label='<setdirection &nbsp; disengage &nbsp; >',  fontcolor='blue') >> basicrobot
     alarmdevice >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> warningdevice
     transporttrolley >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> warningdevice
     transporttrolley >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> coldstorageservice
diag
