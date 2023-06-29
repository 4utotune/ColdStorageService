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
with Diagram('provaArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctx_prova', graph_attr=nodeattr):
          signora=Custom('signora','./qakicons/symActorSmall.png')
     with Cluster('ctx_signora', graph_attr=nodeattr):
          signore=Custom('signore','./qakicons/symActorSmall.png')
     signore >> Edge(color='blue', style='solid', xlabel='ciao', fontcolor='blue') >> signora
diag
