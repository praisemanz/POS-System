
package TESTS;

import DM.DataManagement;
import PD.Store;

public class AC5 {
   Store store = new Store();
   DataManagement dataManager = new DataManagement();

   public AC5() {
      this.store = DataManagement.loadStore(this.store);
   }

   public void runTest() {
	   System.out.println("AC5:");
		System.out.println(store.toString());
		System.out.println("End of AC5 \n");
   }
}

