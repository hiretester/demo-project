The aim of the project is testing of user registration and purchasing of a product as new user 
and as registered user on the http://www.wecsandbox.com according to the test cases
https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0

For running tests, java 8 and last version of Chrome should be installed.
The project was created in IntelliJ IDEA and it is recommended to use this IDE for running tests. However, 
Eclipce or other IDE for java could be used.
To run tests and generate allure report the next maven command should be used mvn test site
The report is generating to /target/site/allure-maven-plugin/index.html file. The full path will depend on where 
the project will be located.
It is recommended to use Mozilla Firefox for openning index.html file.
