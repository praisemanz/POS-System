package UI;

import java.io.FileNotFoundException;


import java.io.IOException;

import DM.DataManagement;
import PD.Store;
import TESTS.AC5;


public class StartClass
{	
	public static void main(String[] args)
	{	
		Store myStore = new Store();
		DataManagement.loadStore(myStore);
		POSFrame storeFrame = new POSFrame(myStore);
		storeFrame.run(myStore);
	}
}