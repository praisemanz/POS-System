package DM;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import PD.Cash;
import PD.Cashier;
import PD.Check;
import PD.Credit;
import PD.Item;
import PD.Price;
import PD.PromoPrice;
import PD.Register;
import PD.Sale;
import PD.SaleLineItem;
import PD.Session;
import PD.Store;
import PD.TaxCategory;
import PD.TaxRate;
import PD.UPC;

public class DataManagement {
    
    /**
     * Loads store data from a CSV file and populates a Store object with relevant information.
     * @param store The store object to be populated with data.
     * @return The populated store object.
     */
    public static Store loadStore(Store store) {
        Sale sale = null;

        String fileName = "StoreData_v2024FA.csv";  // Name of the CSV file containing store data
        String line = null;  // Used to read each line of the CSV file
        String dataType;  // Stores the type of data in each line
        String[] splitter;  // Splits each line by commas for easier parsing
        FileReader fileReader;
        BufferedReader bufferedReader;
        try {
            // Initialize file reader and buffered reader
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            // Read each line of the CSV file
            while ((line = bufferedReader.readLine()) != null) {
                splitter = line.split(",");  // Split the line by commas
                dataType = splitter[0];  // Determine the type of data (e.g., Store, TaxCategory, Item)

                // Populate the store based on data type
                if (dataType.equals("Store")) {
                    // Set the store's name
                    store.setName(splitter[1]);
                } else if (dataType.equals("TaxCategory")) {
                    // Add a tax category and tax rate to the store
                    TaxCategory taxCategory = new TaxCategory(splitter[1]);
                    TaxRate taxRate = new TaxRate(splitter[3], splitter[2]);
                    taxCategory.addTaxRate(taxRate);
                    store.addTaxCategory(taxCategory);
                } else if (dataType.equals("Cashier")) {
                    // Add a cashier to the store with details
                    store.addCashier(new Cashier(splitter[1], splitter[2], splitter[3], splitter[4], splitter[5],
                            splitter[6], splitter[7], splitter[8], splitter[9]));
                } else if (dataType.equals("Item")) {
                    // Add an item to the store
                    Item item = new Item(splitter[1], splitter[3]);  // Initialize item with number and description
                    Price price = new Price(splitter[5], splitter[6]);  // Set item price
                    UPC uPC = new UPC(splitter[2], item);  // Associate UPC with item
                    item.setTaxCategory(splitter[4], store);  // Set the item's tax category
                    item.addPrice(price);  // Add the price to the item
                    item.addUPC(uPC);  // Add the UPC to the item
                    store.getTaxCategory(splitter[4]).addItem(item);  // Add item to the tax category
                    // Check if there is a promo price to add to the item
                    if (splitter.length > 7) {
                        PromoPrice pprice = new PromoPrice(splitter[7], splitter[8], splitter[9]);
                        item.addPrice(pprice);
                    }
                    store.addItem(item);  // Add the item to the store's item list
                } else if (dataType.equals("Register")) {
                    // Add a register to the store
                    store.addRegister(new Register(splitter[1]));
                } else if (dataType.equals("Session")) {
                    // Create a session with start and end times and add it to the store
                    Session session = new Session(splitter[1], splitter[2], store);
                    session.setEndDateTime(LocalDateTime.now());
                    store.addSession(session);
                } else if (dataType.equals("Sale")) {
                    // Create a sale with tax-free status based on CSV data ("Y" for tax-free, "N" for taxed)
                    String taxFree = splitter[1];
                    sale = new Sale(taxFree);  // Initialize sale with tax-free status
                    // Add the sale to the most recent session
                    store.getSessions().get(store.getSessions().size() - 1).addSale(sale);
                } else if (dataType.equals("SaleLineItem")) {
                    // Add a sale line item to the most recent sale in the most recent session
                    Session session = store.getSessions().get(store.getSessions().size() - 1);
                    session.getSales().get(session.getSales().size() - 1)
                            .addSaleLineItem(new SaleLineItem(splitter[1], splitter[2], store));
                } else if (dataType.equals("Payment")) {
                    // Determine payment type and add payment to the current sale
                    dataType = splitter[1];
                    Session session = store.getSessions().get(store.getSessions().size() - 1);
                    Sale currentSale = session.getSales().get(session.getSales().size() - 1);

                    if (dataType.equals("Cash")) {
                        // Add a cash payment to the sale
                        currentSale.addPayment(new Cash(splitter[2], splitter[3]));
                    } else if (dataType.equals("Credit")) {
                        // Add a credit payment with card details to the sale
                        Credit creditPayment = new Credit(splitter[4], splitter[5], splitter[6]);
                        creditPayment.setAmount(splitter[2]);
                        creditPayment.setAmtTendered(splitter[3]);
                        currentSale.addPayment(creditPayment);
                    } else if (dataType.equals("Check")) {
                        // Add a check payment with routing number and account number to the sale
                        Check checkPayment = new Check(splitter[2], splitter[3], splitter[5], splitter[6]);
                        checkPayment.setRoutingNumber(splitter[4]);
                        currentSale.addPayment(checkPayment);
                    }
                }
            }
            bufferedReader.close();  // Close the buffered reader after reading the file
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file '" + fileName + "'");  // Handle file not found exception
        } catch (IOException e) {
            System.out.println("Error reading file '" + fileName + "'");  // Handle IO exceptions
        }

        return store;  // Return the populated store object
    }
}
