package Db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

public class ClientDbUtil {
	private static ClientDbUtil INSTANCE;
	private DataSource dataSource;
	private String jndiName="java:comp/env/jbbc/client_tracker_h2";
	
	private ClientDbUtil() {
		super();
	}
	
	public static ClientDbUtil getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ClientDbUtil();
		return INSTANCE;
	}
	
	public DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	
	
	
	

}
