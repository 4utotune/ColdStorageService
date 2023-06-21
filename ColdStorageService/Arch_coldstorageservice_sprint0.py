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
with Diagram('coldstorageservice_sprint0Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctx_coldstorage', graph_attr=nodeattr):
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
     with Cluster('ctx_accessgui', graph_attr=nodeattr):
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
     with Cluster('ctx_basicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctx_statusgui', graph_attr=nodeattr):
          servicestatusgui=Custom('servicestatusgui','./qakicons/symActorSmall.png')
     with Cluster('ctx_raspberry', graph_attr=nodeattr):
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
          led=Custom('led','./qakicons/symActorSmall.png')
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='coldroomdatarequest', fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='gotoindoor', fontcolor='blue') >> transporttrolley
     transporttrolley >> Edge(color='blue', style='solid', xlabel='updateled', fontcolor='blue') >> led
     transporttrolley >> Edge(color='blue', style='solid', xlabel='updatetrolleystatus', fontcolor='blue') >> servicestatusgui
     transporttrolley >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> coldstorageservice
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='chargedeposited', fontcolor='magenta') >> coldstorageservice
     sonar >> Edge(color='blue', style='solid', xlabel='alarm', fontcolor='blue') >> transporttrolley
diag
