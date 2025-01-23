package PD;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

/**
 * Representation of a Credit card
 */
public class Credit extends AuthorizedPayment {

    /**
     * Type of card (Visa, Master Card, etc)
     */
    private String cardType;
    /**
     * Account Number to define what account the card belongs to
     */
    private String acctNumber;
    /**
     * When a card will expire
     */
    private LocalDate expireDate;

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAcctNumber() {
        return this.acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

    public LocalDate getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * Default constructor for a Credit
     */
    public Credit() {
        cardType = "";
        acctNumber = "";
        try {
            // Setting a default date with error handling
            expireDate = LocalDate.parse("1/1/1111", DateTimeFormatter.ofPattern("M/d/yyyy"));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid default date format: " + e.getMessage());
            expireDate = LocalDate.now(); // Set to current date as a fallback
        }
    }

    /**
     * Constructor for a credit that initializes cardType to cardType, acctNumber to acctNumber, and expireDate to expireDate
     * @param cardType Type of card (Visa, Master Card, etc)
     * @param acctNumber Account Number to define what account the card belongs to
     * @param expireDate When a card will expire
     */
    public Credit(String cardType, String acctNumber, String expireDate) {
        this();
        this.cardType = cardType;
        this.acctNumber = acctNumber;
        try {
            // Parse the expireDate with a specific format and handle incorrect formats
            this.expireDate = LocalDate.parse(expireDate, DateTimeFormatter.ofPattern("M/d/yyyy"));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use M/d/yyyy format.");
            this.expireDate = LocalDate.now(); // Set to current date as a fallback
        }
    }

    /**
     * Determines whether or not a card is authorized to pay for a Sale
     * @return True, is authorized. False, is not authorized
     */
    public Boolean isAuthorized() {
        Random rand = new Random();
        return rand.nextInt(100) + 1 <= 85; // 85% chance to return true
    }

    /**
     * Determines whether a Credit payment counts as Cash, usually used for cash back
     * @return True, counts as cash. False, does not count as cash
     */
    public Boolean countsAsCash() {
        return true;
    }

    /**
     * Makes a String representation of a Credit
     * @return The String representation of a Credit
     */
    @Override
    public String toString() {
        return "Credit [cardType=" + cardType + ", acctNumber=" + acctNumber + ", expireDate=" + expireDate + "]";
    }
}
