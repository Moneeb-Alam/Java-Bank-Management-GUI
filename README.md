# Bank Management System 🏦

A complete GUI-based desktop application built with Java Swing. Currently, the system uses File Handling for data storage, ensuring complete persistence of user accounts, balances, and transaction histories. 

## Features 🚀
* **Secure Authentication:** 16-digit Card Number and 4-digit PIN validation.
* **Account Creation:** Comprehensive registration form with dropdowns for Date of Birth and Province.
* **Transaction Dashboard:** Interactive tabbed interface for depositing and withdrawing cash.
* **Real-time Balance Updates:** Accurate read/write operations using standard File I/O.
* **Crash Prevention:** Safe file rewriting using `Files.move()` and strict Array index bounds checking.

## Tech Stack 💻
* **Language:** Java (Core OOP Concepts)
* **GUI Framework:** Java Swing (`JFrame`, `JPanel`, `JTabbedPane`)
* **Storage:** File Handling (`Data.txt`) *[Future Update: Migration to MySQL]*
