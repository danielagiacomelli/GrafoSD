/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import ThriftGenerate.Methods;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author dcosta
 */
public class Connector {

    public Methods.Client Con() throws TTransportException {
        TTransport transport;

        transport = new TSocket("localhost", 8090);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        Methods.Client client = new Methods.Client(protocol);
        
        return client;
    }
}
