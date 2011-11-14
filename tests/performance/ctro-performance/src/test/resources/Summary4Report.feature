Feature: CTRP Summary 4 Type Report Page functionality

Scenario: First customer test case
    Given I set the start date to 1/1/2009
    And I set the end date to 9/7/2011
    And I select 'Dana-Farber/Harvard Cancer Center' as my family
    When the run report button is clicked 
    Then a report will be displayed with 18 results
	And the program shall respond in less than 1 seconds
	