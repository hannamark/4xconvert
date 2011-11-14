Feature: CTRP Ad-Hoc Report Page functionality

Scenario: First customer test case
    Given I select a primary purpose of Treatment
    And I select a trial phase of II
    And I select a trial category of Both
    And I select a disease condition of recurrent thyroid cancer
    When the run report button is clicked 
    Then a report will be displayed with 18 results
	And no interface command shall take more than 10 seconds
	
Scenario: Second customer test case
	Given I select a primary purpose of Treatment
    And I select a trial category of Both
    And I select a disease condition of recurrent thyroid cancer
    When the run report button is clicked 
    Then a report will be displayed with 32 results
	And no interface command shall take more than 10 seconds
	
Scenario: Third customer test case
	Given I select a lead organization of 'Mayo Clinic'
    And I select a trial category of Complete
    When the run report button is clicked 
    Then a report will be displayed with 3704 results
	And no interface command shall take more than 10 seconds
