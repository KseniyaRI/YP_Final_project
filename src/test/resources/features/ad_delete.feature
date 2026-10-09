Feature: Deleting an ad

  Scenario: User deletes their own ad
    Given an account with this email already exists
    And the user has a published ad
    And the user has signed in
    When the user deletes the ad
    Then the ad is no longer in the user's ads
