Feature: Test Leaf login 

Scenario: Login Test Leaf application 
	Given chrome browser is available and loaded with testleaf url
	When user login with Demosalesmanager and password
	
Scenario: Navigate to Home page
	When user clicks on CRM tab
	Then application navigates to home page
	
Scenario: Navigate to Create Lead page
	When user clicks on create lead link
	Then application is in create lead page