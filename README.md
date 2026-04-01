# ☕ BlockChain Café — Instant VAT Settlement Prototype

An interactive browser-based prototype demonstrating how a private blockchain 
could modernize the German VAT (Mehrwertsteuer) system — eliminating monthly 
reporting, preventing fraud, and preserving consumer privacy.

Built as a live demonstration for Presentation Day, where visitors tap a card 
to buy snacks, watch the VAT split in real time, and leave with a tangible sense 
of what an efficient, blockchain-based tax system could look like.

---

## What it demonstrates

- **Instant VAT settlement** — no monthly Umsatzsteuervoranmeldung needed
- **Tamper-proof input VAT deduction** (Vorsteuerabzug) for business purchases
- **Consumer anonymity** via zero-knowledge proof simulation
- **Decentralized node network** — no central server
- **In-house vs. takeaway** VAT rate switching (19% / 7% MwSt)
- **EU scalability** — shows all 27 member states with their national VAT rates
- **Live blockchain ledger** — every transaction adds a real-time block

---

## How to use

1. Choose **🍽️ In-house** or **🥡 Takeaway** — VAT rates update instantly across all items
2. Add items to your cart using the **+** buttons
3. Tap **🔒 Pay Anonymous** (consumer) or **⚡ Pay + Deduct** (business/B2B)
4. Watch the ZK proof generate, nodes reach consensus, and VAT settle in under 700ms
5. See the new block added to the live chain

---

## VAT logic (legally accurate for Germany)

| Item type | In-house | Takeaway |
|---|---|---|
| Hot drinks (coffee, tea) | 19% | 19% |
| Cold drinks, food, snacks | 19% | 7% |
| Meals | 19% | 7% |

---

## Tech stack

- Vanilla HTML, CSS, JavaScript — zero dependencies, zero build step
- Runs entirely in the browser (no backend required for the demo)
- Fonts: Space Mono + Syne via Google Fonts

## Important note

This is a **conceptual prototype**. The blockchain, ZK proofs, and node 
network are simulated for demonstration purposes. A production system would 
require a real distributed ledger (e.g. Hyperledger Fabric), actual zk-SNARK 
cryptography, SEPA/CBDC payment integration, and a live Finanzamt API.

---

## Context

Developed as part of a university project exploring how blockchain technology 
could reshape tax infrastructure in Germany and across the European Union.
