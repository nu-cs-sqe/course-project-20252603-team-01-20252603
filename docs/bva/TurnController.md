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

- **TC36: Territory and continent armies are added when no trade-in is allowed** ( :x: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.NOT_ALLOWED`
    - **Expected output**: Controller adds territory-based armies and continent bonus armies, does not prompt for a card trade-in, and proceeds to reinforcement

- **TC37: Optional valid trade-in is processed when trade-in is allowed** ( :x: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.ALLOWED`; player enters a valid card selection
    - **Expected output**: Controller processes territory and continent armies, prompts for card trade-in, calls `handleCardTradeIn(...)`, and proceeds after the valid trade succeeds

- **TC38: Optional trade-in may be skipped when trade-in is allowed** ( :x: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.ALLOWED`; player skips card trade-in
    - **Expected output**: Controller processes territory and continent armies, prompts for card trade-in once, does not add trade-in armies, and proceeds to reinforcement

- **TC39: Invalid optional trade-in re-prompts when trade-in is allowed** ( :x: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.ALLOWED`; player first enters an invalid card selection
    - **Expected output**: Error message is displayed; controller re-prompts for card trade-in and proceeds only after a valid trade or explicit skip

- **TC40: Required trade-in accepts valid card selection** ( :x: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.REQUIRED`; player enters a valid card selection
    - **Expected output**: Controller processes territory and continent armies, requires card trade-in, and proceeds only after a valid trade succeeds

- **TC41: Required trade-in rejects skip and re-prompts** ( :x: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.REQUIRED`; player attempts to skip card trade-in
    - **Expected output**: Error message is displayed; controller re-prompts and does not proceed until a valid trade is completed

- **TC42: Required trade-in rejects invalid selection and re-prompts** ( :x: )
    - **State of the system**: Current player starts turn; `checkCardTradeInPossibility()` returns `TradeInPossibility.REQUIRED`; player enters an invalid card selection
    - **Expected output**: Error message is displayed; controller re-prompts and does not proceed until a valid trade is completed

- **TC43: Updated available armies are displayed after armies-to-add phase** ( :x: )
    - **State of the system**: Territory, continent, and optional card trade-in processing are complete
    - **Expected output**: Controller displays the current player's available armies before reinforcement placement begins
