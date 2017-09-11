package Client;

import java.io.IOException;
import java.util.Scanner;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import ThriftGenerate.Edge;
import ThriftGenerate.Methods;
import ThriftGenerate.Vertex;

public class Client {
	private static Scanner s;

	public static void main(String[] args) throws IOException, TTransportException, TException {
		try {
			TTransport transport;

			transport = new TSocket("localhost", 9092);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			Methods.Client client = new Methods.Client(protocol);

			perform(client);

			transport.close();

		} catch (Exception c) {
			c.printStackTrace();
		}

	}

	private static void perform(Methods.Client client) throws TException {

		while (true) {
			System.out.println("\n1 - Criar vertice" + "\n2 - Ler Vertice" + "\n3 - Deletar Vertice"
					+ "\n4 - Criar Aresta" + "\n5 - Ler Aresta" + "\n6 - Deletar Aresta" + "\n7 - Atualizar Aresta"
					+ "\n8 - Listar arestas por vértice");
			
			s = new Scanner(System.in);

			int valor_lido = s.nextInt();
			s.nextLine();

			switch (valor_lido) {
			case 1:

				System.out.println("Nome Vértice: ");
				String nome = s.nextLine();

				System.out.println("Id Vértice: ");
				int id = s.nextInt();

				System.out.println("Descrição: ");
				s.nextLine();
				String descricao = s.nextLine();

				System.out.println("Cor: ");
				String cor = s.nextLine();

				Vertex vertex = new Vertex();
				vertex.idVertex = id;
				vertex.nameVertex = nome;
				vertex.color = cor;
				vertex.description = descricao;

				System.out.println(client.createVertex(vertex));

				break;
			case 2:

				System.out.println("Id Vertice: ");
				int id1 = s.nextInt();
				System.out.println(client.readVertex(id1));

				break;
			case 3:

				System.out.println("Id Vertice: ");
				int id2 = s.nextInt();
				System.out.println(client.deleteVertex(id2));

				break;
			case 4:

				System.out.println("Nome Aresta: ");
				String nomeAresta = s.nextLine();

				System.out.println("Id Aresta: ");
				int idAresta = s.nextInt();

				System.out.println("Id Vértice 1: ");
				int v1 = s.nextInt();

				System.out.println("Id Vertex 2: ");
				int v2 = s.nextInt();
				
				System.out.println("Descrição: ");
				s.nextLine();
				String descricaoAresta = s.nextLine();

				System.out.println("Peso: ");
				double peso = s.nextDouble();

				Edge e = new Edge();
				e.nameEdge = nomeAresta;
				e.idEdge = idAresta;
				e.idVertex1 = v1;
				e.idVertex2 = v2;
				e.description = descricaoAresta;
				e.weight = peso;

				System.out.println(client.createEdge(e));

				break;

			case 5:

				System.out.println("Id Aresta: ");
				int idAresta2 = s.nextInt();

				System.out.println(client.readEdge(idAresta2));

				break;
			case 6:

				System.out.println("Id Aresta: ");
				int idAresta3 = s.nextInt();
				System.out.println(client.deleteEdge(idAresta3));

				break;
			case 7:

				System.out.println("Id Aresta: ");
				int idAresta4 = s.nextInt();
				s.nextLine();

				System.out.println("Nome aresta: ");
				String nameAresta = s.nextLine();
				s.nextLine();

				System.out.println("Descrição: ");
				String descriptionAresta = s.nextLine();
				s.nextLine();

				System.out.println("Peso aresta:");
				double pesoAresta = s.nextDouble();

				System.out.println(client.updateEdge(idAresta4, nameAresta, descriptionAresta, pesoAresta));

				break;
			case 8:

				System.out.println("Id Aresta");
				int idAaresta5 = s.nextInt();
				System.out.println(client.listVertexForEdge(idAaresta5));

				break;
			default:

			}
		}
	}
}
