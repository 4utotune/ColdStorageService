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
with Diagram('coldstorageservice_sprint2Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctx_coldstorage', graph_attr=nodeattr):
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          transporttrolley_mock=Custom('transporttrolley_mock','./qakicons/symActorSmall.png')
     transporttrolley_mock >> Edge(color='magenta', style='solid', decorate='true', label='<chargedeposited &nbsp; >',  fontcolor='magenta') >> coldstorageservice
     transporttrolley_mock >> Edge(color='blue', style='solid',  label='<chargetaken &nbsp; >',  fontcolor='blue') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid',  label='<gotoindoor &nbsp; >',  fontcolor='blue') >> transporttrolley_mock
diag
