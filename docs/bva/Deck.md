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

---

### Method under test: `size()`

- **TC7: Returns 44 on fresh deck** ( implemented in TC1 )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Returns `44`

---

### Method under test: `isEmpty()`

- **TC8: Returns false on fresh deck** ( :white_check_mark: )
    - **State of the system**: Freshly constructed `Deck`
    - **Expected output**: Returns `false`

---

### Method under test: `shuffle()`

- **TC9: Deck still contains 44 cards after shuffle** ( :white_check_mark: )
    - **State of the system**: `Deck` constructed then `shuffle()` called
    - **Expected output**: `size()` still returns `44`; no cards lost or duplicated

- **TC10: Deck order changes after shuffle** ( :white_check_mark: )
    - **State of the system**: Record card order before shuffle; call `shuffle()`
    - **Expected output**: Order of cards after shuffle differs from order before shuffle