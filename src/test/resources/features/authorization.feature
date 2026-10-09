Feature: Authorization

  Scenario: Registered user signs in
    Given an account with this email already exists
    And the user opens the sign in form
    When the user signs in with a valid email and password
    Then the user is signed in
