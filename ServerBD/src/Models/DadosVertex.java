/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import ThriftGenerate.Edge;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dcosta
 */
public class DadosVertex implements Serializable {

    public void setNameVertex(String nameVertex) {
        this.nameVertex = nameVertex;
    }

    public void setIdVertex(int idVertex) {
        this.idVertex = idVertex;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public void setEd(List<Integer> ed) {
        this.ed = ed;
    }

    private String nameVertex;
    private int idVertex;
    private String color;
    private String description;
    private double weight;
    private double distance;
    private int version;
    private boolean visitado = false;
    private DadosVertex pai;
    private List<Integer> ed = new ArrayList<Integer>();

    public DadosVertex getPai() {
        return pai;
    }

    public void setPai(DadosVertex pai) {
        this.pai = pai;
    }

    public List<Integer> getEd() {
        return ed;
    }

    public void setEd(int valor) {
        ed.add(valor);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DadosVertex{" + "nameVertex=" + nameVertex + ", idVertex=" + idVertex + ", color=" + color + ", description=" + description + ", weight=" + weight + ", version=" + version + "Qtd de Arestas "+ed.size()+'}';
    }

    public DadosVertex(String nameVertex, int idVertex, String color, String description, double weight, int version, List<Integer> ed) {
        this.nameVertex = nameVertex;
        this.idVertex = idVertex;
        this.color = color;
        this.description = description;
        this.weight = weight;
        this.version = version;
        this.ed = ed;
    }

    public boolean verificarVisita() {

        return visitado;
    }

    public void Visitado() {
        this.visitado = true;
    }

    public int getVersion() {
        return version;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public String getNameVertex() {
        return nameVertex;
    }

    public int getIdVertex() {
        return idVertex;
    }
}
