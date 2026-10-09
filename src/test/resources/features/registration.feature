Feature: Registration

  Scenario: Guest registers with a new email
    Given the guest opens the registration form
    When the guest registers with a new email
    Then the guest is signed in

  Scenario: Guest cannot register with an email that is already taken
    Given an account with this email already exists
    And the guest opens the registration form
    When the guest registers with the same email
    Then the error "Ошибка" is shown under the email field
