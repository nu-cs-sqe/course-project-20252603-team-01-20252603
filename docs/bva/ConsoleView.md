# BVA Analysis — `ConsoleView`


### Method under test: `promptNumberOfPlayers()`

- **TC1: Reads minimum valid player count** ( :white_check_mark: )
    - **State of the system**: User enters `3` when prompted for number of players
    - **Expected output**: Returns `3`

- **TC2: Reads maximum valid player count** ( :white_check_mark: )
    - **State of the system**: User enters `6` when prompted for number of players
    - **Expected output**: Returns `6`

- **TC3: Reads below-minimum player count for model validation** ( :white_check_mark: )
    - **State of the system**: User enters `2` when prompted for number of players
    - **Expected output**: Returns `2` so `GameModel.setPlayerCount(2)` can reject it and setup can re-prompt

- **TC4: Reads above-maximum player count for model validation** ( :white_check_mark: )
    - **State of the system**: User enters `7` when prompted for number of players
    - **Expected output**: Returns `7` so `GameModel.setPlayerCount(7)` can reject it and setup can re-prompt

---

### Method under test: `promptPlayerName(int playerNumber)`

- **TC5: Reads first player name** ( :white_check_mark: )
    - **State of the system**: `promptPlayerName(1)` called and user enters a non-empty name
    - **Expected output**: Returns the entered name for player 1

- **TC6: Reads last player name in maximum-size game** ( :white_check_mark: )
    - **State of the system**: Six-player game; `promptPlayerName(6)` called and user enters a non-empty name
    - **Expected output**: Returns the entered name for player 6

---

### Method under test: `promptPlayerColor(String playerName, List<PlayerColor> availableColors)`

- **TC7: Reads color when all colors are available** ( :white_check_mark: )
    - **State of the system**: `availableColors` contains all six colors; user selects `RED`
    - **Expected output**: Returns `PlayerColor.RED`

- **TC8: Reads color when one color remains** ( :white_check_mark: )
    - **State of the system**: `availableColors` contains exactly one color; user selects that color
    - **Expected output**: Returns the only available `PlayerColor`

- **TC9: Reads unavailable color for model validation** ( :white_check_mark: )
    - **State of the system**: `availableColors` does not contain `RED`; user selects `RED`
    - **Expected output**: Returns `PlayerColor.RED` so `GameModel.addPlayer(...)` can reject the duplicate color and setup can re-prompt

---

### Method under test: `displayCurrentPlayer(String currentPlayerName)`

- **TC12: Displays selected starting player** ( :white_check_mark: )
    - **State of the system**: A first player has been selected automatically by setup; `currentPlayerName` contains the selected player name
    - **Expected output**: Output displays the selected current player name

---

### Method under test: `displayUnclaimedTerritoriesByContinent(String unclaimedTerritories)`

- **TC13: Displays unclaimed territories grouped by continent** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string containing only unclaimed territories grouped by continent
    - **Expected output**: Output displays the unclaimed territories string exactly as provided by the model

---

### Method under test: `displayCurrentPlayerClaimingStatus(String playerClaimingStatus)`

- **TC14: Displays current player's claiming status** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string containing the current player's name, available Infantry, and currently owned territories
    - **Expected output**: Output displays the current player's claiming status exactly as provided by the model

---

### Method under test: `getTerritoryChoiceDuringSetup()`

- **TC15: Returns entered territory choice during setup** ( :white_check_mark: )
    - **State of the system**: Player is prompted to enter the name of a territory to claim
    - **Expected output**: Returns the territory name entered by the player

---

### Method under test: `getInfantryChoiceDuringSetup()`

- **TC16: Returns entered Infantry count during setup** ( :white_check_mark: )
    - **State of the system**: Player is prompted to enter the number of Infantry to place
    - **Expected output**: Returns the Infantry count entered by the player so the model can validate it

---

### Method under test: `displayCurrentPlayerTerritoriesByContinent(String territoriesByContinent)`

- **TC17: Displays one owned territory grouped by continent** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string where the current player owns exactly one territory
    - **Expected output**: Output displays the provided territory string exactly

- **TC18: Displays multiple owned territories grouped by continent** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string where the current player owns more than one territory across one or more continents
    - **Expected output**: Output displays the provided territory string exactly

---

