Feature: CTRP Ad Hoc Report Page functionality

Scenario: First customer test case
    Given I select a primary purpose of Treatment
    And I select a trial phase of II
    And I select a trial category of Both
    And I select a disease condition of recurrent thyroid cancer
    When I click the run report button 
    Then a report will be displayed with 18 results
