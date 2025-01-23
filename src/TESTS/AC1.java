package TESTS;

import PD.Item;
import PD.Price;
import PD.Store;
import PD.TaxCategory;
import PD.UPC;
import PD.TaxRate;

public class AC1 {

// Store with items and their uPC, and price

private Store store = new Store("Isoko", "0001");

// Items initialized with item number and Description
private Item item1 = new Item("001", "Ibigori");
private Item item2 = new Item("002", "Inyama");
private Item item3 = new Item("003", "Ubugari");

private UPC upc1;
private UPC upc2;
private UPC upc3;

private Price price1;
private Price price2;
private Price price3;

private TaxCategory tC1;
private TaxCategory tC2;
private TaxCategory tC3;

private TaxRate vatRate;
private TaxRate exemptRate;
private TaxRate luxuryRate;


public AC1() {
	
	// UPC initialized with upc and item
   this.upc1 = new UPC("0000000000010110", this.item1);
   this.upc2 = new UPC("0000000000010111", this.item2);
   this.upc3 = new UPC("0000000000011000", this.item3);
   
   // Price initialized with amount and effective date
   this.price1 = new Price("48.4", "9/16/2018");
   this.price2 = new Price("136", "10/14/2019");
   this.price3 = new Price("144", "4/4/1998");
   
   // Tax rates initialization with specific rates and effective dates
   vatRate = new TaxRate("1/1/2010", "0.10");  // 10% VAT
   exemptRate = new TaxRate("1/1/2010", "0.00");  // 0% Tax (Exempt)
   luxuryRate = new TaxRate("1/1/2010", "0.20");  // 20% Luxury Tax

   // Assign tax rates to tax categories
   this.tC1 = new TaxCategory("VAT");
   this.tC1.addTaxRate(vatRate);  // Assign 10% VAT rate to VAT category

   this.tC2 = new TaxCategory("Exempt");
   this.tC2.addTaxRate(exemptRate);  // Assign 0% tax rate to Exempt category

   this.tC3 = new TaxCategory("Luxury");
   this.tC3.addTaxRate(luxuryRate);  // Assign 20% tax rate to Luxury category

   // Set tax categories for items
   this.item1.setTaxCategory(this.tC1);  // Ibigori
   this.item2.setTaxCategory(this.tC2);  // Inyama
   this.item3.setTaxCategory(this.tC3);  // Ubugari
   
   // Set item1
   this.item1.addPrice(this.price1);
   this.item1.setTaxCategory(this.tC1);
   this.item1.addUPC(this.upc1);
   this.price1.setItem(this.item1);
   
   //Set item2
   this.item2.addPrice(this.price2);
   this.item2.setTaxCategory(this.tC2);
   this.item2.addUPC(this.upc2);
   this.price2.setItem(this.item2);
   
   // Set item3
   this.item3.addPrice(this.price3);
   this.item3.setTaxCategory(this.tC3);
   this.item3.addUPC(this.upc3);
   this.price3.setItem(this.item3);
   
   // add items to store
   this.store.addItem(this.item1);
   this.store.addItem(this.item2);
   this.store.addItem(this.item3);
}

public void runTest() {
   
   System.out.print("AC1: \n");
   System.out.println("Store Name: " + this.store.getName());
   System.out.print("Items: \n");
   System.out.println(this.item1.toString());
   System.out.println(this.item2.toString());
   System.out.println(this.item3.toString());
   System.out.print("End of AC1\n");
   System.out.print("\n");
}
}
