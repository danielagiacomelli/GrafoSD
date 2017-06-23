package ClientBD;

import ThriftGenerate.Edge;
import ThriftGenerate.Vertex;
import ThriftGenerate.Methods;
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class JavaClient {

    public static void main(String[] args) {
        try {
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Methods.Client client = new Methods.Client(protocol);

            perform(client);

            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void perform(Methods.Client client) throws TException {

        Vertex vertex = new Vertex();
        
        vertex.idVertex = 1;
        vertex.nameVertex = "Daniel";
        vertex.version = 1;
        vertex.color = "Azul";
        vertex.description = "Descrição";
        vertex.weight = 10;
        

        Vertex vertex2 = new Vertex();
        vertex2.idVertex = 2;
        vertex2.nameVertex = "Saulo";
        vertex2.version = 1;
        vertex2.color = "Vermelho";
        vertex2.description = "Descrição";
        vertex2.weight = 5;

        //System.out.println(client.createVertex(vertex));
        System.out.println(client.createVertex(vertex2));
        //System.out.println(client.deleteVertex(1));
        //System.out.println(client.deleteVertex(2));
        //System.out.println(client.deleteEdge(1));

        //System.out.println(client.showBase());
        //System.out.println(client.readVertex(1));
        //System.out.println(client.readVertex(2));
        
        
        //System.out.println(client.deleteVertex(1));
        
        //System.out.println(client.showBase());
        
        
        Edge edge = new Edge();
        
        edge.idEdge = 1;
        edge.nameEdge = "aresta";
        edge.version = 1;
        edge.idVertex1 = 1;
        edge.idVertex2 = 2;
        edge.description = "Descrição";
        edge.flagDirected = true;
        edge.weight = 5;
        
        
        //System.out.println(client.updateVertex(1, "Amarelo", "Descrição Alterada", 15));
        //System.out.println(client.updateEdge(1, "a1", "Descrição alterada", 7, false));
        //System.out.println(client.createEdge(edge));
        //System.out.println(client.readEdge(1));
        
        //System.out.println(client.deleteEdge(1));
        
        
        //System.out.println(client.createEdge(edge));
        
        //System.out.println(client.showBase());
        
        System.out.println(client.showBase());
        
        //System.out.println(client.listVertexForEdge(1));
        //System.out.println(client.listEdgeForVertex(1));
    }
}