### Method under test: `promptCurrentPlayerTerritoryChoice()`

- **TC19: Returns first owned territory name entered for remaining-army placement** ( :white_check_mark: )
    - **State of the system**: Current player is prompted to choose one of their territories and enters the name of a territory they own
    - **Expected output**: Returns the entered territory name so the model can place one Infantry

- **TC20: Returns unowned territory name for model validation** ( :white_check_mark: )
    - **State of the system**: Current player enters the name of a territory owned by another player
    - **Expected output**: Returns the entered territory name so `addArmiesDuringSetup()` can reject it and setup can re-prompt

---

### Method under test: `displaySetupPhaseComplete()`

- **TC21: Displays setup completion message once placement is complete** ( :white_check_mark: )
    - **State of the system**: All territories are claimed and all players have `0` available Infantry
    - **Expected output**: Output displays a message saying setup is complete and the game is starting now
### Method under test: `displayCurrentPlayerArmies(String availableArmies)`

- **TC22: Displays current player's available army pool** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string containing available Infantry, Cavalry, and Artillery counts
    - **Expected output**: Output displays the available-armies string

---

### Method under test: `promptReinforcement()`

- **TC23: Returns territory name with one Infantry placement** ( :white_check_mark: )
    - **State of the system**: Player enters `"Alaska 1 0 0"`
    - **Expected output**: Returns a list containing `"Alaska"`, `"1"`, `"0"`, and `"0"`

- **TC24: Returns territory name with multiple mixed army types** ( :white_check_mark: )
    - **State of the system**: Player enters `"Alaska 15 2 3"`
    - **Expected output**: Returns a list containing `"Alaska"`, `"15"`, `"2"`, and `"3"`

- **TC25: Returns territory name with zero army placement for model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"Alaska 0 0 0"`
    - **Expected output**: Returns a list containing `"Alaska"`, `"0"`, `"0"`, and `"0"`

- **TC26: Returns territory name with negative army count for model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"Alaska -1 0 0"`
    - **Expected output**: Returns a list containing `"Alaska"`, `"-1"`, `"0"`, and `"0"`

---

### Method under test: `displayCurrentPlayerCards(String cards)`

- **TC22: Displays current player's cards** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string containing the current player's cards
    - **Expected output**: Output displays the cards string exactly as provided by the model

---

### Method under test: `promptChooseCardsToTradeIn()`

- **TC23: Returns three selected card indices** ( :white_check_mark: )
    - **State of the system**: Player enters `"1 2 3"`
    - **Expected output**: Returns a list containing `1`, `2`, and `3`

- **TC24: Returns one-based indices in entered order** ( :white_check_mark: )
    - **State of the system**: Player enters `"3 1 2"`
    - **Expected output**: Returns a list containing `3`, `1`, and `2`

- **TC25: Returns empty list when player skips trade-in** ( :white_check_mark: )
    - **State of the system**: Player presses Enter on empty trade-in input
    - **Expected output**: Returns `List.of()`

- **TC26: Returns invalid low index for controller and model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"0 1 2"`
    - **Expected output**: Returns a list containing `0`, `1`, and `2`

- **TC27: Returns duplicate indices for controller and model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"1 1 2"`
    - **Expected output**: Returns a list containing `1`, `1`, and `2`

- **TC28: Returns fewer than three indices for controller and model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"1 2"`
    - **Expected output**: Returns a list containing `1` and `2`

- **TC29: Returns more than three indices for controller and model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"1 2 3 4"`
    - **Expected output**: Returns a list containing `1`, `2`, `3`, and `4`

- **TC30: Rejects non-numeric card selection input** ( :white_check_mark: )
    - **State of the system**: Player enters `"1 two 3"`
    - **Expected output**: Returns `List.of(Integer.MIN_VALUE)` to signal malformed card-selection input
### Method under test: `promptFortifyChoice()`

- **TC27: Returns yes choice** ( :white_check_mark: )
    - **State of the system**: Current player is asked whether to fortify and enters `"yes"`
    - **Expected output**: Returns `"yes"` so the controller can begin fortification

- **TC28: Returns no choice** ( :white_check_mark: )
    - **State of the system**: Current player is asked whether to fortify and enters `"no"`
    - **Expected output**: Returns `"no"` so the controller can skip fortification

