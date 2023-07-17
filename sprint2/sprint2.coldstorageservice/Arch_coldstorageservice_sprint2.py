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
with Diagram('coldstorageservice_sprint2Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctx_coldstorage', graph_attr=nodeattr):
          accessgui_proxy=Custom('accessgui_proxy','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          transporttrolley_mock=Custom('transporttrolley_mock','./qakicons/symActorSmall.png')
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> accessgui_proxy
     accessgui_proxy >> Edge(color='magenta', style='solid', xlabel='proxy_storerequest', fontcolor='magenta') >> coldstorageservice
     accessgui_proxy >> Edge(color='magenta', style='solid', xlabel='proxy_insertticket', fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='gotoindoor', fontcolor='blue') >> transporttrolley_mock
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> accessgui_proxy
     transporttrolley_mock >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> coldstorageservice
     transporttrolley_mock >> Edge(color='magenta', style='solid', xlabel='chargedeposited', fontcolor='magenta') >> coldstorageservice
diag
