package Project;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import ThriftGenerate.Methods;

public class Main {
	
	public static void main(String[] args) {
		try {

			MethodsHandle handler = new MethodsHandle();
			Methods.Processor processor = new Methods.Processor(handler);
			TServerTransport serverTransport = new TServerSocket(9090);
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
			//TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

			// Use this for a multithreaded server
			// server = new TThreadPoolServer(new Args(serverTransport).processor(processor));

			System.out.println("Starting server...");
			server.serve();
		} catch(Exception x) {
			x.printStackTrace();
		}
		System.out.println("done");
	}
}
