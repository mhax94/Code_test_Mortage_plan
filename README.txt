# Introduction

this code runs the text from prospect.txt and displays:

```bash
CustomerName wants to borrow X € for a period of Z years and pay E € each month
```

## Getting Started

The program runs on the build tool Gradle and is connecting to a MySQL database with the name mortagebase.

The current version requiers the user to import the database-file to MySQL.
The file is in the database-map.

You must write username and password seperated by a comma in the signIn.txt for the code to connect to the databse

in Main.java is currently the following code made into a comment

```java
//monthlyPayment=mortage.monthlyPayment(loan,interest,years);
//addCustomerSQL(newCustomers[i],monthlyPayment);
```

This makes the code run if the database hasn't been imported
If the database is imported, remove // and let the sql code add the customers to the database


## Main code

the code has the following four java-files

```bash
Main
MortageCalculator
MySQLConnection
TextExtractor
```

Main is where the code initiates from. to run the code, run Main.java

MortageCalculator calculates the monthly payment and displays the customer according to the assignment

MySQLConnection has different SQL commands. it's main purpose is to add customers to the database. However it can also remove one, or all customers from database

TextExtractor takes the text from prospects.txt and adjusts/filters the content and makes an array with all the customers. This array is what MortageCalculator and MySQLConnection uses

##Test

the code has the following java-files

```
CalculateAndDisplayTest
SQLTest
TxtExctractTest
```
CalculateAndDisplayTest tests MortageCalculator

SQLTest tests MySQLConnection

TxtExctractTest tests TextExctractor 

