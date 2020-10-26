'''
Created on 24 Oct 2020

@author: Neil
'''
from socketserver import ThreadingMixIn
from http.server import HTTPServer, BaseHTTPRequestHandler
import json
from urllib.parse import urlparse, parse_qs

class ThreadedServer(HTTPServer, ThreadingMixIn):
    pass

    
class GetHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        #simple test for sending json to a get request (note, this will reply with the same message to all get requests, it doesn't parse parameters)
        print(self.path)
        
        self.send_response(200, 'OK')
        self.send_header('Content-type', 'text/json')
        self.send_header("Content-encoding", 'utf-8')
        self.end_headers()
        
        parsed_path = urlparse(self.path)
        print("Parsed path: %s" % str(parsed_path))
        parsed_query = parse_qs(parsed_path.query)
        print("Parsed query: %s" % str(parsed_query))
        
        message = None
        try:
            if ('metric' in parsed_query['type']):
                message = {
                    "type" : "metric",
                    "label" : "weight",
                    "value" : 2.52,
                    "unit" : "kg"
                }
            elif ('tag' in parsed_query['type']):
                message = {
                    "type" : "tag",
                    "name" : "location",
                    "value" : "US"
                }
            else:
                message = {
                    "type" : "error",
                    "message" : "unknown type"
                }
        except Exception as err:
            message = {
                "type" : "error",
                "message": "Exception: %s" % str(err)
            }
            
        json_message = json.dumps(message)
        self.wfile.write(json_message.encode('utf-8'))
        self.wfile.flush()

def run_server(server_class=ThreadedServer, handler_class=GetHandler):
    server_address = ('', 8000)
    httpd = server_class(server_address, handler_class)
    httpd.serve_forever()
    
run_server()