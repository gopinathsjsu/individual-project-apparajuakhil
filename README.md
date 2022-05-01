# Inventory Billing Java Application

This is a java application which maintains an inventory of stock i.e developed using java data structures. It takes an input CSV file of order & validates it against the stock & cap limits & if it is a valid order then we created a success order CSV else we create a error TXT file.

Prerequisites: -
To run this application the below dependencies has to be installed:

- Install JDK 18.0.1
- Install Maven 3.8.5

Below are the steps to run: -

- Clone the git repository
- Go to the project strucure i.e to the Inventory folder & run the below 2 commands
- mvn compile
- mvn exec:java -Dexec.mainClass=com.inventory.Billing
- Enter the input path of the order file

# Design Patterns: -

The patterns used in this application are Singleton Pattern & Factory Method Pattern.

# Singleton Pattern:

The Singleton pattern is used here to create the internal static inmemory database. It restricts the instantiation of a class to one "single" instance. As Billing class calls the database initially, it creates the single instance & reuse the same instance for all the other calls as there is no need to instantiate it again. With the use of Singleton Pattern, we get a global access point to the database instance.

# Factory Method Pattern:

The Factory Method design pattern is used here to provide an interface for creating objects in a superclass while allowing subclasses to change the type of objects created. We use the Result Generator as a common interface to generalize methods i.e writeToFile & saveFile. We use these methods to write CSV & txt files as per the given order i.e for success we write to CSV & for failure we write it to TXT file.
