# BVA Analysis — `HumanPlayer`


### Method under test: `HumanPlayer(String name, PlayerColor color, int startingInfantry)`

- **TC1: Constructs player with minimum setup infantry** ( :white_check_mark: )
    - **State of the system**: `new HumanPlayer("Player 1", PlayerColor.RED, 20)` called for a six-player game
    - **Expected output**: Object is created with name `"Player 1"`, color `RED`, and `20` available Infantry

- **TC2: Constructs player with maximum setup infantry** ( :white_check_mark: )
    - **State of the system**: `new HumanPlayer("Player 1", PlayerColor.BLUE, 35)` called for a three-player game
    - **Expected output**: Object is created with name `"Player 1"`, color `BLUE`, and `35` available Infantry

---

### Method under test: `getName()`

- **TC3: Returns registered player name** ( :white_check_mark: )
    - **State of the system**: Human player constructed with name `"Player 1"`
    - **Expected output**: Returns `"Player 1"`

### Method under test: `addTerritory(Territory territory)`

- **TC8: Add first claimed territory** ( :white_check_mark: )
    - **State of the system**: Human player owns no territories; `addTerritory(alaska)` is called
    - **Expected output**: Player territory count becomes `1`; `ownsTerritory(alaska)` returns `true`

- **TC9: Add another claimed territory** ( :white_check_mark: )
    - **State of the system**: Human player already owns one territory; `addTerritory(alberta)` is called
    - **Expected output**: Player territory count becomes `2`; `ownsTerritory(alberta)` returns `true`; previously owned territory is still owned

---

### Method under test: `ownsTerritory(Territory territory)`

- **TC10: Returns true for owned territory** ( implemented in TC8 )
    - **State of the system**: `addTerritory(alaska)` has been called
    - **Expected output**: `ownsTerritory(alaska)` returns `true`

- **TC11: Returns false for territory not owned by player** ( :white_check_mark: )
    - **State of the system**: Human player owns `alaska`; `alberta` has not been added to this player
    - **Expected output**: `ownsTerritory(alberta)` returns `false`

---

### Method under test: `getTerritoryCount()`

- **TC12: Returns zero before any territory is claimed** ( :white_check_mark: )
    - **State of the system**: Human player has just been constructed
    - **Expected output**: `getTerritoryCount()` returns `0`

- **TC13: Returns one after first territory is claimed** ( implemented in TC8 )
    - **State of the system**: One territory has been added to the player
    - **Expected output**: `getTerritoryCount()` returns `1`

- **TC14: Returns more than one after multiple territories are claimed** ( implemented in TC9 )
    - **State of the system**: Two territories have been added to the player
    - **Expected output**: `getTerritoryCount()` returns `2`

---

### Method under test: `addArmies(HashMap<ArmyType, Integer> armiesToAdd)`

- **TC15: Add one Infantry to available armies** ( :white_check_mark: )
    - **State of the system**: Human player has available armies containing `INFANTRY -> 0`; `addArmies()` is called with a map containing `INFANTRY -> 1`
    - **Expected output**: Player available armies are updated to contain exactly one Infantry

- **TC16: Add multiple Infantry to available armies** ( :white_check_mark: )
    - **State of the system**: Human player has available armies containing `INFANTRY -> 0`; `addArmies()` is called with a map containing `INFANTRY -> 20`
    - **Expected output**: Player available armies are updated to contain exactly twenty Infantry
  
---

### Method under test: `removeArmies(HashMap<ArmyType, Integer> armiesToRemove)`

- **TC17: Remove one Infantry from one available Infantry** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 1`; `armiesToRemove` contains `INFANTRY -> 1`
    - **Expected output**: Player has zero total available army value remaining

- **TC18: Remove one Infantry from multiple available Infantry and normalize remaining value** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 20`; `armiesToRemove` contains `INFANTRY -> 1`
    - **Expected output**: Player has 19 total army value remaining, normalized into available army pieces