- **TC29: Returns invalid choice for controller validation** ( :white_check_mark: )
    - **State of the system**: Current player is asked whether to fortify and enters a value other than yes/y/no/n
    - **Expected output**: Returns the entered string so the controller can reject it and re-prompt

---

### Method under test: `promptFortifySourceTerritory()`

- **TC30: Returns single-word source territory** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for a source territory and enters `"Alaska"`
    - **Expected output**: Returns `"Alaska"`

- **TC31: Returns multi-word source territory** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for a source territory and enters `"Northwest Territory"`
    - **Expected output**: Returns `"Northwest Territory"`

---

### Method under test: `promptFortifyDestinationTerritory()`

- **TC32: Returns single-word destination territory** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for a destination territory and enters `"Alberta"`
    - **Expected output**: Returns `"Alberta"`

- **TC33: Returns multi-word destination territory** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for a destination territory and enters `"Western United States"`
    - **Expected output**: Returns `"Western United States"`

---

### Method under test: `promptFortifyArmyCount()`

- **TC34: Returns zero army count for controller/model validation** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for armies to move and enters `"0"`
    - **Expected output**: Returns `"0"` so the fortify flow can reject moving zero armies

- **TC35: Returns one army count** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for armies to move and enters `"1"`
    - **Expected output**: Returns `"1"`

- **TC36: Returns multiple army count** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for armies to move and enters `"3"`
    - **Expected output**: Returns `"3"`

- **TC37: Returns negative army count for controller/model validation** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for armies to move and enters `"-1"`
    - **Expected output**: Returns `"-1"` so the fortify flow can reject moving a negative number of armies

- **TC38: Returns non-numeric army count for controller validation** ( :white_check_mark: )
    - **State of the system**: Current player is prompted for armies to move and enters `"two"`
    - **Expected output**: Returns `"two"` so the controller can reject it without calling the model

---

### Method under test: `promptTerritoriesToAttack()`

- **TC39: Returns single-word attacking and defending territory names** ( :white_check_mark: )
    - **State of the system**: Player is prompted separately for attacking and defending territories and enters `"Alaska"` and `"Alberta"`
    - **Expected output**: Returns a list containing `"Alaska"` and `"Alberta"`

- **TC40: Returns multi-word attacking and defending territory names** ( :white_check_mark: )
    - **State of the system**: Player is prompted separately for attacking and defending territories and enters `"Western United States"` and `"Eastern United States"`
    - **Expected output**: Returns a list containing `"Western United States"` and `"Eastern United States"`

- **TC41: Re-prompts on blank attacking territory input** ( :white_check_mark: )
    - **State of the system**: Player is prompted for attacking territory, enters an empty line, and then enters `"Alaska"`; player then enters `"Alberta"` for defending territory
    - **Expected output**: Re-prompts for the attacking territory and then returns a list containing `"Alaska"` and `"Alberta"`

- **TC42: Re-prompts on blank defending territory input** ( :white_check_mark: )
    - **State of the system**: Player enters `"Alaska"` for attacking territory, then enters an empty line for defending territory, and then enters `"Alberta"`
    - **Expected output**: Re-prompts for the defending territory and then returns a list containing `"Alaska"` and `"Alberta"`

---

### Method under test: `promptNumberOfDice(String attackerName, String defenderName)`

- **TC43: Returns minimum valid attacker and defender dice counts** ( :white_check_mark: )
    - **State of the system**: Player enters `"1"` for attacker dice and `"1"` for defender dice
    - **Expected output**: Returns a list containing `1` and `1`

- **TC44: Returns intermediate valid attacker and defender dice counts** ( :white_check_mark: )
    - **State of the system**: Player enters `"2"` for attacker dice and `"1"` for defender dice
    - **Expected output**: Returns a list containing `2` and `1`

- **TC45: Returns maximum valid attacker and defender dice counts** ( :white_check_mark: )
    - **State of the system**: Player enters `"3"` for attacker dice and `"2"` for defender dice
    - **Expected output**: Returns a list containing `3` and `2`

- **TC46: Returns below-minimum attacker dice count for controller and model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"0"` for attacker dice and `"1"` for defender dice
    - **Expected output**: Returns a list containing `0` and `1`

- **TC47: Returns above-maximum attacker dice count for controller and model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"4"` for attacker dice and `"1"` for defender dice
    - **Expected output**: Returns a list containing `4` and `1`

