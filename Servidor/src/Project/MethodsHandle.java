/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import DataBases.EdgeDatabase;
import DataBases.VertexDatabase;
import Models.DadosEdge;
import Models.DadosVertex;
import ThriftGenerate.Edge;
import ThriftGenerate.Vertex;
import ThriftGenerate.Methods;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author dcosta
 */
public class MethodsHandle implements Methods.Iface {

    private VertexDatabase dbVertex = null;
    private EdgeDatabase dbEdge = null;

    private HashMap log;

    public MethodsHandle() {
        log = new HashMap();

        try {
            dbVertex = new VertexDatabase();
            dbEdge = new EdgeDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createVertex(Vertex v) throws TException {
        DadosVertex result = null;
        List l = new ArrayList<Integer>();

        try {
            result = dbVertex.searchVertex(v.idVertex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result == null) {
            try {
                dbVertex.insertVertex(new DadosVertex(v.nameVertex, v.idVertex, v.color, v.description, v.weight, 1, l));
                return "Dados inseridos com sucesso";
            } catch (IOException e) {
                return "Erro na inserção"
                        + "Erro: " + e.getMessage();
            }
        } else {
            return "Vertice " + v.nameVertex + " já presente no banco";
        }
    }

    @Override
    public String readVertex(int idVertex) throws TException {
        System.out.println("Procurando dados...");
        DadosVertex result = null;
        try {
            result = dbVertex.searchVertex(idVertex);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (result == null) {
            return "Registro nao encontrado!";
        }
        return result.toString();
    }

    public String readNameVertex(int idVertex) throws TException {
        DadosVertex result = null;
        try {
            result = dbVertex.searchVertex(idVertex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.getNameVertex();
    }

    @Override
    public String deleteVertex(int idVertex) throws TException {
        System.out.println("Removendo dados...");
        DadosVertex result = null;

        try {
            result = dbVertex.searchVertex(idVertex);

        } catch (IOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
            return "Registro removido!";
        }
    }

    @Override
    public String showBase() throws TException {
        List<String> lVertex = new ArrayList<String>();
        List<String> lEdge = new ArrayList<String>();

        try {
            lVertex = dbVertex.ShowDataVertex();
            lEdge = dbEdge.ShowDataEdge();

        } catch (IOException ex) {
            ex.printStackTrace();
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

        DadosEdge result = null;
        try {
            result = dbEdge.searchEdge(idEdge);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (result == null) {
            return "Registro nao encontrado!";
        }
        return result.toString();
    }

    public String readNameEdge(int idEdge) throws TException {
        DadosEdge result = null;
        try {
            result = dbEdge.searchEdge(idEdge);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (result == null) {
            return "Registro nao encontrado!";
        }
        return result.getNameEdge();
    }

    @Override
    public String deleteEdge(int idEdge) throws TException {
        System.out.println("Removendo dados...");

        DadosEdge result = null;
        try {
            result = dbEdge.searchEdge(idEdge);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result == null) {
            return "Registro inexistente";
        } else {
            try {
                dbEdge.removeEdge(idEdge);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Registro removido!";
        }
    }

    @Override
    public String createEdge(Edge a) throws TException {
        DadosEdge result = null;

        DadosVertex v1 = null;
        DadosVertex v2 = null;

        try {
            result = dbEdge.searchEdge(a.idEdge);

            v1 = dbVertex.searchVertex(a.idVertex1);
            v2 = dbVertex.searchVertex(a.idVertex2);

        } catch (IOException ex) {
            Logger.getLogger(MethodsHandle.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        if (result == null) {
            if (v1 == null || v2 == null) {
                return "Não é possivel adicionar, algum dos vértice inexistentes";
            } else {
                v1.setEd(a.idEdge);
                v2.setEd(a.idEdge);

                try {
                    dbEdge.insertEdge(new DadosEdge(a.nameEdge, a.idEdge, a.weight, a.description, a.flagDirected, a.idVertex1, a.idVertex2, 1));
                    return "Aresta " + a.nameEdge + " Inserida";

                } catch (IOException ex) {
                    return "Erro na inserção"
                            + "Erro: " + ex.getMessage();
                }
            }

        } else {
            return "Aresta já presente no banco";
        }
    }

    @Override
    public String listVertexForEdge(int idEdge) throws TException {
        DadosEdge result = null;
        try {
            result = dbEdge.searchEdge(idEdge);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String a = readNameVertex(result.getIdVertex1());
        String b = readNameVertex(result.getIdVertex2());

        return "Vértices: [" + a + " - " + b + "]";
    }

    @Override
    public String listEdgeForVertex(int idVertex) throws TException {
        DadosVertex result = null;
        try {
            result = dbVertex.searchVertex(idVertex);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String textFinal = null;
        DadosEdge e1 = null;

        for (int i = 0; i < result.getEd().size(); i++) {
            try {
                e1 = dbEdge.searchEdge(i);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            textFinal = textFinal + e1.getNameEdge() + " - ";
        }
        return "Arestas: [" + textFinal + "]";
    }

    @Override
    public String listVertexForVertex(int idVertex) throws TException {

        DadosVertex result = null;
        DadosEdge ed = null;
        DadosEdge ed2 = null;

        DadosVertex v1 = null;
        DadosVertex v2 = null;
        String textFinal = null;
        try {
            result = dbVertex.searchVertex(idVertex);

            for (int i = 0; i < result.getEd().size(); i++) {
                ed = dbEdge.searchEdge(i);
                v1 = dbVertex.searchVertex(ed.getIdVertex1());

                textFinal = textFinal + v1.getNameVertex() + " - ";
            }

        } catch (IOException ex) {
            Logger.getLogger(MethodsHandle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Vértices:[" + textFinal + "]";
    }

    @Override
    public String updateVertex(int idVertex, String color, String description, double peso) throws TException {
        DadosVertex result = null;
        List l = new ArrayList<Integer>();

        try {
            result = dbVertex.searchVertex(idVertex);

        } catch (IOException ex) {
            Logger.getLogger(MethodsHandle.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        if (result == null) {
            return "Vertice não encontrada";
        } else {
            try {
                dbVertex.insertVertex(new DadosVertex(result.getNameVertex(), result.getIdVertex(), color, description, peso, result.getVersion() + 1, l));
                return "Vertice " + result.getNameVertex() + " atualizado";

            } catch (IOException ex) {
                return "Erro na atualização"
                        + "Erro: " + ex.getMessage();
            }
        }
    }

    @Override
    public String updateEdge(int idEdge, String nameEdge, String description, double peso, boolean flagDirected) throws TException {
        DadosEdge result = null;
        try {
            result = dbEdge.searchEdge(idEdge);

        } catch (IOException ex) {
            Logger.getLogger(MethodsHandle.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        if (result == null) {
            return "Aresta não encontrada";
        } else {
            try {
                dbEdge.insertEdge(new DadosEdge(nameEdge, result.getIdEdge(), peso, description, flagDirected, result.getIdVertex1(), result.getIdVertex2(), result.getVersion() + 1));
                return "Aresta " + nameEdge + " atualizada";

            } catch (IOException ex) {
                return "Erro na atualização"
                        + "Erro: " + ex.getMessage();
            }
        }
    }

    // Atributos usados na funcao encontrarMenorCaminho
    // Lista que guarda os vertices pertencentes ao menor caminho encontrado
    List<DadosVertex> menorCaminho = new ArrayList<DadosVertex>();

    // Variavel que recebe os vertices pertencentes ao menor caminho
    DadosVertex verticeCaminho;

    // Variavel que guarda o vertice que esta sendo visitado
    Vertex VerticeAtual = new Vertex();

    // Variavel que marca o vizinho do vertice atualmente visitado
    //Vertex vizinho = new Vertex();
    DadosVertex verticeVizinho;

    // Lista dos vertices que ainda nao foram visitados
    List<DadosVertex> naoVisitados = new ArrayList<DadosVertex>();

    // Algoritmo de Dijkstra
    @Override
    public List<String> dijkstra(Vertex v1, Vertex v2) throws TException {

        int idEdgeTemp;
        DadosEdge cadaEdge = null;
        DadosVertex verticeDestino = null;
        DadosVertex vAtual = null;

        DadosVertex vertice1 = new DadosVertex(v1.nameVertex, v1.idVertex, v1.color, v1.description, v1.weight, v1.version, v1.ed);
        menorCaminho.add(vertice1);

        try {
            for (int i = 0; i < dbVertex.ShowDataVertex().size(); i++) {
                DadosVertex result = null;

                String a = dbVertex.ShowDataVertex().get(i);
                int id = Integer.parseInt(a);

                result = dbVertex.searchVertex(id);

                if (result.getIdVertex() == (v1.getIdVertex())) {

                    result.setDistance(0);

                } else {

                    result.setDistance(999);
                }
                // Insere o vertice na lista de vertices nao visitados
                //String nVisitado = Integer.toString(result.getIdVertex());

                naoVisitados.add(result);
            }
        } catch (IOException ex) {
            Logger.getLogger(MethodsHandle.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Collections.sort(naoVisitados);
        for (int i = 0; i < naoVisitados.size(); i++) {
            System.out.println(naoVisitados.get(i).getNameVertex());
        }
        // O algoritmo continua ate que todos os vertices sejam visitados
        while (!naoVisitados.isEmpty()) {

            // Toma-se sempre o vertice com menor distancia, que eh o primeiro da lista
            vAtual = naoVisitados.get(0);
            System.out.println("Pegou esse vertice:  " + vAtual.getNameVertex());

            //		 * Para cada vizinho (cada aresta), calcula-se a sua possivel
            //		 * distancia, somando a distancia do vertice atual com a da aresta
            //		 * correspondente. Se essa distancia for menor que a distancia do
            //		 * vizinho, esta eh atualizada.
            for (int i = 0; i < vAtual.getEd().size(); i++) {

                idEdgeTemp = vAtual.getEd().get(i);

                try {
                    cadaEdge = dbEdge.searchEdge(idEdgeTemp);
                    verticeVizinho = dbVertex.searchVertex(cadaEdge.getIdEdge());
                } catch (IOException ex) {
                    Logger.getLogger(MethodsHandle.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("Olhando a aresta vizinha de " + vAtual.getNameVertex() + ": "
                        + verticeVizinho.getNameVertex());

                if (!verticeVizinho.verificarVisita()) {

                    // Comparando a distância do vizinho com a possível
                    // distância
                    if (verticeVizinho.getDistance() > (vAtual.getDistance() + cadaEdge.getWeight())) {
                        verticeVizinho.setDistance(vAtual.getDistance()
                                + cadaEdge.getWeight());
                        verticeVizinho.setPai(vAtual);

                        //			 * Se o vizinho eh o vertice procurado, e foi feita uma
                        //			 * mudanca na distancia, a lista com o menor caminho
                        //			 * anterior eh apagada, pois existe um caminho menor
                        //			 * vertices pais, ateh o vertice origem.
                        try {
                            verticeDestino = dbVertex.searchVertex(v2.idVertex);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        if (verticeVizinho == verticeDestino) {
                            menorCaminho.clear();
                            verticeCaminho = verticeVizinho;
                            menorCaminho.add(verticeVizinho);

                            while (verticeCaminho.getPai() != null) {

                                menorCaminho.add(verticeCaminho.getPai());
                                verticeCaminho = verticeCaminho.getPai();
                            }
<<<<<<< HEAD
                            // Ordena a lista do menor caminho, para que ele
                            // seja exibido da origem ao destino.
                            //Collections.sort(menorCaminho);
=======
>>>>>>> 75c65e7f59ef2476dfd985969935908ed06aaa8d
                        }
                    }
                }
            }
            // Marca o vertice atual como visitado e o retira da lista de nao
            // visitados
            vAtual.Visitado();
            naoVisitados.remove(vAtual);
<<<<<<< HEAD

            //Ordena a lista, para que o vertice com menor distancia fique na
            // primeira posicao
            //Collections.sort(naoVisitados);
            System.out.println("Nao foram visitados ainda:" + naoVisitados);
        }
=======
            
            System.out.println("Nao foram visitados ainda:" + naoVisitados);
        }

>>>>>>> 75c65e7f59ef2476dfd985969935908ed06aaa8d
        List<String> s = new ArrayList<String>();
        
        for (int i = 0; i < menorCaminho.size(); i++) {
            s.add(menorCaminho.get(i).getNameVertex());
            //System.out.println("Menor caminho ->" + menorCaminho.get(i).getNameVertex());
        }
        return s;
    }
}
