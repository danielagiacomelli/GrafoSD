/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.thrift.TException;

import Atomix.GetQuery;
import Atomix.PutCommand;
import DataBases.EdgeDatabase;
import DataBases.VertexDatabase;
import ThriftGenerate.Edge;
import ThriftGenerate.Methods;
import ThriftGenerate.Vertex;
import io.atomix.copycat.client.CopycatClient;

/**
 *
 * @author dcosta
 */
public class MethodsHandle implements Methods.Iface {

	private VertexDatabase dbVertex = null;
	private EdgeDatabase dbEdge = null;
	static float min;
	static int next = 0;
	CopycatClient ccClient;

	public MethodsHandle(CopycatClient ccClient) {
		try {
			dbVertex = new VertexDatabase();
			dbEdge = new EdgeDatabase();
			this.ccClient = ccClient;

		} catch (IOException e) {
		}
	}

	@Override
	public String createVertex(Vertex v) throws TException {

		Methods.Client client = Servidor.buscaServidor(v.idVertex);
		
		if(client == null){
			try {
				// minha base
				dbVertex.insertVertex(v);
				ccClient.submit(new PutCommand(0,dbVertex));
				System.out.println("****Vertice inserido*****");
				updateDataBase(0);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//updateDataBase()
			
		}else{
			return client.createVertex(v);
		}
		
		

		return "OK";
	}
	
	public void updateDataBase(int key){
		
		// Key = 0 lista de vertices
		CompletableFuture<Object> future = ccClient.submit(new GetQuery(key));
		
		//verificar se é vertice ou aresta
		
		try {
			if(key == 0){
				dbVertex = (VertexDatabase)future.get();
				
			}else if(key == 1){
				
				dbEdge = (EdgeDatabase)future.get();
			}
		
		//Listavertices = (ArrayList<Vertex>)future.get();
		
		//Arestasvertices = (ArrayList<Vertex>)future.get();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		future.join();
		
	}

	@Override
	public String readVertex(int idVertex) throws TException {

		Methods.Client client = Servidor.buscaServidor(idVertex);

		if (client == null) {

			System.out.println("Handler readVertex...");
			Vertex v = null;
			
			try {
				
				ccClient.submit(new GetQuery(0)).thenAccept(result -> {
					  System.out.println("foo is: " + result);
				});
				
				CompletableFuture<Object> future = ccClient.submit(new GetQuery(0));
				dbVertex = (VertexDatabase)future.get();
				v = dbVertex.searchVertex(idVertex);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return v.getNameVertex();

		} else {
			return client.readVertex(idVertex);
		}
	}

	public String readNameVertex(int idVertex) throws TException {

		Vertex result = null;
		try {
			result = dbVertex.searchVertex(idVertex);
		} catch (IOException e) {
		}
		return result.getNameVertex();

	}

	@Override
	public String deleteVertex(int idVertex) throws TException {

		Methods.Client client = Servidor.buscaServidor(idVertex);

		if (client == null) {

			System.out.println("Removendo dados...");
			Vertex result = null;

			try {
				result = dbVertex.searchVertex(idVertex);

			} catch (IOException e) {
			}
			if (result == null) {
				return "Registro inexistente";
			} else {
				try {
					for (int i = 0; i < result.getEd().size(); i++) {
						deleteEdge(result.getEd().get(i));
					}
					dbVertex.removeVertex(idVertex);

				} catch (IOException e) {
				}
				return "Registro removido!";
			}

		} else {
			return " " + client.deleteVertex(idVertex);
		}
	}

	@Override
	public String showBase() throws TException {

		// Methods.Client client = Servidor.simpleClientServidor();

		List<String> lVertex = new ArrayList<>();
		List<String> lEdge = new ArrayList<>();

		try {
			lVertex = dbVertex.ShowDataVertex();
			lEdge = dbEdge.ShowDataEdge();

		} catch (IOException ex) {
		}

		String aux = "";

		for (int i = 0; i < lVertex.size(); i++) {
			int id = Integer.parseInt(lVertex.get(i));

			aux = aux + readVertex(id) + "\n";
		}
		for (int i = 0; i < lEdge.size(); i++) {
			int id = Integer.parseInt(lEdge.get(i));

			aux = aux + readEdge(id) + "\n";
		}

		return aux;
	}

	@Override
	public String readEdge(int idEdge) throws TException {

		System.out.println("Procurando dados...");

		Edge result = null;
		try {
			result = dbEdge.searchEdge(idEdge);
		} catch (IOException e) {
		}
		if (result == null) {
			return "Registro nao encontrado!";
		}
		return result.toString();

	}

	public String readNameEdge(int idEdge) throws TException {
		Edge result = null;
		try {
			result = dbEdge.searchEdge(idEdge);
		} catch (IOException e) {
		}
		if (result == null) {
			return "Registro nao encontrado!";
		}
		return result.getNameEdge();
	}

	@Override
	public String deleteEdge(int idEdge) throws TException {

		System.out.println("Removendo dados...");

		Edge result = null;
		try {
			result = dbEdge.searchEdge(idEdge);
		} catch (IOException e) {
		}
		if (result == null) {
			return "Registro inexistente";
		} else {
			try {
				dbEdge.removeEdge(idEdge);

			} catch (IOException e) {
			}
			return "Registro removido!";
		}

	}

	@Override
	public String createEdge(Edge a) throws TException {

		Edge result = null;
		Vertex v1 = null;
		Vertex v2 = null;

		try {
			v1 = ReadVertexObject(a.idVertex1);
			v2 = ReadVertexObject(a.idVertex2);

			result = dbEdge.searchEdge(a.idEdge);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == null) {

			if (v1 == null || v2 == null) {
				return "Não é possivel adicionar, algum dos vértice inexistentes";

			} else {
				
				List<Integer> l = v1.getEd();
				l.add(a.idEdge);
				v1.setEd(l);
				
				List<Integer> l2 = v2.getEd();
				l2.add(a.idEdge);
				v2.setEd(l2);
				
				a.setVersion(1);


				try {
					dbEdge.insertEdge(a);
					return "Aresta " + a.nameEdge + " Inserida";

				} catch (IOException ex) {
					return "Erro na inserção" + "Erro: " + ex.getMessage();
				}
			}

		} else {
			return "Aresta já presente no banco";
		}

	}

	@Override
	public Vertex ReadVertexObject(int idVertex) throws TException {
		
		Methods.Client client = Servidor.buscaServidor(idVertex);
		if (client == null) {

			System.out.println("Procurando dados...");
			Vertex result = null;
			try {
				result = dbVertex.searchVertex(idVertex);

			} catch (IOException e) {
				e.printStackTrace();
			}

			if (result == null) {
				return null;
			} else {
				return result;
			}

		} else {
			return client.ReadVertexObject(idVertex);
		}
	}

	@Override
	public String listVertexForEdge(int idEdge) throws TException {

		Edge result = null;
		try {
			result = dbEdge.searchEdge(idEdge);
		} catch (IOException e) {
		}

		String a = readVertex(result.getIdVertex1());
		String b = readVertex(result.getIdVertex2());

		return "Vértices: [" + a + " - " + b + "]";

	}

	@Override
	public String listEdgeForVertex(int idVertex) throws TException {
		Vertex result = null;

		Methods.Client client = Servidor.buscaServidor(idVertex);
		if (client == null) {

			try {
				result = dbVertex.searchVertex(idVertex);

			} catch (IOException e) {
			}
			String textFinal = null;
			Edge e1 = null;

			for (int i = 0; i < result.getEd().size(); i++) {
				try {
					e1 = dbEdge.searchEdge(i);

				} catch (IOException ex) {
				}

				textFinal = textFinal + e1.getNameEdge() + " - ";
			}
			return "Arestas: [" + textFinal + "]";

		} else {
			return " " + client.listEdgeForVertex(idVertex);
		}
	}

	@Override
	public String listVertexForVertex(int idVertex) throws TException {

		Methods.Client client = Servidor.buscaServidor(idVertex);
		if (client == null) {

			Vertex result = null;
			Edge ed = null;

			Vertex v1 = null;

			String textFinal = null;
			try {
				result = dbVertex.searchVertex(idVertex);

				for (int i = 0; i < result.getEd().size(); i++) {
					ed = dbEdge.searchEdge(i);
					v1 = dbVertex.searchVertex(ed.getIdVertex1());

					textFinal = textFinal + v1.getNameVertex() + " - ";

				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}

			return "Vértices:[" + textFinal + "]";

		} else {
			return " " + client.listVertexForVertex(idVertex);
		}
	}

	@Override
	public String updateVertex(int idVertex, String color, String description) throws TException {

		Methods.Client client = Servidor.buscaServidor(idVertex);
		if (client == null) {

			Vertex result = null;
			try {
				result = dbVertex.searchVertex(idVertex);

			} catch (IOException ex) {
				ex.printStackTrace();
			}

			if (result == null) {
				return "Vertice não encontrado";
			} else {

				Vertex v = new Vertex();
				v.setColor(color);
				v.setDescription(description);
				v.setIdVertex(result.idVertex);
				v.setNameVertex(result.nameVertex);
				v.setEd(result.ed);

				try {
					dbVertex.insertVertex(v);
					return "Vertice " + result.getNameVertex() + " atualizado";

				} catch (IOException ex) {
					return "Erro na atualização" + "Erro: " + ex.getMessage();
				}
			}

		} else {
			return " " + client.updateVertex(idVertex, color, description);
		}
	}

	@Override
	public String updateEdge(int idEdge, String nameEdge, String description, double peso) throws TException {

		Edge result = null;
		try {
			result = dbEdge.searchEdge(idEdge);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (result == null) {
			return "Aresta não encontrada";
		} else {

			Edge e = new Edge();
			e.setDescription(description);
			e.setNameEdge(result.nameEdge);
			e.setIdVertex1(result.idVertex1);
			e.setIdVertex2(result.idVertex2);
			e.setIdEdge(result.idEdge);
			e.setVersion(result.version++);

			try {
				dbEdge.insertEdge(e);
				return "Aresta " + nameEdge + " atualizada";

			} catch (IOException ex) {
				return "Erro na atualização " + "Erro: " + ex.getMessage();
			}
		}
	}

	public static float[][] converte(List<Edge> listaArestas, int tamanho) {
		float[][] grapho = new float[50][50];

		for (Edge aresta : listaArestas) {
			grapho[aresta.getIdVertex1()][aresta.getIdVertex2()] = (float) aresta.getWeight();
		}

		return grapho;
	}

	@Override
	public double menorDistancia(List<Edge> listaArestas, int tamanho, int tag1, int tag2, List<Vertex> array)
			throws TException {// TODO Auto-generated method stub

		float[][] grapho = converte(listaArestas, array.size());
		Vertex vertice = new Vertex();
		double menorCaminho = 0.0;

		float distancia[] = new float[tamanho];
		int visitados[] = new int[tamanho];
		int pre[] = new int[tamanho + 1];
		float matriz[][] = new float[tamanho][tamanho];
		matriz = grapho;
		for (int i = 0; i <= tamanho; i++) {
			for (int j = 0; j <= tamanho; j++) {
				if (matriz[i][j] <= 0) {
					matriz[i][j] = 999;
				}
			}
		}
		for (int i = 0; i < tamanho; i++) {
			distancia[i] = matriz[tag1][i + 1];
		}

		visitados[tag1 - 1] = 1;

		for (int i = 0; i < tamanho; i++) {
			// min=999;
			// for(int j=0; j<tamanho; j++){
			// if(min>distancia[j] && visitados[j]!=1){
			// min = distancia[j];
			// next=j;
			//
			// }
			// }

			min = 999;
			for (int j = 0; j < tamanho; j++) {
				try {
					vertice = ReadVertexObject(j);
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (min > distancia[vertice.getIdVertex()] && visitados[vertice.getIdVertex()] != 1) {
					min = distancia[j];
					next = j;

				}
			}

			visitados[next] = 1;

			for (int c = 0; c < tamanho; c++) {
				if (visitados[c] != 1) {
					if (min + matriz[next + 1][c + 1] < distancia[c]) {
						distancia[c] = min + matriz[next + 1][c + 1];
						pre[c] = next;
					}

				}
			}

		}
		int j;
		if (distancia[tag2 - 1] == -1 || distancia[tag2 - 1] == 999) {
			System.out.println("\nCaminho: " + array.get(tag2 - 1).getIdVertex() + " Distância: " + 999);
		} else if (distancia[tag2 - 1] > 0 && distancia[tag2 - 1] < 999) {
			// System.out.println("\nCaminho: "+array.get(tag2-1).getNome()+" Distância:
			// "+distancia[tag2-1]);
			for (Vertex v : array) {
				if (v.getIdVertex() == tag2)
					System.out.println(
							"\nCaminho: de " + tag1 + " até " + v.getIdVertex() + " Distância: " + distancia[tag2 - 1]);
				menorCaminho = distancia[tag2 - 1];
			}
		}
		j = tag2 - 1;
		do {
			j = pre[j];
			if (j != 0) {
				System.out.println(" <- " + array.get(j).getIdVertex());
			}
		} while (j != 0);

		System.out.println(" <- " + array.get(tag1 - 1).getIdVertex());

		for (int i = 0; i < tamanho; i++) {

			distancia[i] = 0;
			visitados[i] = 0;
			pre[i] = 0;

		}

		return menorCaminho;
	}

}
