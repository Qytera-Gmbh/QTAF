# Test Google Search
Feature: Google Search

  # Wird vor jedem Szenario ausgeführt
  Background: To Launch the browser
    Given Launch the browser

  # Testfall 1
  @TestName:QTAF-4
  Scenario: Search for Cucumber in Google
    When Hit Qytera on your browser
    Then Enter "Cucumber" in the search text box.
    And Select the first result.