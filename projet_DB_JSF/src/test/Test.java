package test;

import dao.ClientDao;

public class Test {

	public static void main(String[] args) {
		ClientDao s = ClientDao.getInstance();
		System.out.println(s);
	}

}
