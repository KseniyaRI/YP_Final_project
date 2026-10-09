Feature: Editing an ad

  Scenario: User edits their own ad
    Given an account with this email already exists
    And the user has a published ad
    And the user has signed in
    When the user changes the title of the ad
    Then the ad is shown with the new title
