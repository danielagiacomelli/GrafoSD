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
        try {
            result = dbVertex.searchVertex(v.idVertex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result == null) {
            try {
                dbVertex.insertVertex(new DadosVertex(v.nameVertex, v.idVertex, v.color, v.description, v.weight, 1));
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

        DadosEdge e1 = null;
        DadosEdge e2 = null;

        try {
            result = dbVertex.searchVertex(idVertex);

            e1 = dbEdge.searchEdge(result.getIdEdge1());
            e2 = dbEdge.searchEdge(result.getIdEdge2());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (result == null) {
            return "Registro inexistente";
        } else {
            try {
                if (e1 != null) {
                    dbEdge.removeEdge(e1.getIdEdge());
                }
                if (e2 != null) {
                    dbEdge.removeEdge(e2.getIdEdge());
                }
                
                dbVertex.removeVertex(idVertex);
            } catch (IOException e) {
                // TODO Auto-generated catch block
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
                try {
                    v1.setIdEdge2(a.idEdge);
                    v2.setIdEdge1(a.idEdge);

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

        return "Aresta '" + result.getNameEdge() + "' possui os vértices: (" + a + "-----" + b + ")";
    }

    @Override
    public String listEdgeForVertex(int idVertex) throws TException {
        DadosVertex result = null;
        try {
            result = dbVertex.searchVertex(idVertex);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String a = readNameEdge(result.getIdEdge1());
        String b = readNameEdge(result.getIdEdge2());

        return "Vértice '" + result.getNameVertex() + "' possui as arestas: (" + a + "-----" + b + ")";
    }

    @Override
    public String listVertexForVertex(int idVertex) throws TException {

        DadosVertex result = null;

        try {
            result = dbVertex.searchVertex(idVertex);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String a = readNameEdge(result.getIdEdge1());
        String b = readNameEdge(result.getIdEdge2());

        return "Arestas: " + a + "-----" + b;
    }

    @Override
    public String updateVertex(int idVertex, String color, String description, double peso) throws TException {
        DadosVertex result = null;
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
                dbVertex.insertVertex(new DadosVertex(result.getNameVertex(), result.getIdVertex(), color, description, peso, result.getVersion() + 1));
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
}
