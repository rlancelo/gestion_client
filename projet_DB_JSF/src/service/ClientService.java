package service;

import java.util.List;

import dao.ClientDao;
import metier.Client;

public class ClientService {
	private static ClientService INSTANCE;
	private ClientDao clientDao = ClientDao.getInstance();

	private ClientService() {

	}

	public static ClientService getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ClientService();
		return INSTANCE;
	}

	public List<Client> getClients() {
		return clientDao.getClients();
	}

	public void addClient(Client client) {
		if (!client.getPrenom().isEmpty() && !client.getNom().isEmpty() && !client.getEmail().isEmpty()) {
			clientDao.addClient(client);
		}
	}

	public void deleteClient(Client client) {
		clientDao.deleteClient(client);
	}

	public void updateClient(Client client) {
		if (!client.getPrenom().isEmpty() && !client.getNom().isEmpty() && !client.getEmail().isEmpty()) {
			clientDao.updateClient(client);
		}
	}

}
