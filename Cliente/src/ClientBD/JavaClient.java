package ClientBD;

import ThriftGenerate.Edge;
import ThriftGenerate.Vertex;
import ThriftGenerate.Methods;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransportException;

public class JavaClient {

    public static void main(String[] args) throws IOException, TTransportException, TException {

        /*try {
        TTransport transport;
        
        transport = new TSocket("localhost", 8090);
        transport.open();
        
        TProtocol protocol = new TBinaryProtocol(transport);
        Methods.Client client = new Methods.Client(protocol);
        
        Connector c = new Connector();
        c.perform(client);
        
        transport.close();
        } catch (TException x) {
        x.printStackTrace();
        }*/
        int quantidade = 2;
        TTransport[] conexoes = new TSocket[quantidade];

        for (int i = 0; i < quantidade; i++) {
            conexoes[i] = new TSocket("localhost", 8090 + 1);

            conexoes[i].open();
        }
        
        TProtocol protocol = new TBinaryProtocol(conexoes[0]);
        TProtocol protocol2 = new TBinaryProtocol(conexoes[1]);
        
        Methods.Client client = new Methods.Client(protocol);
        Methods.Client client2 = new Methods.Client(protocol2);

        Connector c = new Connector();
        c.perform(client, client2);
        
        for (int i = 0; i < quantidade; i++) {
            conexoes[i].close();
        }
    }
}
