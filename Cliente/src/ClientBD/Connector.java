/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientBD;

import ThriftGenerate.Edge;
import ThriftGenerate.Methods;
import ThriftGenerate.Vertex;
import org.apache.thrift.TException;

/**
 *
 * @author dcosta
 */
public class Connector {

    public static void perform(Methods.Client client, Methods.Client client2) throws TException{

        Vertex vertex = new Vertex();

        vertex.idVertex = 1;
        vertex.nameVertex = "Daniel";
        vertex.version = 1;
        vertex.color = "Azul";
        vertex.description = "Descrição";
        vertex.weight = 21;
        vertex.distance = 0;

        Vertex vertex2 = new Vertex();
        vertex2.idVertex = 2;
        vertex2.nameVertex = "Saulo";
        vertex2.version = 1;
        vertex2.color = "Vermelho";
        vertex2.description = "Descrição";
        vertex2.weight = 19;
        vertex2.distance = 5;

        Vertex vertex3 = new Vertex();

        vertex3.idVertex = 3;
        vertex3.nameVertex = "Paulo";
        vertex3.version = 1;
        vertex3.color = "Verde";
        vertex3.description = "Descrição";
        vertex3.weight = 24;
        vertex3.distance = 10;

        Edge edge = new Edge();

        edge.idEdge = 1;
        edge.nameEdge = "aresta";
        edge.version = 1;
        edge.idVertex1 = 1;
        edge.idVertex2 = 2;
        edge.description = "Descrição";
        edge.flagDirected = true;
        edge.weight = 2;

        Edge edge2 = new Edge();

        edge2.idEdge = 2;
        edge2.nameEdge = "aresta2";
        edge2.version = 1;
        edge2.idVertex1 = 2;
        edge2.idVertex2 = 3;
        edge2.description = "Descrição";
        edge2.flagDirected = true;
        edge2.weight = 5;

        Edge edge3 = new Edge();

        edge3.idEdge = 3;
        edge3.nameEdge = "aresta3";
        edge3.version = 1;
        edge3.idVertex1 = 1;
        edge3.idVertex2 = 3;
        edge3.description = "Descrição";
        edge3.flagDirected = true;
        edge3.weight = 15;
        
        criarVertice(vertex2, client, client2);
        //criarAresta(edge, client);
        
        //deletarVertex(1, client, client2);
        //deletarAresta(1, client);
        
        //lerVertice(1, client, client2);
        //lerAresta(1, client);
        
        //updateAresta(1, client);
        //updateVertice(1, client, client2);
        
        ///dijkstra(vertex, vertex3, client, client2);
        showBase(client, client2);
    }

    private static void criarVertice(Vertex vertex, Methods.Client client, Methods.Client client2) throws TException {
        if(vertex.idVertex%2 != 0){
            System.out.println(client.createVertex(vertex));
        }
        else{
            System.out.println(client2.createVertex(vertex));
        }
    }

    private static void criarAresta(Edge edge, Methods.Client client) throws TException {
        System.out.println(client.createEdge(edge));
    }

    private static void deletarVertex(int idVertice, Methods.Client client, Methods.Client client2) throws TException {
        if(idVertice%2 == 0){
            System.out.println(client.deleteVertex(idVertice));
        }
        else{
            System.out.println(client2.deleteVertex(idVertice));
        }
    }

    private static void deletarAresta(int idAresta, Methods.Client client) throws TException {
        System.out.println(client.deleteEdge(idAresta));
    }

    private static void lerAresta(int id, Methods.Client client) throws TException {
        System.out.println(client.readEdge(id));
    }

    private static void lerVertice(int idVertice, Methods.Client client, Methods.Client client2) throws TException {
        if(idVertice%2 == 0){
            System.out.println(client.readVertex(idVertice));
        }
        else{
            System.out.println(client2.readVertex(idVertice));
        }
    }

    private static void updateVertice(int idVertice, Methods.Client client, Methods.Client client2) throws TException {
        if(idVertice%2 == 0){
            System.out.println(client.updateVertex(idVertice, "Amarelo", "Descrição Alterada", 15));
        }
        else{
            System.out.println(client2.updateVertex(idVertice, "Amarelo", "Descrição Alterada", 15));
        }
    }

    private static void updateAresta(int id, Methods.Client client) throws TException {
        System.out.println(client.updateEdge(id, "a1", "Descrição alterada", 7, false));
    }

    private static void showBase(Methods.Client client, Methods.Client client2) throws TException {
        System.out.println(client.showBase());
        System.out.println(client2.showBase());
    }

    private static void dijkstra(Vertex vertex, Vertex vertex3, Methods.Client client, Methods.Client client2) throws TException {
        System.out.println(client.dijkstra(vertex, vertex3));
    }
}

//System.out.println(client.createVertex(vertex));
//System.out.println(client.createVertex(vertex2));
//System.out.println(client.createVertex(vertex3));

//System.out.println(client.deleteVertex(1));
//System.out.println(client.deleteVertex(2));
//System.out.println(client.deleteVertex(3));

//System.out.println(client.deleteEdge(1));
//System.out.println(client.showBase());
//System.out.println(client.readVertex(1));
//System.out.println(client.readVertex(2));
//System.out.println(client.deleteVertex(1));
//System.out.println(client.showBase());


//System.out.println(client.updateVertex(1, "Amarelo", "Descrição Alterada", 15));
//System.out.println(client.updateEdge(1, "a1", "Descrição alterada", 7, false));
//System.out.println(client.createEdge(edge));
//System.out.println(client.createEdge(edge2));
//System.out.println(client.createEdge(edge3));
//System.out.println(client.readEdge(1));
//System.out.println(client.deleteEdge(1));
//System.out.println(client.deleteEdge(2));
//System.out.println(client.deleteEdge(3));
//System.out.println(client.createEdge(edge2));
//System.out.println(client.listVertexForVertex(2));
//System.out.println(client.showBase());
//System.out.println(client.dijkstra(vertex, vertex3));
//System.out.println(client.listVertexForEdge(1));
//System.out.println(client.listEdgeForVertex(1));
