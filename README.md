# AzeriGas API Service

## Overview
This API service provides functionality for managing transactions and related data for abonents. It allows users to perform various operations such as adding transactions, retrieving transactions by abonent code, and more.

## Technologies used
<ul>
    <li>Java</li>
    <li>Spring Boot</li>
    <li>MySQL</li>
</ul>

## Setup
<ol>
    <li>Clone the repository from GitHub: link-to-repo</li>
    <li>Ensure you have Java and MySQL installed on your system.</li>
    <li>Configure the application properties file (application.properties) with your MySQL database details.</li>
    <li>Build and run the application using Maven or your preferred IDE.</li>
    <li>Once the application is running, you can start making requests to the API endpoints.</li>
</ol>

## Endpoints

<h4>Add Transaction</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>POST /api/transactions</code></li>
  <li><strong>Description:</strong> Adds a new transaction for the specified abonent.</li>
  <li><strong>Request Parameters:</strong>
    <ul>
      <li><code>transactionAmount</code>: The amount of the transaction.</li>
      <li><code>abonentCode</code>: The code of the abonent for whom the transaction is being added.</li>
    </ul>
  </li>
  <li><strong>Response:</strong> Returns the details of the added transaction.</li>
</ul>

<h4>Get Transactions by Abonent Code</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>GET /api/transactions/{abonentCode}</code></li>
  <li><strong>Description:</strong> Retrieves a list of transactions for the specified abonent.</li>
  <li><strong>Path Parameter:</strong> <code>abonentCode</code>: The code of the abonent for whom transactions are being retrieved.</li>
  <li><strong>Response:</strong> Returns a list of transactions with details such as transaction amount and date.</li>
</ul>

<h4>Get All Abonents</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>GET /api/abonents</code></li>
  <li><strong>Description:</strong> Retrieves a list of all abonents.</li>
  <li><strong>Response:</strong> Returns a list of abonents with details such as abonent code, name, and region.</li>
</ul>

<h4>Get Abonent by Code</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>GET /api/abonents/{abonentCode}</code></li>
  <li><strong>Description:</strong> Retrieves the details of a specific abonent by their code.</li>
  <li><strong>Path Parameter:</strong> <code>abonentCode</code>: The code of the abonent to retrieve.</li>
  <li><strong>Response:</strong> Returns the details of the abonent, including code, name, region, and tariff.</li>
</ul>

<h4>Add Abonent</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>POST /api/abonents</code></li>
  <li><strong>Description:</strong> Adds a new abonent to the system.</li>
  <li><strong>Request Body:</strong> JSON object containing abonent details such as name, region, and tariff.</li>
  <li><strong>Response:</strong> Returns the details of the added abonent.</li>
</ul>

<h4>Update Abonent</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>PUT /api/abonents/{id}</code></li>
  <li><strong>Description:</strong> Updates the details of a specific abonent.</li>
  <li><strong>Path Parameter:</strong> <code>id</code>: The ID of the abonent to update.</li>
  <li><strong>Request Body:</strong> JSON object containing updated abonent details.</li>
  <li><strong>Response:</strong> Returns the details of the updated abonent.</li>
</ul>

<h4>Delete Abonent</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>DELETE /api/abonents/{id}</code></li>
  <li><strong>Description:</strong> Deletes a specific abonent from the system.</li>
  <li><strong>Path Parameter:</strong> <code>id</code>: The ID of the abonent to delete.</li>
  <li><strong>Response:</strong> Returns a success message upon successful deletion.</li>
</ul>

<h4>Get All Regions</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>GET /api/regions</code></li>
  <li><strong>Description:</strong> Retrieves a list of all regions.</li>
  <li><strong>Response:</strong> Returns a list of regions with details such as region ID and name.</li>
</ul>

<h4>Get Region by ID</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>GET /api/regions/{id}</code></li>
  <li><strong>Description:</strong> Retrieves the details of a specific region by its ID.</li>
  <li><strong>Path Parameter:</strong> <code>id</code>: The ID of the region to retrieve.</li>
  <li><strong>Response:</strong> Returns the details of the region, including ID and name.</li>
</ul>

<h4>Get All Tariffs</h4>
<ul>
  <li><strong>Endpoint:</strong> <code>GET /api/tariffs</code></li>
  <li><strong>Description:</strong> Retrieves a list of all tariffs.</li>
  <li><strong>Response:</strong> Returns a list of tariffs
</ul>

## Dependencies
- **Spring Boot Starter Web**: for building RESTful APIs.
- **Spring Boot Starter Data JPA**: for working with relational databases using JPA.
- **ModelMapper**: for mapping DTOs to entity objects and vice versa.
- **MySQL Connector**: for connecting Spring Boot application with MySQL database.
- **Lombok**: for reducing boilerplate code by automatically generating getters, setters, constructors, and other common code.

