Sample of automated tests project using JAVA+TESTNG+SELENIUM WD+ALLURE
------------------------------
### About tests

The aim of the project is testing of user registration and purchasing of a product as new user 
and as registered user on the http://www.wecsandbox.com according to the test cases
https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0

The next technologies was used for creating this project:

JAVA + [TestNG](http://testng.org/doc/) - for writing tests

[Selenium WD](https://www.seleniumhq.org/docs/03_webdriver.jsp) - for communication with browser

[Allure 2](https://github.com/allure-framework/allure2) - for generating report about results of running tests

### Running tests

For running tests, maven, java 8 and latest version of Chrome should be installed.

To run tests from the terminal first go to the project directory and then use the next maven command

> mvn test

To run tests from the terminal and generate allure report the next maven command should be used 

> mvn test site

### Allure Report

After using next command 

> mvn test site 

the report is generating to /target/site/allure-maven-plugin/index.html file. The full path will depend on where 
the project will be located.

It is recommended to use Mozilla Firefox for openning index.html file.

Also it is possible to generate and open report using allure cli. For this [allure cli](https://docs.qameta.io/allure/#_installing_a_commandline) should be installed.

After running tests, in terminal go to the ./target and print next command to generate report

> allure generate ./allure-results

and then next command to open report

> allure open ./allure-report

### Build with

[Maven](https://maven.apache.org/) - Dependency Management
