package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ThriftGenerate.Vertex;


public class LeArquivos {
	
	public static List<String> readLines(File file) throws Exception {
	      if (!file.exists()) {
	          return new ArrayList<String>();
	      }
	      BufferedReader reader = new BufferedReader(new FileReader(file));
	      List<String> results = new ArrayList<String>();
	      String line = reader.readLine();
	      while (line != null) {
	          results.add(line);
	          line = reader.readLine();
	      }
	      return results;
	  }
	
	public long getTamanhoArquivo(String path){
		  long qtdLinhas = 0;
		  BufferedReader br;
		  
		  try {
			br = new BufferedReader(new FileReader(path));
			qtdLinhas = br.lines().count();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		  
		  return qtdLinhas;
	  }
	
//	 public List<Vertex> leVertices(String pathBD) {
//			List<Vertex> listaVertice = new ArrayList<Vertex>();
//			Vertex vertice = new Vertex();
//
//			BufferedReader br = null;
//			String linha = "";
//			try {
//			
//			    br = new BufferedReader(new FileReader(pathBD));
//			    while ((linha = br.readLine()) != null) {
//			
//			        String[] registro = linha.split(";");
//			
//			        vertice.setNome(Integer.parseInt(registro[0].replace(",", ".")));
//			        vertice.setCor(Integer.parseInt(registro[1].replace(",", ".")));
//			        vertice.setDescricao(registro[2]);
//			        vertice.setPeso(Double.parseDouble(registro[3]));
//			        
//					listaVertice.add(vertice);
//					vertice = new Vertice();
//			            
//			    }
//			    
//			    
//			
//		    } catch (FileNotFoundException e) {
//		        e.printStackTrace();
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    } finally {
//		        if (br != null) {
//		            try {
//		                br.close();
//		            } catch (final IOException e) {
//		                e.printStackTrace();
//		            }
//		        }
//		        return listaVertice;
//		    }
//		  }
	 
	 
//	 public List<Aresta> carregaArestas(String pathBD) {
//		List<Aresta> listaVertice = new ArrayList<Aresta>();
//		Aresta aresta = new Aresta();
//
//		BufferedReader br = null;
//		String linha = "";
//		try {
//		
//		    br = new BufferedReader(new FileReader(pathBD));
//		    while ((linha = br.readLine()) != null) {
//		
//		        String[] registro = linha.split(";");
//		        
//		        aresta.setNomeVertice1(Integer.parseInt(registro[0].replace(",", ".")));
//		        aresta.setNomeVertice2(Integer.parseInt(registro[1].replace(",", ".")));
//		        aresta.setPeso(Double.parseDouble(registro[2]));
//		        aresta.setFlag(Integer.parseInt(registro[3].replace(",", ".")));
//		        aresta.setDescricao(registro[4]);
//		        
//		        
////		        System.out.println(aresta.getDescricao());
////				System.out.println("***************");
//				listaVertice.add(aresta);
//				aresta = new Aresta();
//		    }
//			
//		    } catch (FileNotFoundException e) {
//		        e.printStackTrace();
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    } finally {
//		        if (br != null) {
//		            try {
//		                br.close();
//		                return listaVertice;
//		            } catch (IOException e) {
//		                e.printStackTrace();
//		            }
//		        }
//		    }
//			return null;
//	}
	 
//	 public void criaArquivoVetice(List<Vertice> lista, String pathBD){
//		  
//		  try {
//			
//		      File arquivo = new File(pathBD);
//		      
//		      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(arquivo, false));
//		      
//		      for(Vertice v : lista){
//		    	  fileWriter.write(v.getNome() + ";");
//		    	  fileWriter.write(v.getCor()+ ";");
//		    	  fileWriter.write(v.getDescricao()+ ";");
//		    	  fileWriter.write(String.valueOf(v.getPeso()));
//		    	  fileWriter.newLine();
//		      }
//				
//		      fileWriter.close();
//				
//		      System.out.println("Arquivo criado com sucesso");
//				
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		  
//	  }
	 
//	 public void criaArquivoAresta(List<Aresta> lista, String pathBDAresta){
//		  
//		  try {
//			
//		      File arquivo = new File(pathBDAresta);
//		      
//		      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(arquivo, false));
//		      
//		      for(Aresta a : lista){
//		    	  fileWriter.write(a.getNomeVertice1() + ";");
//		    	  fileWriter.write(a.getNomeVertice2() + ";");
//		    	  fileWriter.write(String.valueOf(a.getPeso()) + ";");
//		    	  fileWriter.write(a.getFlag()+ ";");
//		    	  fileWriter.write(a.getDescricao());
//		    	  fileWriter.newLine();
//		      }
//				
//		      fileWriter.close();
//				
//		      System.out.println("Arquivo criado com sucesso");
//				
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		  
//	  }
	 
	 public ArrayList<MapeamentoServidor> leArquivos(String path) {
		 
		 	MapeamentoServidor ms = new MapeamentoServidor();
		 	ArrayList<MapeamentoServidor> listaMs = new ArrayList<MapeamentoServidor>();
			BufferedReader br = null;
			String linha = "";
			try {
			
			    br = new BufferedReader(new FileReader(path));
			    while ((linha = br.readLine()) != null) {
			
			        String[] registro = linha.split(";");
			        ms.setQtdServidores(Integer.parseInt(registro[0]));
			        ms.setCluster(Integer.parseInt(registro[1]));
			        ms.setNomeServidor(Integer.parseInt(registro[2]));
			        ms.setPorta(Integer.parseInt(registro[3]));
			        ms.setPortaClient(Integer.parseInt(registro[4]));
			        ms.setIp(registro[5]);
			        ms.setPathBD(registro[6]);
			        ms.setPathBDAresta(registro[7]);
			        
			        System.out.println(registro[0]);
			        System.out.println(registro[1]);
			        System.out.println(registro[2]);
			        System.out.println(registro[3]);
			        System.out.println(registro[4]);
			        System.out.println(registro[5]);
			        System.out.println(registro[6]);
			        System.out.println(registro[7]);
			        
			        listaMs.add(ms);
			        
			        ms = new MapeamentoServidor();
			        
			    }
			    
			} catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if (br != null) {
		            try {
		                br.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		 
		 return listaMs;
		 
	 }
	 
	 
	 public ArrayList<MapeamentoServidor> leArquivoCliente(String path) {
		 
		 	MapeamentoServidor ms = new MapeamentoServidor();
		 	ArrayList<MapeamentoServidor> listaMs = new ArrayList<MapeamentoServidor>();
			BufferedReader br = null;
			String linha = "";
			try {
			
			    br = new BufferedReader(new FileReader(path));
			    while ((linha = br.readLine()) != null) {
			
			        String[] registro = linha.split(";");
			        ms.setNomeServidor(Integer.parseInt(registro[0]));
			        ms.setPorta(Integer.parseInt(registro[1]));
			        ms.setIp(registro[2]);
			        
			        System.out.println(registro[0]);
			        System.out.println(registro[1]);
			        System.out.println(registro[2]);
			        
			        listaMs.add(ms);
			        
			        ms = new MapeamentoServidor();
			        
			    }
			    
			} catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if (br != null) {
		            try {
		                br.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		 
		 return listaMs;
		 
	 }

}
