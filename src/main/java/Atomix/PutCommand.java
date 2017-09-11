package Atomix;

import ThriftGenerate.Vertex;
import io.atomix.copycat.Command;

public class PutCommand implements Command<Vertex> {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Object key;
	private final Object value;


    public PutCommand(Object key, Object value) {
    	System.out.println("*******PutCommand*******");
    	this.key = key;
	    this.value = value;
    }

    public Object key() {
        return key;
      }

      public Object value() {
        return value;
      }

}