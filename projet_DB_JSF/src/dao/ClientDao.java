package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import metier.Client;

public class ClientDao {
	private static ClientDao INSTANCE;
	private DataSource dataSource;
	private String jndiName="java:comp/env/jdbc/client_tracker_h2";
	
	private ClientDao(){
		try {
			dataSource = getDataSource();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ClientDao getInstance() {
		if(INSTANCE ==null)
			INSTANCE = new ClientDao();
		return INSTANCE;
	}
	
	public DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	public List<Client> getClients(){
		ArrayList<Client> listClients = new ArrayList<>();
		
		Connection connection = null;
		Statement stat = null;
		ResultSet result = null;
		
		try {
			connection = getConnection();

			String sql = "select * from client;";

			stat = connection.createStatement();

			result = stat.executeQuery(sql);

			while (result.next()) {
				int id = result.getInt("id");
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				String email = result.getString("email");
				Client client = new Client(id, nom, prenom, email);
				listClients.add(client);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(connection, stat);
		}
		return listClients;
	}

	private void close(Connection connection, Statement stat) {
		try {
			connection.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}
	
	public void addClient(Client client){
		Connection connection = null;
		PreparedStatement stat = null;
		
		try {
			connection = getConnection();
			String sql = "INSERT INTO client (nom, prenom, email) values (?,?,?)";
			stat = connection.prepareStatement(sql);
			
			stat.setString(1, client.getNom());
			stat.setString(2, client.getPrenom());
			stat.setString(3, client.getEmail());
			
			stat.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(connection, stat);
		}
	}
	
	public void deleteClient(Client client){
		Connection connection = null;
		PreparedStatement stat = null;
		
		try {
			connection = getConnection();
			String sql = "delete from client where id = ? ";
            stat = connection.prepareStatement(sql);
            
            // renseinger parametres 
            stat.setInt(1, client.getId());
            
            //executer SQL
            stat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection, stat);
		}
	}
	
	public void updateClient(Client client){
		Connection connection = null;
		PreparedStatement stat = null;
		
		try {
			connection = getConnection();
			String sql = "UPDATE client set nom = ?, prenom = ?, email = ? where id = ? ";
			stat = connection.prepareStatement(sql);
			
			stat.setString(1, client.getNom());
			stat.setString(2, client.getPrenom());
			stat.setString(3, client.getEmail());
			stat.setInt(4, client.getId());
			
			stat.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(connection, stat);
		}
	}
}
