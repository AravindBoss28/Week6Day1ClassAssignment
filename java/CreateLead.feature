Feature: Create Lead 

Scenario: Verify Application is in Create Lead page 
	Given user is in Home Page 
	
Scenario Outline: Create a lead and verify lead is available in application 
	When user clicks on "Create Lead" link 
	When user enters <CompanyName> <FirstName> <LastName> 
	And users click on the save button 
	Then created leads are available in application <CompanyName>
	
	Examples: 
		| CompanyName    | FirstName    | LastName |
		| 'Test11'       | 'Aravind11'  | 'boss11' |
		| 'Test21'       | 'Aravind21'  | 'boss21' |
		| 'Test31'       | 'Aravind31'  | 'boss31' |

Scenario Outline: Duplicate the created Leads 
	When user clicks on "Find Leads" link 
	And user searches with <FirstName> and Lead ID 
	And user selects the first option 
	And user clicks on "Duplicate Lead" link 
	And user modifies the details 
	And users click on the save button 
	Then modified data is available in application for <FirstName> 
	Examples: 
		| FirstName    | CompanyName |   
		| 'Aravind11'  | 'Test11'    |
		| 'Aravind21'  | 'Test21'    |
		| 'Aravind31'  | 'Test31'    |
		
Scenario Outline: 
	When user clicks on "Find Leads" link 
	And user searches with <FirstName> and Lead ID 
	And user selects the first option 
	And user clicks on "Delete" link 
	Examples: 
		| FirstName    |
		| 'Aravind11'  |
		| 'Aravind21'  |
		| 'Aravind31'  |
		