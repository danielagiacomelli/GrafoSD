/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import ThriftGenerate.Vertex;
import java.io.Serializable;

/**
 *
 * @author dcosta
 */
public class DadosEdge implements Serializable {

    private String nameEdge;
    private int idEdge;
    private double weight;
    private String description;
    private int flagDirected;
    private int idVertex1;
    private int idVertex2;
    private int versao;

    public DadosEdge(String nameEdge, int idEdge, double weight, String description, int flagDirected, int idVertex1, int idVertex2, int versao) {
                this.nameEdge = nameEdge;
        this.idEdge = idEdge;
        this.weight = weight;
        this.description = description;
        this.flagDirected = flagDirected;
        this.idVertex1 = idVertex1;
        this.idVertex2 = idVertex2;
        this.versao = versao;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "DadosEdge{" + "nameEdge=" + nameEdge + ", idEdge=" + idEdge + ", weight=" + weight + ", description=" + description + ", flagDirected=" + flagDirected + ", idVertex1=" + idVertex1 + ", idVertex2=" + idVertex2 + ", versao=" + versao + '}';
    }

    public int getIdVertex1() {
        return idVertex1;
    }

    public int getIdVertex2() {
        return idVertex2;
    }

    public String getNameEdge() {
        return nameEdge;
    }

    public int getIdEdge() {
        return idEdge;
    }

    public int getVersion() {
        return versao;
    }

}
