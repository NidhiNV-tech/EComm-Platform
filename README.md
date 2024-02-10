# EComm-Platform
EComm Platform

A Spring Boot-based REST API to manage products for an e-commerce platform. The API supports CRUD operations for products and each product has the following attributes:

- ProductID (unique identifier)
- Name
- Description
- Price
- Quantity Available
  
The API supports the following operations:

1) Create Product:

Input:  Product details (Name, Description, Price, Quantity Available)
Output:  Product details (ProductID , Name, Description, Price, Quantity Available) an error message if creation fails.

2) Read Product:

Input:  ProductID of the product to retrieve
Output:  Product details (ProductID, Name, Description, Price, Quantity Available) or an error message if the product is not found.

3) Update Product:

Input: Product details (ProductID , Name, Description, Price, Quantity Available) an error message if update fails.
Output: Success/Failure message

4) Delete Product:

Input: ProductID of the product to delete
Output: Success/Failure message

5) Apply Discount or Tax:

Consider an online bookstore where the administrator needs to manage the product catalog. When a new book is added to the catalog, the administrator can choose to apply either a discount or a tax to the book based on certain conditions. For example, when a new fiction book titled "The Adventure" by Author X is added to the catalog with a base price of $30 and a quantity of 50, the administrator might decide to apply a 10% discount to promote sales. Alternatively, they may choose to apply a 5% tax for regulatory compliance on a non-fiction book titled "Tax Law Handbook" by Author Y.

The API supports the application of either a discount or a tax to a product which implements a mechanism to modify the price of a product based on either a discount percentage or a tax rate provided in the request.
Input: ProductID, Discount Percentage OR Tax Rate (client can choose either discount or tax), and applicable value
Output: Success/Failure message, Updated Product details (including modified price)
