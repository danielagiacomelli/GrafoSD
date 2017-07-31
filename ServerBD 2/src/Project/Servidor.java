/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import ThriftGenerate.Methods;
import java.util.ArrayList;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

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

        mapeamento.setNomeServidor(1);
        mapeamento.setPorta(8080);

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

            System.out.println("Starting server 2...");
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Methods.Client buscaServidor(int id) {
        int faixa = id % 3;

        if (faixa == mapeamento.getNomeServidor()) {
            return null;
        } else {

            for (MapeamentoServidor map : listaMapeamento) {
                if (map.getNomeServidor() == faixa) {
                    Methods.Client client = simpleClientServidor(map.getPorta());
                    return client;
                }
            }

            return null;
        }
    }
    
    //Implementar
    private static Methods.Client simpleClientServidor(int porta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
