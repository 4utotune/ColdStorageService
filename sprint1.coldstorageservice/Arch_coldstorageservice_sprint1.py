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
with Diagram('coldstorageservice_sprint1Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctx_coldstorage', graph_attr=nodeattr):
          test_suite=Custom('test_suite','./qakicons/symActorSmall.png')
          accessguimock=Custom('accessguimock','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
     with Cluster('ctx_basicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     test_suite >> Edge(color='blue', style='solid', xlabel='next_test', fontcolor='blue') >> test_suite
     test_suite >> Edge(color='blue', style='solid', xlabel='mock_store_request', fontcolor='blue') >> accessguimock
     test_suite >> Edge(color='blue', style='solid', xlabel='mock_ticket_input', fontcolor='blue') >> accessguimock
     accessguimock >> Edge(color='magenta', style='solid', xlabel='storerequest', fontcolor='magenta') >> coldstorageservice
     accessguimock >> Edge(color='blue', style='solid', xlabel='test_gotticket', fontcolor='blue') >> test_suite
     accessguimock >> Edge(color='magenta', style='solid', xlabel='insertticket', fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='gotoindoor', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> accessguimock
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> coldstorageservice
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='chargedeposited', fontcolor='magenta') >> coldstorageservice
     transporttrolley >> Edge(color='blue', style='solid', xlabel='setdirection', fontcolor='blue') >> basicrobot
     transporttrolley >> Edge(color='blue', style='solid', xlabel='next_test', fontcolor='blue') >> test_suite
     transporttrolley >> Edge(color='blue', style='solid', xlabel='disengage', fontcolor='blue') >> basicrobot
diag
