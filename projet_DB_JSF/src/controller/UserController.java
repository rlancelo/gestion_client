package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.logging.Level;
import java.util.logging.Logger;

import metier.Client;
import service.ClientService;

@ManagedBean(name="userController")
@SessionScoped
public class UserController {
	
	ClientService clientService = ClientService.getInstance();
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private List<Client> clients = new ArrayList<>();
	Client client = new Client();
	
	public UserController() {
		clients = clientService.getClients(); 
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public String addClient(Client client){

		clientService.addClient(client);
		return "index?faces-redirect=true";
	}
	
	public String loanClients(){
		clients = clientService.getClients();
		return "index?faces-redirect=true";
	}
	
	public String deleteClient(Client client){
		clientService.deleteClient(client);
		return "index?faces-redirect=true";
		
	}
	
	public String updateClient(Client client){
		clientService.updateClient(client);
		return "index?faces-redirect=true";
	}
	
	public String redirectForUpdateClient(Client client){
		logger.info("loading client: " + client.getId());
		
		try {
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("client", client);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading client id:" + client.getId(), exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		return "update-client";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
