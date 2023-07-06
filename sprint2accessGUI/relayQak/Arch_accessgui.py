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
with Diagram('accessguiArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctx_gui', graph_attr=nodeattr):
          gui_relay=Custom('gui_relay','./qakicons/symActorSmall.png')
     with Cluster('ctx_coldstorage', graph_attr=nodeattr):
          accessgui_proxy=Custom('accessgui_proxy(ext)','./qakicons/externalQActor.png')
     gui_relay >> Edge(color='blue', style='solid', xlabel='relay_storerequest', fontcolor='blue') >> accessgui_proxy
diag
