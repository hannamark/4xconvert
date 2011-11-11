Feature: CTRP Ad Hoc Report Page functionality

Scenario: First customer test case
    Given I select a primary purpose of Treatment
    And I select a trial phase of II
    And I select a trial category of Both
    And I select a disease condition of recurrent thyroid cancer
    When the run report button is clicked 
    Then a report will be displayed with 18 results
	And the program shall respond in less than 1 seconds
	