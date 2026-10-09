Feature: Creating an ad

  Scenario: User publishes an ad
    Given the user has signed in
    When the user publishes an ad in the "Технологии" category
    Then the ad appears in the user's ads
