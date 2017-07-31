/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import ThriftGenerate.Methods;
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

        mapeamento.setNomeServidor(2);
        mapeamento.setPorta(8090);

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

            System.out.println("Starting server 3...");
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void simpleClientServidor(int porta) {
        try {
            TTransport transport;

            transport = new TSocket("localhost", 8090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Methods.Client client = new Methods.Client(protocol);

            Connector c = new Connector();

            transport.close();

        } catch (TException x) {
            x.printStackTrace();
        }
    }

}