- **TC19: Remove one Cavalry using Infantry value** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 5`; `armiesToRemove` contains `CAVALRY -> 1`
    - **Expected output**: Player has zero total available army value remaining

- **TC20: Remove one Cavalry from one Artillery and make change** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `ARTILLERY -> 1`; `armiesToRemove` contains `CAVALRY -> 1`
    - **Expected output**: Player has 5 total army value remaining, normalized as one Cavalry or equivalent available pieces

- **TC21: Remove Infantry from one Cavalry and make change** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `CAVALRY -> 1`; `armiesToRemove` contains `INFANTRY -> 3`
    - **Expected output**: Player has 2 total army value remaining, normalized as two Infantry

---

### Method under test: `hasAvailableArmies(HashMap<ArmyType, Integer> requiredArmies)`

- **TC22: Returns true when exact Infantry count is available** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 1`; `requiredArmies` contains `INFANTRY -> 1`
    - **Expected output**: Returns `true`

- **TC23: Returns true when exact total equivalent value is available through Infantry conversion** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 15`; `requiredArmies` contains `CAVALRY -> 1` and `ARTILLERY -> 1`
    - **Expected output**: Returns `true` because the player has 15 total army value available

- **TC24: Returns true when exact total equivalent value is available through Artillery conversion** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `ARTILLERY -> 1`; `requiredArmies` contains `CAVALRY -> 1`
    - **Expected output**: Returns `true` because one Artillery has enough value to cover one Cavalry

- **TC25: Returns false when required total value is greater than available total value** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 4`; `requiredArmies` contains `CAVALRY -> 1`
    - **Expected output**: Returns `false`

