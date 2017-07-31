/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

/**
 *
 * @author dcosta
 */
public class MapeamentoServidor {

    private int nomeServidor;
    private int porta;
    private String ip;
    private String pathBD;
    private String pathBDAresta;
    private int qtdServidores;

    public String getPathBDAresta() {
        return pathBDAresta;
    }

    public void setPathBDAresta(String pathBDAresta) {
        this.pathBDAresta = pathBDAresta;
    }

    public int getQtdServidores() {
        return qtdServidores;
    }

    public void setQtdServidores(int qtdServidores) {
        this.qtdServidores = qtdServidores;
    }

    public int getNomeServidor() {
        return nomeServidor;
    }

    public void setNomeServidor(int nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPathBD() {
        return pathBD;
    }

    public void setPathBD(String pathBD) {
        this.pathBD = pathBD;
    }
}
