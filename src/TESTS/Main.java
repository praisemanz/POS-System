package TESTS;

public class Main {

    public static void main(String[] args) {
        // Create instances of the test classes
        AC1 ac1 = new AC1();
        AC2 ac2 = new AC2();
        AC3 ac3 = new AC3();
        AC4 ac4 = new AC4();
        AC5 ac5= new AC5();

        // Call the runTest method on each instance
        ac1.runTest();
        ac2.runTest();
        ac3.runTest();
        ac4.runTest();
        ac5.runTest();
    }
} 
