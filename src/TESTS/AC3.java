package TESTS;


import PD.Register;
import PD.CashDrawer;
import PD.Store;
import java.math.BigDecimal;

public class AC3
{
	private Store store;
	private Register reg1, reg2;
	private CashDrawer cD1 = new CashDrawer(), cD2 = new CashDrawer();
	
	public AC3()
	{
		store = new Store("Chez Barore", "0001");
		cD1.addCash(new BigDecimal("500"));
		cD2.addCash(new BigDecimal("1000"));
		reg1 = new Register("01", cD1);
		reg2 = new Register("02", cD2);
		
		store.addRegister(reg1);
		store.addRegister(reg2);
	}
	
	public void runTest()
	{	
		System.out.println("AC3");
		System.out.println(reg1.toString());
		System.out.println(reg2.toString());
		System.out.println("End of AC3 \n");

	}
}
