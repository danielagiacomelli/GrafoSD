/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import ThriftGenerate.Edge;
import ThriftGenerate.Vertex;
import java.io.Serializable;

/**
 *
 * @author dcosta
 */
public class DadosVertex implements Serializable {

    private String nameVertex;
    private int idVertex;
    private String color;
    private String description;
    private double weight;
    private int idEdge1;
    private int idEdge2;

    public void setVersion(int version) {
        this.version = version;
    }
    private int version;

    @Override
    public String toString() {
        return "DadosVertex{" + "nameVertex=" + nameVertex + ", idVertex=" + idVertex + ", color=" + color + ", description=" + description + ", weight=" + weight + ", idEdge1=" + idEdge1 + ", idEdge2=" + idEdge2 + ", version=" + version + '}';
    }

    public DadosVertex(String nameVertex, int idVertex, String color, String description, double weight, int version) {
        this.nameVertex = nameVertex;
        this.idVertex = idVertex;
        this.color = color;
        this.description = description;
        this.weight = weight;
        this.version = version;
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

    public int getIdEdge1() {
        return idEdge1;
    }

    public int getIdEdge2() {
        return idEdge2;
    }

    public void setIdEdge1(int idEdge1) {
        this.idEdge1 = idEdge1;
    }

    public void setIdEdge2(int idEdge2) {
        this.idEdge2 = idEdge2;
    }

    public String getNameVertex() {
        return nameVertex;
    }

    public int getIdVertex() {
        return idVertex;
    }
}
