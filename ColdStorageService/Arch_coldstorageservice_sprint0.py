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
     with Cluster('ctxcoldstorage', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          servicestatusgui=Custom('servicestatusgui','./qakicons/symActorSmall.png')
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
          led=Custom('led','./qakicons/symActorSmall.png')
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
diag
