Feature: Get Temperature by date

  Scenario: WITH ALL REQUIRED PARAMS IS SUCCESSFUL
    When user call the temperatures by filter date: '2020-09-02' and conversion: 'C' group hour
    Then the get 'IS SUCCESSFUL'

  Scenario: WITH ALL REQUIRED PARAMS IS SUCCESSFUL AND COVERT fahrenheit
    When user call the temperatures by filter date: '2020-09-02' and conversion: 'F' group hour
    Then the get 'IS SUCCESSFUL'

  Scenario: WITH ALL REQUIRED PARAMS IS FAILS
    When user call the temperatures by filter date: '2020-09-0b' and conversion: 'C' group hour
    Then the get 'FAILS'