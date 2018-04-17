h1 Demo project for testing WebEyeCare website
==============================
h2 Getting started
-------------------------------
The aim of the project is testing of user registration and purchasing of a product as new user 
and as registered user on the http://www.wecsandbox.com according to the test cases
https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0

h2 Prerequisites
----------------------------
For running tests, maven, java 8 and latest version of Chrome should be installed.

h2 Running tests

To run tests and generate allure report the next maven command should be used 

mvn test site

h2 Allure Report

The report is generating to /target/site/allure-maven-plugin/index.html file. The full path will depend on where 
the project will be located.

It is recommended to use Mozilla Firefox for openning index.html file.

h2 Build with

[Maven](https://maven.apache.org/) - Dependency Management
