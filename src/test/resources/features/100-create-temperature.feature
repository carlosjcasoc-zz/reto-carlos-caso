Feature: Create Temperature

  Scenario: WITH ALL REQUIRED FIELDS IS SUCCESSFUL

    Given user wants to create an temperature with the following attributes
      | temperatureValue  | creationDate |
      | 78.9 | 2020-09-02 09:37:15    |

    When user saves the new temperature 'WITH ALL REQUIRED FIELDS'
    Then the save 'IS SUCCESSFUL'

  Scenario: WITH ALL REQUIRED FIELDS IS FAILS

    Given user wants to create an temperature with the following attributes
      | temperatureValue  | creationDate |
      | 78.9 | 2020-09-02 09:37:1b    |

    When user saves the new temperature 'WITH ALL REQUIRED FIELDS'
    Then the save 'FAILS'