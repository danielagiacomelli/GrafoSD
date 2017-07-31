package ClientBD;

import ThriftGenerate.Edge;
import ThriftGenerate.Methods;
import ThriftGenerate.Vertex;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransportException;

public class JavaClient {

    private static Scanner scan;

    public static void main(String[] args) throws IOException, TTransportException, TException {

        try {
            TTransport transport;

            transport = new TSocket("localhost", 8070);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Methods.Client client = new Methods.Client(protocol);

            Connector c = new Connector();
            c.perform(client);


            /*int continua = 1;
            scan = new Scanner(System.in);
            Vertex vertice = new Vertex();
            Edge aresta = new Edge();
            
            int opcao;
            
            while (continua == 1) {
            opcao = menu();
            
            switch (opcao) {
            case 1:
            listarVertices(client);
            break;
            case 2:
            System.out.println("*** Digite o nome do vertice: ");
            int nomeVertice = scan.nextInt();
            buscarVertice(client, nomeVertice);
            break;
            case 3:
            Vertice v = new Vertice();
            System.out.println("*** Digite o nome:");
            vertice.setNome(scan.nextInt());
            System.out.println("*** Digite a cor:");
            vertice.setCor(scan.nextInt());
            System.out.println("*** Digite a descrição:");
            scan = new Scanner(System.in);
            vertice.setDescricao(scan.nextLine());
            System.out.println("*** Digite o peso:");
            vertice.setPeso(scan.nextInt());
            addVertice(client, v);
            break;
            case 4:
            System.out.println("*** Digite o nome do vertice:");
            int nome = scan.nextInt();
            verticesAdj(client, nome);
            break;
            case 5:
            System.out.println("*** Digite o nome do vertice:");
            nome = scan.nextInt();
            deletaVertice(client, nome);
            break;
            case 6:
            System.out.println("*** Digite o nome do vertice: ");
            nomeVertice = scan.nextInt();
            vertice = buscarVertice(client, nomeVertice);
            if (vertice.getNome() != 0) {
            System.out.println("*** Digite a cor do vertice: ");
            vertice.setCor(scan.nextInt());
            System.out.println("*** Digite a descrição do vertice: ");
            scan = new Scanner(System.in);
            vertice.setDescricao(scan.nextLine());
            System.out.println("*** Digite a peso do vertice: ");
            vertice.setPeso(scan.nextFloat());
            }
            atualizaVertice(client, vertice);
            break;
            case 7:
            listarArestas(client);
            break;
            case 8:
            System.out.println("*** Digite o nome do 1º vertice:");
            nome = scan.nextInt();
            System.out.println("*** Digite o nome do 2º vertice:");
            int nome2 = scan.nextInt();
            buscaAresta(client, nome, nome2);
            break;
            case 9:
            Aresta a = new Aresta();
            System.out.println("*** Digite o nome do 1º vertice:");
            a.setNomeVertice1(scan.nextInt());
            System.out.println("*** Digite o nome do 1º vertice:");
            a.setNomeVertice2(scan.nextInt());
            System.out.println("*** Digite o peso:");
            a.setPeso(scan.nextFloat());
            System.out.println("*** Digite a Flag:");
            a.setFlag(scan.nextInt());
            System.out.println("*** Digite a descrição:");
            scan = new Scanner(System.in);
            a.setDescricao(scan.nextLine());
            addAresta(client, a);
            break;
            case 10:
            System.out.println("*** Digite o nome do 1º vertice:");
            nome = scan.nextInt();
            System.out.println("*** Digite o nome do 2º vertice:");
            nome2 = scan.nextInt();
            deletaAresta2(client, nome, nome2);
            break;
            case 11:
            System.out.println("*** Digite o nome do vertice1: ");
            nome = scan.nextInt();
            System.out.println("*** Digite o nome do vertice2: ");
            nome2 = scan.nextInt();
            aresta = buscaAresta(client, nome, nome2);
            if (aresta.getNomeVertice1() != 0) {
            System.out.println("*** Digite a peso do vertice: ");
            aresta.setPeso(scan.nextFloat());
            System.out.println("*** Digite a direção da aresta bi/unidirecional: ");
            aresta.setFlag(scan.nextInt());
            System.out.println("*** Digite a descrição do vertice: ");
            scan = new Scanner(System.in);
            aresta.setDescricao(scan.nextLine());
            atualizaAresta(client, aresta);
            } else {
            System.out.println("Aresta não existe!");
            }
            break;
            }
            
            }*/
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }

    }

