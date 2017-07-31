/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import ThriftGenerate.Methods;
import ThriftGenerate.Vertex;
import java.util.ArrayList;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author dcosta
 */
public class Servidor {

    public static MethodsHandle handler;
    public static MapeamentoServidor mapeamento;
    public static ArrayList<MapeamentoServidor> listaMapeamento = new ArrayList<MapeamentoServidor>();

    public static void main(String[] args) {

        mapeamento = new MapeamentoServidor();
        
        mapeamento.setNomeServidor(0);
        mapeamento.setPorta(8070);

        try {

            handler = new MethodsHandle();
            Methods.Processor processor = new Methods.Processor(handler);

            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor, mapeamento);
                }
            };

            new Thread(simple).start();
            
        } catch (Exception x) {
            x.printStackTrace();
        }

    }

    public static void simple(Methods.Processor processor, MapeamentoServidor mapeamento) {
        try {

            TServerTransport serverTransport = new TServerSocket(mapeamento.getPorta());
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting server 1...");
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void simpleClientServidor(int porta, Vertex v, int idOperacao) {
        try {
            TTransport transport;

            transport = new TSocket("localhost", porta);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Methods.Client client = new Methods.Client(protocol);

			if(idOperacao == 1)
				client.createVertex(v);
			else
				if(idOperacao == 2)
					client.readVertex(v.idVertex);
				else
					if(idOperacao == 2)
						client.deleteVertex(v.idVertex);
		
            transport.close();

        } catch (TException x) {
            x.printStackTrace();
        }
    }
}
