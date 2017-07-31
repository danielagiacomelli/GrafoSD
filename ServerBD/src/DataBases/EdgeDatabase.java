/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBases;

/**
 *
 * @author dcosta
 */
import Models.DadosEdge;
import Models.DadosVertex;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class EdgeDatabase {

    private RecordManager recman;
    private HTree hashtable;
    private FastIterator iter;

    public EdgeDatabase() throws IOException {
        recman = RecordManagerFactory.createRecordManager("edgeDB", new Properties());
        long recid = recman.getNamedObject("edge");
        if (recid != 0) {
            System.out.println("Loading DB Edge...");
            hashtable = HTree.load(recman, recid);
        } else {
            System.out.println("Creating new DB Edge...");
            hashtable = HTree.createInstance(recman);
            recman.setNamedObject("edge", hashtable.getRecid());
        }
    }

    public synchronized void insertEdge(DadosEdge data) throws IOException {
        String id = Integer.toString(data.getIdEdge());
        hashtable.put(id, data);
        recman.commit();
    }

    public synchronized DadosEdge searchEdge(int idEdge) throws IOException {
        String id = Integer.toString(idEdge);
        return (DadosEdge) hashtable.get(id);
    }

    public synchronized void removeEdge(int idEdge) throws IOException {
        String id = Integer.toString(idEdge);
        hashtable.remove(id);
        recman.commit();
    }

    public ArrayList<String> ShowDataEdge() throws IOException {
        iter = hashtable.keys();
        ArrayList<String> list = new ArrayList<String>();

        String id = (String) iter.next();

        while (id != null) {
            list.add(id);
            id = (String) iter.next();
        }
        return list;
    }
}
