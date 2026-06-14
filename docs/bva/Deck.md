# BVA Analysis — `Deck`

### Method under test: `Deck()` *(constructor)*

- **TC1: Deck contains exactly 44 cards** ( :white_check_mark: )
    - **State of the system**: `Deck` constructed; no other calls made
    - **Expected output**: `size()` returns `44`

- **TC2: Deck contains exactly 42 territory cards** ( :white_check_mark: )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Count of cards where `isWild() == false` equals `42`

- **TC3: Deck contains exactly 2 wild cards** ( :white_check_mark: )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Count of cards where `isWild() == true` equals `2`

- **TC4: Every territory card has a non-null territory** ( :white_check_mark: )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Every card where `isWild() == false` has a non-null territory

- **TC5: Every territory appears on exactly one card** ( :white_check_mark: )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: No two non-wild cards share the same territory; 42 distinct territories across the 42 territory cards

- **TC6: Each CardType (INFANTRY, CAVALRY, ARTILLERY) appears on at least one territory card** ( :white_check_mark: )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: At least one card of each of the three non-wild CardTypes exists in the deck

- **TC7: Discard pile starts empty**
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: `getDiscardPileSize()` returns `0`

---

### Method under test: `size()`

- **TC8: Returns 44 on fresh deck** ( implemented in TC1 )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Returns `44`

---

### Method under test: `isEmpty()`

- **TC9: Returns false on fresh deck** ( :white_check_mark: )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Returns `false`

---

### Method under test: `shuffle()`

- **TC10: Deck still contains 44 cards after shuffle** ( :white_check_mark: )
    - **State of the system**: `Deck` constructed then `shuffle()` called
    - **Expected output**: `size()` still returns `44`; no cards lost or duplicated

- **TC11: Deck order changes after shuffle** ( :white_check_mark: )
    - **State of the system**: Record card order before shuffle; call `shuffle()`
    - **Expected output**: Order of cards after shuffle differs from order before shuffle

---

### Method under test: `drawCard()`

- **TC12: Draw one card from a full draw pile**
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Returns a non-null `RiskCard`; `size()` decreases from `44` to `43`; `getDiscardPileSize()` remains `0`

- **TC13: Draw the last card from the draw pile**
    - **State of the system**: `Deck` has exactly one card remaining in the draw pile and discard pile is empty
    - **Expected output**: Returns a non-null `RiskCard`; `size()` becomes `0`; `isEmpty()` returns `true`

- **TC14: Draw from empty draw pile with one discarded card available**
    - **State of the system**: Draw pile is empty; discard pile contains exactly one `RiskCard`
    - **Expected output**: Discard pile is moved into the draw pile, discard pile becomes empty, draw pile is shuffled, one card is drawn, `size()` returns `0`, and `getDiscardPileSize()` returns `0`

- **TC15: Draw from empty draw pile with multiple discarded cards available**
    - **State of the system**: Draw pile is empty; discard pile contains multiple `RiskCard` objects
    - **Expected output**: Discard pile is moved into the draw pile, discard pile becomes empty, draw pile is shuffled, one card is drawn, and `size()` decreases by one from the number of discarded cards

- **TC16: Draw from empty draw pile with empty discard pile is rejected**
    - **State of the system**: Draw pile is empty; discard pile is empty
    - **Expected output**: `IllegalStateException` is raised with message `"Cannot draw a card because both the draw pile and discard pile are empty."`

---

### Method under test: `discardCards(List<RiskCard> cardsToDiscard)`

- **TC17: Discard one card into an empty discard pile**
    - **State of the system**: Freshly constructed `Deck`; `cardsToDiscard` contains one `RiskCard`
    - **Expected output**: `getDiscardPileSize()` returns `1`; draw pile size is unchanged

- **TC18: Discard multiple cards into an empty discard pile**
    - **State of the system**: Freshly constructed `Deck`; `cardsToDiscard` contains three `RiskCard` objects
    - **Expected output**: `getDiscardPileSize()` returns `3`; draw pile size is unchanged

- **TC19: Discard zero cards leaves discard pile unchanged**
    - **State of the system**: Freshly constructed `Deck`; `cardsToDiscard` is an empty list
    - **Expected output**: `getDiscardPileSize()` remains `0`; draw pile size is unchanged

- **TC20: Discard cards after discard pile already contains cards**
    - **State of the system**: Discard pile already contains one `RiskCard`; `cardsToDiscard` contains three more `RiskCard` objects
    - **Expected output**: `getDiscardPileSize()` returns `4`; draw pile size is unchanged

---

### Method under test: `getDiscardPileSize()`

- **TC21: Returns zero for fresh deck** ( implemented in TC7 )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Returns `0`

- **TC22: Returns current discard pile count after cards are discarded** ( implemented in TC17 and TC18 )
    - **State of the system**: Cards have been added to the discard pile with `discardCards(...)`
    - **Expected output**: Returns the number of cards currently stored in the discard pile

---

### Method under test: `reinitializeDrawPileFromDiscardPile()`

- **TC23: Reinitialize draw pile from one discarded card**
    - **State of the system**: Draw pile is empty; discard pile contains one `RiskCard`
    - **Expected output**: Draw pile contains that one card, discard pile becomes empty, and `size()` returns `1`

- **TC24: Reinitialize draw pile from multiple discarded cards**
    - **State of the system**: Draw pile is empty; discard pile contains multiple `RiskCard` objects
    - **Expected output**: Draw pile contains all discarded cards, discard pile becomes empty, draw pile is shuffled, and `size()` returns the previous discard pile size

- **TC25: Reinitialize when discard pile is empty leaves draw pile empty**
    - **State of the system**: Draw pile is empty; discard pile is empty
    - **Expected output**: `size()` remains `0`; `getDiscardPileSize()` remains `0`
