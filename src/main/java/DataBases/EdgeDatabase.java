/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import ThriftGenerate.Edge;
/**
 *
 * @author dcosta
 */
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;

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

    public synchronized void insertEdge(Edge data) throws IOException {
        String id = Integer.toString(data.idEdge);
        hashtable.put(id, data);
        recman.commit();
    }

    public synchronized Edge searchEdge(int idEdge) throws IOException {
        String id = Integer.toString(idEdge);
        return (Edge) hashtable.get(id);
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