    /*public static int menu() {
    
    int opcao = 11;
    while (opcao >= 11) {
    System.out.println("*********** Vertice ***********");
    System.out.println("*** 1 - Listar Vertices     ***");
    System.out.println("*** 2 - Buscar Vertice 	    ***");
    System.out.println("*** 3 - Adicionar Vertice   ***");
    System.out.println("*** 4 - Vertices Adjacentes	***");
    System.out.println("*** 5 - Remove Vertice      ***");
    System.out.println("*** 6 - Atualiza Vertice    ***");
    System.out.println("*********** Aresta ************");
    System.out.println("*** 7 - Listar Aresta       ***");
    System.out.println("*** 8 - Busca Aresta 	    ***");
    System.out.println("*** 9 - Adiciona Aresta     ***");
    System.out.println("*** 10 - Remove Aresta       ***");
    System.out.println("*** 11 - Atualiza Aresta    ***");
    opcao = scan.nextInt();
    }
    return opcao;
    
    }
    
    private static Vertice buscarVertice(GrafoService.Client client, int nomeVertice) {
    Vertice vertice = null;
    try {
    vertice = client.buscaVertice(nomeVertice);
    if (vertice.getNome() != 0) {
    System.out.println("Descrição: " + vertice.getDescricao());
    } else {
    System.out.println("Vertice não encontrado");
    }
    } catch (TException e) {
    e.printStackTrace();
    }
    return vertice;
    
    }
    
    private static void listarVertices(GrafoService.Client client) throws TException {
    List<Vertice> lVertices = client.listaTodosVertices();
    System.out.println("");
    for (Vertice v : lVertices) {
    System.out.println("Vertice: " + v.getNome());
    System.out.println("Cor: " + v.getCor());
    System.out.println("Descrição: " + v.getDescricao());
    System.out.println("Peso: " + v.getPeso());
    System.out.println("================================");
    }
    
    }
    
    private static List<Integer> verticesAdjacentes(GrafoService.Client client, int nomeVertice) throws TException {
    List<Integer> lVertices = client.listaVerticesAdjacentes(nomeVertice);
    System.out.println("");
    for (Integer v : lVertices) {
    System.out.println("Vertice: " + v);
    System.out.println("================================");
    }
    
    return lVertices;
    
    }
    
    private static void addVertice(GrafoService.Client client, Vertice vertice) throws TException {
    if (client.criaVertice(vertice).getNome() == 0) {
    System.out.println("Já existe um vertice com este nome");
    }
    }
    
    private static void verticesAdj(GrafoService.Client client, int nomeVertice) throws TException {
    List<Integer> lista = client.listaVerticesAdjacentes(nomeVertice);
    if (lista.size() == 0) {
    System.out.println("Não existe vertices adjacentes");
    } else {
    for (Integer v : lista) {
    System.out.println("Vertice: " + v);
    }
    
    System.out.println("");
    }
    }
    
    private static void deletaVertice(GrafoService.Client client, int nomeVertice) throws TException {
    client.deletaVertice(nomeVertice);
    }
    
    private static void listarArestas(GrafoService.Client client) throws TException {
    List<Aresta> lArestas = client.listaTodasArestas();
    
    System.out.println("");
    for (Aresta a : lArestas) {
    System.out.println("Vertice1 : " + a.getNomeVertice1());
    System.out.println("Vertice1 : " + a.getNomeVertice2());
    System.out.println("Peso: " + a.getPeso());
    System.out.println("Flag: " + a.getFlag());
    System.out.println("Descrição: " + a.getDescricao());
    System.out.println("================================");
    }
    
    }
    
    private static Aresta buscaAresta(GrafoService.Client client, int nomeAresta1, int nomeAresta2) {
    try {
    Aresta a = client.buscaAresta(nomeAresta1, nomeAresta2);
    if (a.getNomeVertice1() != 0) {
    System.out.println("");
    System.out.println("Vertice1 : " + a.getNomeVertice1());
    System.out.println("Vertice1 : " + a.getNomeVertice2());
    System.out.println("Peso: " + a.getPeso());
    System.out.println("Flag: " + a.getFlag());
    System.out.println("Descrição: " + a.getDescricao());
    System.out.println("================================");
    }
    return a;
    } catch (TException e) {
    e.printStackTrace();
    }
    
    return new Aresta();
    
    }
    
    private static void deletaAresta2(GrafoService.Client client, int nomeVertice1, int nomeVertice2) throws TException {
    client.deletaAresta2(nomeVertice1, nomeVertice2);
    }
    
    private static void atualizaVertice(GrafoService.Client client, Vertice vertice) throws TException {
    client.atualizaVertice(vertice);
    }
    
    private static void atualizaAresta(GrafoService.Client client, Aresta aresta) throws TException {
    client.atualizaAresta(aresta);
    }
    
    private static void addAresta(GrafoService.Client client, Aresta aresta) throws TException {
    if (client.criaAresta(aresta).getNomeVertice1() == 0) {
    System.out.println("Já existe um vertice com este nome");
    }
    }
    
    private static void perform(GrafoService.Client client) {
    Vertice vertice;
    try {
    vertice = client.buscaVertice(23);
    System.out.println("Nome:" + vertice.getNome());
    System.out.println("Cor:" + vertice.getCor());
    } catch (TException e) {
    e.printStackTrace();
    System.out.println("Vertice não encontrado");
    return;
    }
    }*/
}
