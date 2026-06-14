## TurnController BVA

---

### Method under test: `handleReinforcement()`

- **TC30: Successful one-Infantry placement updates state and display** ( :x: )
    - **State of the system**: Current player has available armies; player enters `"Alaska 1 0 0"` for a territory they own
    - **Expected output**: Controller passes the placement to the model; model accepts it; updated territories and remaining armies are displayed

- **TC31: Successful mixed-type placement is passed to model** ( :x: )
    - **State of the system**: Current player enters `"Alaska 15 2 3"`
    - **Expected output**: Controller builds a pieces map with Infantry, Cavalry, and Artillery counts and passes it to the model

- **TC32: Invalid territory re-prompts same player** ( :x: )
    - **State of the system**: Current player enters a territory they do not own
    - **Expected output**: Error message is displayed; reinforcement loop continues

- **TC33: Invalid army count re-prompts same player** ( :x: )
    - **State of the system**: Current player enters zero, negative, or too many armies
    - **Expected output**: Error message is displayed; reinforcement loop continues

- **TC34: Reinforcement continues when one army value remains** ( :x: )
    - **State of the system**: Current player has exactly one Infantry of army value remaining
    - **Expected output**: Controller continues prompting for reinforcement placement

- **TC35: Reinforcement stops when no army value remains** ( :x: )
    - **State of the system**: Current player's available reinforcement pool is empty
    - **Expected output**: Reinforcement loop ends and the turn can proceed toward attack phase

---

### Method under test: `handleArmiesToAdd()`

- **TC36: Territory and continent armies are added when no trade-in is allowed** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.NOT_ALLOWED`
    - **Expected output**: Controller adds territory-based armies and continent bonus armies, does not prompt for a card trade-in, and proceeds to reinforcement

- **TC37: Optional valid trade-in is processed when trade-in is allowed** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.ALLOWED`; player enters a valid card selection
    - **Expected output**: Controller processes territory and continent armies, prompts for card trade-in, calls `handleCardTradeIn(...)`, and proceeds after the valid trade succeeds

- **TC38: Optional trade-in may be skipped when trade-in is allowed** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.ALLOWED`; player skips card trade-in and `promptChooseCardsToTradeIn()` returns `List.of()`
    - **Expected output**: Controller processes territory and continent armies, prompts for card trade-in once, does not add trade-in armies, and proceeds to reinforcement

- **TC39: Malformed optional trade-in input re-prompts when trade-in is allowed** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.ALLOWED`; player first enters malformed card input and `promptChooseCardsToTradeIn()` returns `List.of(Integer.MIN_VALUE)`
    - **Expected output**: Error message is displayed; controller re-prompts for card trade-in and proceeds only after a valid trade or explicit skip

- **TC40: Numeric-but-invalid optional trade-in re-prompts when trade-in is allowed** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.ALLOWED`; player first enters a numeric card selection that `handleCardTradeIn(...)` rejects
    - **Expected output**: Error message is displayed; controller re-prompts for card trade-in and proceeds only after a valid trade or explicit skip

- **TC41: Required trade-in accepts valid card selection** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.REQUIRED`; player enters a valid card selection
    - **Expected output**: Controller processes territory and continent armies, requires card trade-in, and proceeds only after a valid trade succeeds

- **TC42: Required trade-in rejects skip and re-prompts** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.REQUIRED`; player attempts to skip card trade-in and `promptChooseCardsToTradeIn()` returns `List.of()`
    - **Expected output**: Error message is displayed; controller re-prompts and does not proceed until a valid trade is completed

- **TC43: Required trade-in rejects malformed input and re-prompts** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.REQUIRED`; player enters malformed card input and `promptChooseCardsToTradeIn()` returns `List.of(Integer.MIN_VALUE)`
    - **Expected output**: Error message is displayed; controller re-prompts and does not proceed until a valid trade is completed

- **TC44: Required trade-in rejects numeric-but-invalid selection and re-prompts** ( :white_check_mark: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.REQUIRED`; player enters a numeric card selection that `handleCardTradeIn(...)` rejects
    - **Expected output**: Error message is displayed; controller re-prompts and does not proceed until a valid trade is completed

- **TC45: Updated available armies are displayed after armies-to-add phase** ( :white_check_mark: )
    - **State of the system**: Territory, continent, and optional card trade-in processing are complete
    - **Expected output**: Controller displays the current player's available armies before reinforcement placement begins

---

### Method under test: `handleAttackPhase(Player player)`

- **TC46: Valid attack resolves one battle and displays result** ( :white_check_mark: )
    - **State of the system**: Current player selects valid attacking and defending territories; selected dice counts are valid; model returns a battle result
    - **Expected output**: Controller validates territories and dice, executes one battle, displays the battle result, and returns from the attack phase

- **TC47: Invalid attacking territory re-prompts for territories** ( :white_check_mark: )
    - **State of the system**: Model raises `IllegalArgumentException` with message `"Current player must own the attacking territory."` during territory validation, then the player enters valid territories
    - **Expected output**: Controller displays the model error message and re-prompts for attacking and defending territories

- **TC48: Invalid defending territory re-prompts for territories** ( :white_check_mark: )
    - **State of the system**: Model raises `IllegalArgumentException` with message `"Defending territory must be owned by another player."` or `"Attacking and defending territories must be adjacent."`, then the player enters valid territories
    - **Expected output**: Controller displays the model error message and re-prompts for attacking and defending territories

- **TC49: Malformed dice input re-prompts for dice** ( :x: )
    - **State of the system**: View returns `List.of(Integer.MIN_VALUE)` for dice input, then returns valid dice counts
    - **Expected output**: Controller displays `"Invalid dice input."` and re-prompts for dice

- **TC50: Incorrect number of dice entries re-prompts for dice** ( :x: )
    - **State of the system**: View returns a dice-count list with fewer or more than two values, then returns valid dice counts
    - **Expected output**: Controller displays `"Invalid dice input."` and re-prompts for dice

- **TC51: Invalid dice count re-prompts for dice** ( :x: )
    - **State of the system**: Model raises `IllegalArgumentException` during dice validation, then the player enters valid dice counts
    - **Expected output**: Controller displays the model error message and re-prompts for dice

- **TC52: Battle result is displayed exactly once after successful execution** ( :x: )
    - **State of the system**: Territory and dice validation succeed; model returns one battle result
    - **Expected output**: Controller calls `displayBattleResult(...)` exactly once with the returned battle result
