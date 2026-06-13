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