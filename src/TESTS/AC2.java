package TESTS;

import PD.Store;
import PD.Cashier;
import PD.Person;

public class AC2
{
	private Store store;
	private Cashier worker1, worker2, worker3;
	private Person person1, person2, person3;
	
	public AC2()
	{
		// Store initialized with name and number
		store = new Store("Chez Keza", "0001");
		// Person initialized with name, address, Number
		person1 = new Person("Nyiramariza", "340 Street St.", "OKC", "OK", "234234", "555-555-5555", "444-44-4444", worker1);
		worker1 = new Cashier("7777", person1, "*******");
		person2 = new Person("Maziyateke", "340 Road Rd.", "OKC", "OK", "234234", "555-555-5556", "444-44-4445", worker2);
		worker2 = new Cashier("7778", person2, "*******");
		person3 = new Person("Ingabire", "340 Boulevard Bd.", "OKC", "OK", "234234", "555-555-5557", "444-44-4446", worker3);
		worker3 = new Cashier("7779", person3, "*******");
		
		store.addCashier(worker1);
		store.addCashier(worker2);
		store.addCashier(worker3);
	}
	
	public void runTest()
	{	
		System.out.println("AC2:");
		System.out.println(worker1.toString());
		System.out.println(worker2.toString());
		System.out.println(worker3.toString());
		System.out.println("End of AC2 \n");
	}

}
