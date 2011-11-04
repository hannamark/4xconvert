Feature: CTRP login page functionality.

Scenario: Training user with valid password and account source
    Given I enter a Login Id of abstractor
    And I enter a password of Coppa#12345
    And I enter the Account Source of Training
    When I click the login button
    Then I should be logged in

Scenario: Attempt to login with an invalid password
    Given I enter a Login Id of abstractor
    And I enter a password of BadPassword
    And I enter the Account Source of Training
    When I click the login button
    Then I should not be logged in

Scenario: Attempt login with a user that doesn't exist in the Account Source
    Given I enter a Login Id of Unknown
    And I enter a password of Coppa#12345
    And I enter the Account Source of Training
    When I click the login button
    Then I should not be logged in
    
Scenario: Attempt login with a blank Login Id
    Given I enter a blank Login Id
    And I enter a password of Coppa#12345
    And I enter the Account Source of Training
    When I click the login button
    Then I should not be logged in
    
Scenario: Attempt login with a blank Password
    Given I enter a Login Id of abstractor
    And I enter a blank password
    And I enter the Account Source of Training
    When I click the login button
    Then I should not be logged in
    