Feature: Navigation test

  Background: To Launch the browser
    Given Launch the browser

  # Scenario with a 1D data table
  @TestName:QTAF-1
  Scenario: Navigate to page
    Given There are the following valid links
      | testing-solutions  |
      | seminare-trainings |
    When I visit the Qytera homepage
    Then I navigate to "testing-solutions"

  # Scenario with a 1D data table
  @TestName:QTAF-2
  Scenario: Navigate to contact page
    Given There are the following invalid links
      | abc |
      | xyz |
    When I visit the Qytera homepage
    Then I navigate to "seminare-trainings"

  @TestName:QTAF-3
  Scenario: Fill contact field
    Given I am at the contact form page
    When I enter the following contact data
      | Name     | Email             | Subject |
      | John Doe | john.doe@inet.com | Hallo   |
      | Jane Doe | jane.doe@inet.com | Hallo   |
    And I enter the following message
      """
      Hallo Welt,
      dies ist eine Testnachricht.
      """
    Then I can submit the form
