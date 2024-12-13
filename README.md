# DobAgeCalc

#### [C. Manognan](https://github.com/Manognan2046)
---
**DobAgeCalc** is a versatile Java-based console application designed to calculate either a person's age based on their date of birth (DOB) or their date of birth based on a given age. The application supports multiple date formats and custom delimiters, offering flexibility for various use cases.

---

## Features

1. **Age Calculation**  
   - Input a Date of Birth (DOB) and a reference date (today's date or a custom date).  
   - The program calculates the age in years, months, and days.

2. **Date of Birth Calculation**  
   - Input an age and a reference date.  
   - The program calculates the date of birth.

3. **Flexible Input Formats**  
   - Supports different date formats:  
     - `YYYY-MM-DD` (International)  
     - `DD-MM-YYYY` (Indian)  
     - `MM-DD-YYYY` (USA)  
   - Accepts custom delimiters like `-`, `/`, or `.`.

---

## Installation

1. Clone this repository to your local machine:

```bash
git clone https://github.com/Manognan2046/DobAgeCalc.git
```
2. Navigate to the project directory:
```bash
cd DobAgeCalc
```
Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse) or compile and run the program via command line:
```bash
javac DobAgeCalc.java
java DobAgeCalc
```
---

## Input Rules

  1. **Date Formats Supported**:
      - YYYY-MM-DD (International)
      - DD-MM-YYYY (Indian)
      - MM-DD-YYYY (USA)

  2. **Delimiter Choices**:
      - -, /, .

  3. **Operation Types**:
      - DOB=<Date>: Enter a date to calculate the age.
      - AGE=<YY-MM-DD>: Enter an age to calculate the DOB.

  4. **Reference Date**:
      - Enter a specific reference date or type TODAY to use the current date.
---

## Usage

  1. Run the application and follow the prompts.
  2. The program will ask for:
     - DOB or AGE: Specify the type of input, either Date of Birth (DOB=YYYY-MM-DD) or Age (AGE=00YY-MM-DD).
     - Reference Date: Either choose today's date or input a custom reference date in the specified format.
     - Date Format: Enter the format (e.g., YYYY-MM-DD).
     - Delimiter: Specify the delimiter used in the date (e.g., -, /, or .).

### Example 1: Calculating Age from DOB

**Input**:

  - ```DOB=2000-01-15```
  - Do you want to take reference date as today?(y/n): ```y```
  - Enter today's date: ```2024-11-16```
  - Reference Date: ```2024-01-01```
  - Date Format: ```YYYY-MM-DD```
  - Delimiter: ```-```

**Output**:
```plaintext
Your age is (DD, MM, YYYY): 17 days, 11 months,  23 years.
```

### Example 2: Calculating DOB from Age

**Input**:

  - ```AGE=23-11-17```
  - Enter today's date: ```2024-11-16```
  - Date Format: ```YYYY-MM-DD```
  - Delimiter: ```-```

**Output**:
```plaintext
Your DOB is on (DD, MM, YYYY): 15-01-2000.
```
---

## Contribution Guidelines

  1. Fork the repository.
  2. Create a new branch for your feature or bugfix.
  3. Commit your changes with meaningful commit messages.
  4. Push the changes and create a pull request.
---