- **TC48: Returns below-minimum defender dice count for controller and model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"1"` for attacker dice and `"0"` for defender dice
    - **Expected output**: Returns a list containing `1` and `0`

- **TC49: Returns above-maximum defender dice count for controller and model validation** ( :white_check_mark: )
    - **State of the system**: Player enters `"1"` for attacker dice and `"3"` for defender dice
    - **Expected output**: Returns a list containing `1` and `3`

- **TC50: Returns non-numeric attacker dice input sentinel** ( :white_check_mark: )
    - **State of the system**: Player enters `"one"` for attacker dice
    - **Expected output**: Returns `List.of(Integer.MIN_VALUE)` to signal malformed attacker-dice input

- **TC51: Returns non-numeric defender dice input sentinel** ( :white_check_mark: )
    - **State of the system**: Player enters `"1"` for attacker dice and `"two"` for defender dice
    - **Expected output**: Returns `List.of(Integer.MIN_VALUE)` to signal malformed defender-dice input

---

### Method under test: `displayBattleResult(List<String> battleResult)`

- **TC52: Displays battle result exactly as provided** ( :white_check_mark: )
    - **State of the system**: Controller provides a formatted battle-result list containing attacker dice, defender dice, losses, and updated army counts
    - **Expected output**: Output displays the battle result exactly as provided

---

### Method under test: `promptAttackChoice()`

- **TC53: Returns yes attack choice** ( :white_check_mark: )
    - **State of the system**: Player is prompted whether to attack and enters `"yes"`
    - **Expected output**: Returns `"yes"`

- **TC54: Returns no attack choice** ( :white_check_mark: )
    - **State of the system**: Player is prompted whether to attack and enters `"no"`
    - **Expected output**: Returns `"no"`

- **TC55: Returns invalid attack choice for controller validation** ( :white_check_mark: )
    - **State of the system**: Player is prompted whether to attack and enters `"maybe"`
    - **Expected output**: Returns `"maybe"` so the controller can reject it and re-prompt

---

### Method under test: `promptCaptureArmyCount(String attackerName, String defenderName)`

- **TC56: Returns zero capture movement count for controller/model validation** ( :white_check_mark: )
    - **State of the system**: Player is prompted for capture movement and enters `"0"`
    - **Expected output**: Returns `"0"` so the capture flow can reject moving zero armies

- **TC57: Returns one capture movement count** ( :white_check_mark: )
    - **State of the system**: Player is prompted for capture movement and enters `"1"`
    - **Expected output**: Returns `"1"`

- **TC58: Returns multiple capture movement count** ( :white_check_mark: )
    - **State of the system**: Player is prompted for capture movement and enters `"3"`
    - **Expected output**: Returns `"3"`

- **TC59: Returns negative capture movement count for controller/model validation** ( :white_check_mark: )
    - **State of the system**: Player is prompted for capture movement and enters `"-1"`
    - **Expected output**: Returns `"-1"` so the capture flow can reject a negative movement

- **TC60: Returns non-numeric capture movement count for controller validation** ( :white_check_mark: )
    - **State of the system**: Player is prompted for capture movement and enters `"two"`
    - **Expected output**: Returns `"two"` so the controller can reject it without calling the model

---

### Method under test: `displayNoValidAttacks()`

- **TC61: Displays no valid attacks message** ( :white_check_mark: )
    - **State of the system**: Controller determines the current player has no valid attacks
    - **Expected output**: Output contains `"No valid attacks available."`

---

### Method under test: `displayTerritoryCaptured(String attackerName, String defenderName, int movedArmies)`

- **TC62: Displays territory capture with one moved army**
    - **State of the system**: `"Alaska"` captures `"Alberta"` and `1` army is moved
    - **Expected output**: Output contains attacking territory, defending territory, and moved army count

- **TC63: Displays territory capture with multiple moved armies**
    - **State of the system**: `"Alaska"` captures `"Alberta"` and `3` armies are moved
    - **Expected output**: Output contains attacking territory, defending territory, and moved army count

---

### Method under test: `displayRiskCardAwarded(String playerName)`

- **TC64: Displays Risk card awarded message**
    - **State of the system**: Current player captured at least one territory and receives a card
    - **Expected output**: Output contains player name and states that a Risk card was awarded
