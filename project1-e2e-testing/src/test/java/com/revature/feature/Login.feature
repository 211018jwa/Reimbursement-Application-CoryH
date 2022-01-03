Feature: Login

# " " double quotes are used as inline parameters for the actual gluecode methods. It will be passed in as arguments

Scenario: Invalid password, valid username (negative test)
	Given I am at the login page
	When I type in a username of "cory_hall" 
	But I type in a password of "paassword1234"
	And I click the login button
	Then I should see a message of "Incorrect username and/or password"
	
Scenario: Invalid password, invalid username (negative test)
	Given I am at the login page
	When I type in a username of "weferga"
	And I type in a password of "gqgrag"
	And I click the login button
	Then I should see a message of "Incorrect username and/or password"
	
Scenario: Valid password, invalid username (negative test)
	Given I am at the login page
	When I type in a username of "afbsbsrt"
	And I type in a password of "password$"
	And I click the login button
	Then I should see a message of "Incorrect username and/or password"
	
Scenario: Valid password, valid username (positive test)
	Given I am at the login page
	When I type in a username of "cory_hall"
	And I type in a password of "password1234"
	And I click the login button
	Then I should be directed to the finance manager homepage
	
Scenario: Valid password, valid username (positive test)
	Given I am at the login page
	When I type in a username of "silerjas"
	And I type in a password of "pass4321"
	And I click the login button
	Then I should be directed to the employee homepage
	
# Scenario v. Scenario Outline
# Scenario: single grouping of steps that will be executed ONCE sequentially
# Scenario Outline: template of steps that will be executed MULTIPLE times sequentially
	
Scenario Outline: Successful employee login
	Given I am at the login page
	When I type in a username of <username>
	And I type in a password of <password>
	And I click the login button
	Then I should be redirected to the employee homepage
	
	Examples:
		| username   | password    |
		| "silerjas"  | "pass4321"   |
		|"jsimms"| "words567" |
		

		
Scenario Outline: Successful FM login
	Given I am at the login page
	When I type in a username of <username>
	And I type in a password of <password>
	And I click the login button
	Then I should be redirected to the finance manager homepage
	
	Examples:
		| username      | password   |
		| "cory_hall"   | "password1234"|	