- **TC26: Returns false when player has zero total available army value** ( :white_check_mark: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 0`, `CAVALRY -> 0`, and `ARTILLERY -> 0`; `requiredArmies` contains `INFANTRY -> 1`
    - **Expected output**: Returns `false`

---

### Method under test: `getAvailableArmies()`

- **TC21: Returns available army map as display string when Infantry is available** ( :white_check_mark: )
    - **State of the system**: Player available armies contain `INFANTRY -> 20`
    - **Expected output**: Returns a string containing `INFANTRY` and `20`

- **TC22: Returns available army map as display string when no Infantry is available** ( :white_check_mark: )
    - **State of the system**: Player available armies contain `INFANTRY -> 0`
    - **Expected output**: Returns a string containing `INFANTRY` and `0`

---

### Method under test: `addArmiesToAvailableBasedOnTerritories()`

- **TC27: Zero territories is rejected because the player should be eliminated** ( :white_check_mark: )
    - **State of the system**: Human player owns 0 territories
    - **Expected output**: `IllegalStateException` is raised with message `"Player cannot own 0 territories and play a turn because they have been eliminated."`; available armies are unchanged

- **TC28: One territory receives minimum reinforcement armies** ( :white_check_mark: )
    - **State of the system**: Human player owns 1 territory and has 0 available Infantry
    - **Expected output**: Player receives 3 Infantry

- **TC29: Two territories receives minimum reinforcement armies** ( :white_check_mark: )
    - **State of the system**: Human player owns 2 territories and has 0 available Infantry
    - **Expected output**: Player receives 3 Infantry

- **TC30: Eight territories receives minimum reinforcement armies** ( :white_check_mark: )
    - **State of the system**: Human player owns 8 territories and has 0 available Infantry
    - **Expected output**: Player receives 3 Infantry

- **TC31: Nine territories receives three reinforcement armies** ( :white_check_mark: )
    - **State of the system**: Human player owns 9 territories and has 0 available Infantry
    - **Expected output**: Player receives 3 Infantry

- **TC32: Ten territories rounds down to three reinforcement armies** ( :white_check_mark: )
    - **State of the system**: Human player owns 10 territories and has 0 available Infantry
    - **Expected output**: Player receives 3 Infantry

- **TC33: Eleven territories rounds down to three reinforcement armies** ( :white_check_mark: )
    - **State of the system**: Human player owns 11 territories and has 0 available Infantry
    - **Expected output**: Player receives 3 Infantry

- **TC34: Twelve territories receives four reinforcement armies** ( :white_check_mark: )
    - **State of the system**: Human player owns 12 territories and has 0 available Infantry
    - **Expected output**: Player receives 4 Infantry

- **TC35: Forty-one territories receives thirteen reinforcement armies** ( :white_check_mark: )
    - **State of the system**: Human player owns 41 territories and has 0 available Infantry
    - **Expected output**: Player receives 13 Infantry

- **TC36: Forty-two territories is rejected because the game should already be won** ( :white_check_mark: )
    - **State of the system**: Human player owns 42 territories
    - **Expected output**: `IllegalStateException` is raised with message `"Player cannot own 42 territories and play a turn because they should have already won."`; available armies are unchanged

- **TC37: More than forty-two territories is rejected as an invalid game state** ( :white_check_mark: )
    - **State of the system**: Human player owns 43 territories
    - **Expected output**: `IllegalStateException` is raised with message `"Player cannot own 43 territories because there are only 42 territories on the board."`; available armies are unchanged

---

### Method under test: `tradeCardsAndAddArmies(List<Integer> cardIndices, Deck deck, int numSetsTradedIn)`

- **TC38: Fewer than three selected cards is rejected** ( :white_check_mark: )
    - **State of the system**: Human player has 3 cards; selected indices contain 2 cards
    - **Expected output**: Returns `false`; available armies, card hand, and deck discard pile are unchanged

- **TC39: More than three selected cards is rejected** ( :white_check_mark: )
    - **State of the system**: Human player has 4 cards; selected indices contain 4 cards
    - **Expected output**: Returns `false`; available armies, card hand, and deck discard pile are unchanged

- **TC40: Selected index below one is rejected** ( :white_check_mark: )
    - **State of the system**: Human player has 3 cards; selected indices include `0`
    - **Expected output**: Returns `false`; available armies, card hand, and deck discard pile are unchanged

- **TC41: Selected index above hand size is rejected** ( :white_check_mark: )
    - **State of the system**: Human player has 3 cards; selected indices include `4`
    - **Expected output**: Returns `false`; available armies, card hand, and deck discard pile are unchanged

- **TC42: Duplicate selected indices are rejected** ( :white_check_mark: )
    - **State of the system**: Human player has 3 cards; selected indices are `[1, 1, 2]`
    - **Expected output**: Returns `false`; available armies, card hand, and deck discard pile are unchanged

- **TC43: Three Infantry cards is accepted** ( :white_check_mark: )
    - **State of the system**: Selected cards are Infantry, Infantry, Infantry; `numSetsTradedIn = 0`
    - **Expected output**: Returns `true`; player receives 4 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC44: Three Cavalry cards is accepted** ( :white_check_mark: )
    - **State of the system**: Selected cards are Cavalry, Cavalry, Cavalry; `numSetsTradedIn = 0`
    - **Expected output**: Returns `true`; player receives 4 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC45: Three Artillery cards is accepted** ( :white_check_mark: )
    - **State of the system**: Selected cards are Artillery, Artillery, Artillery; `numSetsTradedIn = 0`
    - **Expected output**: Returns `true`; player receives 4 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC46: One Infantry, one Cavalry, and one Artillery is accepted** ( :white_check_mark: )
    - **State of the system**: Selected cards are Infantry, Cavalry, Artillery; `numSetsTradedIn = 0`
    - **Expected output**: Returns `true`; player receives 4 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC47: Two matching cards and one different non-wild card is rejected** ( :white_check_mark: )
    - **State of the system**: Selected cards are Infantry, Infantry, Cavalry
    - **Expected output**: Returns `false`; available armies, card hand, and deck discard pile are unchanged

- **TC48: One wild card with two matching non-wild cards is accepted** ( :white_check_mark: )
    - **State of the system**: Selected cards are Wild, Infantry, Infantry; `numSetsTradedIn = 0`
    - **Expected output**: Returns `true`; player receives 4 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC49: One wild card with two different non-wild cards is accepted** ( :white_check_mark: )
    - **State of the system**: Selected cards are Wild, Infantry, Cavalry; `numSetsTradedIn = 0`
    - **Expected output**: Returns `true`; player receives 4 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC50: Two wild cards are rejected** ( :white_check_mark: )
    - **State of the system**: Selected cards include two Wild cards
    - **Expected output**: Returns `false`; available armies, card hand, and deck discard pile are unchanged

- **TC51: First trade-in gives 4 armies** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 0`
    - **Expected output**: Player receives 4 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC52: Second trade-in gives 6 armies** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 1`
    - **Expected output**: Player receives 6 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC53: Third trade-in gives 8 armies** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 2`
    - **Expected output**: Player receives 8 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC54: Fourth trade-in gives 10 armies** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 3`
    - **Expected output**: Player receives 10 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC55: Fifth trade-in gives 12 armies** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 4`
    - **Expected output**: Player receives 12 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC56: Sixth trade-in gives 15 armies** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 5`
    - **Expected output**: Player receives 15 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC57: Seventh trade-in gives 20 armies** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 6`
    - **Expected output**: Player receives 20 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC58: Fourteenth trade-in gives 55 armies as the maximum legal trade-in** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 13`
    - **Expected output**: Returns `true`; player receives 55 Infantry; selected cards are removed from hand and added to the deck discard pile

- **TC59: Fifteenth trade-in is rejected as impossible with forty-four cards** ( :white_check_mark: )
    - **State of the system**: Valid set selected; `numSetsTradedIn = 14`
    - **Expected output**: `IllegalArgumentException` is raised with message `"Cannot trade cards after 14 sets because a 44-card deck supports at most 14 traded sets."`; available armies, card hand, and deck discard pile are unchanged

---

### Method under test: `removeTerritory(Territory territory)`

- **TC60: Remove the only owned territory** (:white_check_mark:)
    - **State of the system**: Human player owns exactly `alaska`; `removeTerritory(alaska)` is called
    - **Expected output**: Player territory count becomes `0`; `ownsTerritory(alaska)` returns `false`

- **TC61: Remove one territory while another remains** (:white_check_mark:)
    - **State of the system**: Human player owns `alaska` and `alberta`; `removeTerritory(alaska)` is called
    - **Expected output**: Player territory count becomes `1`; `ownsTerritory(alaska)` returns `false`; `ownsTerritory(alberta)` returns `true`

- **TC62: Removing a territory not owned by the player leaves territories unchanged** (:white_check_mark:)
    - **State of the system**: Human player owns `alaska`; `removeTerritory(alberta)` is called
    - **Expected output**: Player territory count remains `1`; `ownsTerritory(alaska)` returns `true`; `ownsTerritory(alberta)` returns `false`

---

### Method under test: `isEliminated()`

- **TC63: New human player is not eliminated** (:white_check_mark:)
    - **State of the system**: Human player has just been constructed
    - **Expected output**: Returns `false`

- **TC64: Human player is eliminated after markEliminated is called** ( implemented in TC65 )
    - **State of the system**: `markEliminated()` has been called
    - **Expected output**: Returns `true`

---

### Method under test: `markEliminated()`

- **TC65: Mark active player as eliminated** (:white_check_mark:)
    - **State of the system**: Human player is active and `isEliminated()` returns `false`
    - **Expected output**: `isEliminated()` returns `true`

- **TC66: Mark already eliminated player as eliminated again** (:x:)
    - **State of the system**: `markEliminated()` has already been called once
    - **Expected output**: `isEliminated()` remains `true`; no exception is raised

---

### Method under test: `addCard(RiskCard card)`

- **TC67: Add first Risk card to empty hand** (:x:)
    - **State of the system**: Human player has zero cards; `addCard(card)` is called
    - **Expected output**: Player card count becomes `1`; `getAvailableCards()` contains `card`

- **TC68: Add another Risk card to non-empty hand** (:x:)
    - **State of the system**: Human player already has one card; `addCard(secondCard)` is called
    - **Expected output**: Player card count becomes `2`; both cards are present in `getAvailableCards()`

---

### Method under test: `addCards(List<RiskCard> cardsToAdd)`

- **TC69: Add zero cards to empty hand** (:x:)
    - **State of the system**: Human player has zero cards; `addCards(List.of())` is called
    - **Expected output**: Player card count remains `0`

- **TC70: Add one card to empty hand** (:x:)
    - **State of the system**: Human player has zero cards; `addCards(List.of(card))` is called
    - **Expected output**: Player card count becomes `1`; `getAvailableCards()` contains `card`

- **TC71: Add multiple cards to non-empty hand** (:x:)
    - **State of the system**: Human player already has one card; `addCards(List.of(secondCard, thirdCard))` is called
    - **Expected output**: Player card count becomes `3`; all three cards are present in `getAvailableCards()`

---

### Method under test: `removeAllCards()`

- **TC72: Remove all cards from empty hand** (:x:)
    - **State of the system**: Human player has zero cards; `removeAllCards()` is called
    - **Expected output**: Returns an empty list; player card count remains `0`

- **TC73: Remove all cards from one-card hand** (:x:)
    - **State of the system**: Human player has exactly one card; `removeAllCards()` is called
    - **Expected output**: Returns a list containing that card; player card count becomes `0`

- **TC74: Remove all cards from multi-card hand** (:x:)
    - **State of the system**: Human player has multiple cards; `removeAllCards()` is called
    - **Expected output**: Returns all cards that were in the hand; player card count becomes `0`; returned list size equals the previous card count
