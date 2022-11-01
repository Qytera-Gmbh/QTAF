[<img src="assets/QyteraLogo.png" align="right" alt="Qytera logo" width="15%" title="Qytera logo">](https://www.qytera.de/)

# QTAF - Qytera Test Automation Framework
The Qytera Test Automation Framework (QTAF) is a Java test framework developed by Qytera GmbH based on TestNG, which offers easy setup of new Selenium test projects, HTML reporting, Cucumber support, connection to Jira Xray and easy extensibility.

A costly time factor when setting up a new test project is setting up test case documentation. A reporting format such as JSON that is as general-purpose as possible is needed to document the executed test cases and any errors that may have occurred and to transfer them to another tool such as a test management system. Furthermore, HTML reporting would be useful to provide testers with a quick overview of the executed tests and to find errors quickly.

Setting up such an environment is time-consuming and ties up an unnecessary amount of resources. Moreover, it is ineffective to repeat this process for every new test project. It would be more effective to develop a framework that would take over exactly these tasks, so that the testers can take care of their actual task: testing.

QTAF is the realization of exactly this idea, to automate the setup of the test environment and the documentation of the test cases. And QTAF offers the testers even more.

[![GitHub License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/Qytera-Gmbh/QTAF/blob/master/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat)](https://makeapullrequest.com) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Qytera-Gmbh_QTAF&metric=sqale_rating&token=a2bbe8b96ab480a3f4a4c1030d2d3192a622b239)](https://sonarcloud.io/summary/new_code?id=Qytera-Gmbh_QTAF)
<!-- [![GitHub Workflow Status](https://img.shields.io/github/workflow/status/qytera-gmbh/qtaf/workflow-name?logo=github)](https://github.com/Qytera-Gmbh/QTAF/actions) -->
<!-- [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Qytera-Gmbh_QTAF&metric=vulnerabilities&token=a2bbe8b96ab480a3f4a4c1030d2d3192a622b239)](https://sonarcloud.io/summary/new_code?id=Qytera-Gmbh_QTAF) -->
<!-- [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Qytera-Gmbh_QTAF&metric=bugs&token=a2bbe8b96ab480a3f4a4c1030d2d3192a622b239)](https://sonarcloud.io/summary/new_code?id=Qytera-Gmbh_QTAF) -->

## The advantages of QTAF

QTAF is a project that was born out of practical experience and combines the experience of testers from years of working with testing tools. QTAF solves three basic and time-consuming problems of testing:

- Setting up a configurable Selenium test environment,
- The reporting of an executed test suite
- The subsequent documentation of the test results.

The implementation of these enumerated problems requires from the testers on the one hand good programming skills and knowledge in the design of software projects, on the other hand it is very time-consuming and thus expensive. The test environment should be aligned to reusability, so that many of its basic components can be used also in other test environments. This saves valuable time in other projects.

Before the actual test cases can be written, the test environment must be set up and put into operation. This usually takes days to weeks and costs your company an unnecessary amount of time and money. This is where the idea of QTAF came up, which should relieve the tester of setting up such a reusable test environment.


QTAF solves exactly these problems, since it relieves the tester of the development of an own test environment and thus no knowledge in software development projects must be presupposed. The setup of a QTAF project takes a few minutes, after which the development of test cases can be started immediately. The testers have a complete Selenium test environment at their disposal after setup, which is capable of running automated tests on the most popular browsers such as Chrome, Edge, Firefox, Opera and Safari. The documentation of the test cases is also handled by QTAF and can be automatically transferred to a test management tool such as Xray after the test suite has been run.

### Setting up a new test project
Setting up a new test project is very simple using QTAF. All that is required is the creation of a Maven project, for which a suitable development environment such as Eclipse or IntelliJ can be used. Then, all that is required is to set up QTAF as a dependency for the project and the test environment is ready.

The testers can immediately start creating test cases. The reporting of these test cases is done automatically by the QTAF framework. The following sections will explain which other features are offered by the test project that has now been set up.

### Creating Test Cases
Creating a test case with QTAF is as easy as with an ordinary TestNG project. Testers who are familiar with this framework will find QTAF easy to use. The testers create their test classes and provide them with additional annotations, which QTAF provides. During testing, QTAF monitors which test cases have been executed and creates both JSON and HTML reports from the recorded data.


### Configurability
All configuration parameters can be passed either via a JSON file, via environment variables or as command line parameters. In doing so, QTAF also provides the ability to override default values from a JSON file using environment variables or command line parameters. This feature is especially useful if you want to use the same QTAF project in multiple environments with different configuration values.

For example, you can specify a default configuration in a JSON file and overwrite the name of the web driver using an environment variable or command line parameter when you run it. Thus, you can test a webapp on multiple browsers in parallel without making any change in your source code.


### Docker and Kubernetes
QTAF is designed to be used as a microservice. Due to its configurability using environment variables, it is possible to implement the following scenario: You want to test a webapp on the most popular browsers (Chrome, Firefox, Edge). Create a Docker image from your QTAF project and create multiple container instances from this image. Each instance tests the webapp using a webdriver whose name you pass via an environment variable.

This allows you to run multiple tests in parallel on different browsers using the same code base and Docker image. Using Docker and Kubernetes, it is also possible to distribute Docker containers across multiple servers and use them with the most common cloud providers, such as AWS and Azure.

### Selenium Webdriver
QTAF automatically loads the latest versions of Selenium Web Drivers for the most popular web browsers such as Chrome, Firefox, Edge, Internet Explorer or Opera. After setting up a QTAF project, testers do not need to install the required browsers or worry about updating them. QTAF takes care of these tasks for you and takes care of obtaining and updating the appropriate browsers itself. Only the browser name has to be transferred by means of the configuration file.

### Android Testing via Appium
QTAF offers the possibility to test Android devices via Appium. QTAF itself provides the drivers required for testing Android devices. Again, users only need to pass the name of the driver via the configuration file.

### Remote Webdriver
QTAF also allows you to control web drivers over a network. This is a useful feature especially in cloud environments. Docker containers are already available in the official Docker Registry for many of the most common drivers.

### Saucelabs
QTAF supports test execution on the Saucelabs platform. Saucelabs is a cloud environment in which virtual machines with pre-installed browsers are deployed. This offers the advantage of running tests on a large number of different browsers, browser versions and operating systems in a short time. The parameters listed can be passed to QTAF externally. In addition to testing web applications, Saucelabs also supports the testing of mobile apps on the Android and iOS operating systems. Here, too, the advantage of Saucelabs is that the required devices do not have to be purchased by the customer but can be leased and remotely controlled via Saucelabs. Here, too, the advantage is that test cases can be tested efficiently on different versions of the operating systems and different devices from different manufacturers.

Saucelabs comes up with additional features such as video recordings and screenshots of the test runs. These can be viewed and downloaded after the test run has been executed. You can track each test step of a test case using screen recordings.

### Page Object Pattern and Dependency Injection
QTAF supports the Page Object Pattern. In the Page Object Pattern (PO), code that drives a visual object of a website (for example, a navigation bar, a form, etc.) is encapsulated in its own class. Within these classes, methods are provided for manipulating this object (for example, clicking a button, submitting a form).

By means of QTAF these methods, which are called within a PO class, can be tracked. The parameters that were passed to the methods during the test case are also recorded, so that it is later possible to trace which parameters caused which result during a method call.

Using Dependency Injection, the PO classes created can be easily integrated into other classes. The testers are relieved of the effort of instantiating the corresponding PO classes themselves, which also results in a reduction of the complexity and the amount of code.

### Support of Cucumber
QTAF offers support for Cucumber out of the box. Here, QTAF records the executed Cucumber tests and also provides the results of the test cases via JSON or as HTML report.

### Extensibility
QTAF offers its own event system that allows testers to add their own logic to the test flow or develop their own plugins for QTAF. For example, testers can implement their own processing of the generated reporting data, such as a connection to a REST API, automatic notification via e-mail, etc. Thus, entire plugins can be created, which you can use in several projects or also make available to the community.


## QTAF Plugins

### Generation of log files and HTML reports
After the execution of the test cases, QTAF generates a JSON file in which the executed test cases are listed together with their parameters. This makes it possible to track how much time the execution of individual test cases and the entire test set took. On the other hand, the test results can be further processed by third-party software thanks to the machine-readable format.

Furthermore, an HTML report is also generated from the generated log files, so that the test results are available in a readable format at the end of the test execution.

### Connection to the Xray API
QTAF offers a connection to Xray via a plugin. All you have to do is enter the access data for your own Xray project in the configuration file and QTAF will then save the test results in Xray.

### Developing your own plugins
Since the world of software is constantly changing, QTAF was designed to be extensible. Plugins can access and process the collected data via the event system of QTAF.
