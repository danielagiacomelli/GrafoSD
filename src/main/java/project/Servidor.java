package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import Atomix.GetQuery;
import Atomix.MapStateMachine;
import Atomix.PutCommand;
import ThriftGenerate.Methods;
import ThriftGenerate.Methods.Processor;
import io.atomix.catalyst.transport.Address;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

/**
 *
 * @author dcosta
 */
public class Servidor {
	public static MethodsHandle handler;
    public static MapeamentoServidor mapeamento;
    public static ArrayList<MapeamentoServidor> listaMapeamento = new ArrayList<MapeamentoServidor>();
    public static CopycatServer server;
    public static CopycatClient.Builder clientBuilder;

    public static void main(String[] args) {
//		handler = new Handler();
//		processor = new GrafoService.Processor(handler);
    	
    	for(int i = 0; i < args.length; i++){
			System.out.println("Variável args: " + args[i]);
			listaMapeamento = leArquivos(args[i]);
		}
    	
    	Collection<Address> clusterServer = null;
    	
//    		clusterServer = Arrays.asList(
//    				new Address("127.0.0.1", 5000),
//    				new Address("127.0.0.1", 5001),
//    				new Address("127.0.0.1", 5002)
//    		);
    		
    		Iterator<MapeamentoServidor> iter = listaMapeamento.iterator();
    		while(iter.hasNext()){
    			clusterServer = Arrays.asList(
        				new Address("127.0.0.1", iter.next().getPortaClient()),
        				new Address("127.0.0.1", iter.next().getPortaClient()),
        				new Address("127.0.0.1", iter.next().getPortaClient())
        		);
    		}
    		
		
		
		for(MapeamentoServidor map: listaMapeamento){
			mapeamento = map;
				//descobrir outros nos deste cluster.
				//crie um cluster copy-catd
				// fazer um for PARA ITERAR para achar todos os servidores
				// Se ele form o primeiro controi cluster
				
				Address address = new Address("localhost", mapeamento.getPortaClient());
				System.out.println("Porta servidor: " + mapeamento.getPortaClient());
				
				CopycatServer.Builder builder = CopycatServer.builder(address);
				
				builder.withStateMachine(MapStateMachine::new);
				
//					builder.withTransport(NettyTransport.builder()
//							.withThreads(4)
//							.build());
				
				builder.withStorage(Storage.builder()
						//.withDirectory(new File("logs"))
						.withStorageLevel(StorageLevel.MEMORY)
						.build());
				
	            
				
				server = builder.build();
				
				//server.serializer().register(PutCommand.class);
				
			if(listaMapeamento.get(0) == mapeamento){
				try {
					server.bootstrap().join();
					break;
				} catch (Exception x) {
					System.out.println("Servidor com porta em uso");
					//x.printStackTrace();
				}
				
			}else{
				try {
					//Collection<Address> cluster = Collections.singleton(new Address("127.0.0.1", mapeamento.getPortaClient()));
					server.join(clusterServer).join();
					break;
				} catch (Exception x) {
					System.out.println("Servidor com porta em uso");
					//x.printStackTrace();
				}
			}
			
		}
		
		
//
//        mapeamento = new MapeamentoServidor();
//
//        mapeamento.setNomeServidor(0);
//        mapeamento.setPorta(9070);
//
//        try {
//
//            //descobrir outros nos deste cluster.
//            //crie um cluster copy-cat
//            // fazer um for PARA ITERAR para achar todos os servidores
//            // Se ele form o primeiro controi cluster
//            Address address = new Address("127.0.0.1", mapeamento.getPorta());
//            System.out.println("Porta servidor: " + mapeamento.getPorta());
//
//            CopycatServer.Builder builder = CopycatServer.builder(address);
//
//            builder.withStateMachine(MapStateMachine::new);
//
//            builder.withTransport(NettyTransport.builder()
//                    .withThreads(4)
//                    .build());
//
//            builder.withStorage(Storage.builder()
//                    .withDirectory(new File("logs"))
//                    .withStorageLevel(StorageLevel.DISK)
//                    .build());
//
//            server = builder.build();
//
//            server.serializer().register(CommandCriaVertice.class);
//
//            server.bootstrap().join();
//            CompletableFuture<CopycatServer> future = server.bootstrap();
//            future.join();
//
//            Collection<Address> clusterServer = Arrays.asList(
//                    new Address("127.0.0.1", 9091),
//                    new Address("127.0.0.1", 9092)
//            );
//
//            server.join(clusterServer).join();
//
//            //else ... demais servidores join no cluster
////			Iterator<MapeamentoServidor> iter = listaMapeamento.iterator();
////			iter.next();
////			while(iter.hasNext()){
////				Collection<Address> cluster = Collections.singleton(new Address("127.0.0.1", iter.next().getPorta()));
////				server.join(cluster).join();
////				
////			}
//            // Iterator<MapeamentoServidor> iterClient = listaMapeamento.iterator();
//            // todos executam
//            //veriavel clientBuilder global
            clientBuilder = CopycatClient.builder(new Address("localhost",mapeamento.getPortaClient()));
//            clientBuilder.withTransport(NettyTransport.builder()
//                    .withThreads(2)
//                    .build());

            CopycatClient client = clientBuilder.build();

            client.serializer().register(PutCommand.class);
//            client.serializer().register(GetQuery.class);

//            Collection<Address> clusterClient;
//            while (iterClient.hasNext()) {
//                System.out.println("Starting server 1...");
//                clusterClient = Arrays.asList(
//                        //endereço dos tres servidores de um cluster
//                        new Address("127.0.0.1", iterClient.next().getPorta())
//                );
//
//                client.connect(clusterClient).join();
//                //CompletableFuture<CopycatClient> futureClient
//                //futureClient.join();
//            }
//            Collection<Address> clusterClient = Arrays.asList(
//                    new Address("127.0.0.1", mapeamento.getPortaClient())
//            );

            CompletableFuture<CopycatClient> futureClient = client.connect();
            futureClient.join();
//
//            //CommandCriaVertice command = new CommandCriaVertice(vertex);
////            CompletableFuture<Vertex> futureCommand;
////            futureCommand = client.submit(new CommandCriaVertice(vertex));
////            Object result = future.join();
            handler = new MethodsHandle(client);
            
            Methods.Processor processor = new Methods.Processor(handler);
			try{
	            Runnable simple = new Runnable() {
	                public void run() {
	                    simple(processor, mapeamento);
	                }
	            };
	            new Thread(simple).start();
	            
	            System.out.println("Servidor startado!!");
	
	        } catch (Exception x) {
	            x.printStackTrace();
	        }

    }

	public static void simple(Processor<MethodsHandle> processor, MapeamentoServidor mapeamento) {
		try {

			TServerTransport serverTransport = new TServerSocket(mapeamento.getPorta());
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

			System.out.println("Starting server "+mapeamento.getNomeServidor()+" in port " + mapeamento.getPorta());
			server.serve();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<MapeamentoServidor> leArquivos(String path) {
		return new LeArquivos().leArquivos(path);
	}

	public static Methods.Client buscaServidor(int idVertice) {
		int aux = idVertice % mapeamento.getQtdServidores();

		if (aux == mapeamento.getNomeServidor()) {
			return null;
		} else {

			for (MapeamentoServidor map : listaMapeamento) {
				if (map.getNomeServidor() == aux) {
					Methods.Client client = simpleClientServidor(map.getPorta());
					
					System.out.println("Entrou aqui");
					
					return client;
					
					
				}
			}
			return null;
		}
	}

	public static Methods.Client simpleClientServidor(int porta) {
		TTransport transport;
		try {
			transport = new TSocket("localhost", porta);
			transport.open();
			
			TProtocol protocol = new  TBinaryProtocol(transport);
			Methods.Client client = new Methods.Client(protocol);
			return client;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}