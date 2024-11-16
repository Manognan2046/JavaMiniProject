import java.time.LocalDate;
import java.util.Scanner;

public class DobAgeCalc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        
        // Displaying input format instructions to the user
        System.out.println("""
                The Date format could be\s
                i) YYYYdlcMMdlcDD (international style)
                ii) DDdlcMMdlcYYYY (indian style)
                iii) MMdlcDDdlcYYYY (USA)
                Acceptable DOB format: "DOB=YYYYdlcMMdlcDD"
                Acceptable AGE format: "AGE=00YYdlcMMdlcDD"
                dlc is one of the tokens: '-', '/' , or '.'
                """);
        while (flag) {
            try {
                // Taking and validating user inputs
                String inp = removeWhitespace(takeInp());
                String ref = removeWhitespace(takeRef(inp));
                String format = removeWhitespace(takeFormat());
                String dlc = removeWhitespace(takeDlc());
                
                // Checking if the delimiter matches the format
                if (!checkDlcFormat(format, dlc)) {
                    System.out.println("The delimiter in the date format does not match the entered delimiter or the entered format is wrong. Please enter all the values again.");
                    continue;
                }
                
                // Extracting the actual date or age input
                String[] splitInput = inp.split("=");
                String actualInp = splitInput[1];
                
                // Parsing the date or age parts
                int[] dateParts = dateFinder(actualInp, format, dlc);
                int[] curParts = dateFinder(ref, format, dlc);

                // Checking whether input is DOB or AGE and calculating accordingly
                if (inp.charAt(0) == 'D' || inp.charAt(0) == 'd') {
                    ageFinder(dateParts, curParts);
                } else if (inp.charAt(0) == 'A' || inp.charAt(0) == 'a') {
                    dobFinder(dateParts, curParts, dlc);
                }
                flag = false;

            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Please enter valid DOB or AGE.\nFollow the input rules.");
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter valid dates.");
            }
            catch (NullPointerException e) {
                System.out.println("Please give a non empty input.");
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }

    // Method to validate input day, month, and year
    public static boolean isValidInput(int day, int month, int year) {
        if (month < 1 || month > 12) {
            System.out.println("Error: Month must be between 1 and 12.");
            return false;
        }
        if (day < 1 || day > 31) {
            System.out.println("Error: Day must be between 1 and 31.");
            return false;
        }
        if (year > LocalDate.now().getYear() + 10) {
            System.out.println("Error: Year must be under " + (LocalDate.now().getYear() + 10) + ".");
            return false;
        }
        return isValidDate(day, month, year);
    }

    // Method to check if a date is valid considering the month and year
    public static boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12) {
            System.out.println("Error: Month must be between 1 and 12.");
            return false;
        }
        if (daysInMonth(month, year) == 31 && (day < 1 || day > 31)) {
            System.out.println("Error: Day must be between 1 and 31 for the provided month.");
            return false;
        }
        else if (daysInMonth(month, year) == 30 && (day < 1 || day > 30)) {
            System.out.println("Error: Day must be between 1 and 30 for the provided month.");
            return false;
        }
        switch (month) {
            case 2:
                if (isLeapYear(year)) {
                    if (day > 29) {
                        System.out.println("Error: February in a leap year can only have 29 days.");
                        return false;
                    }
                } else {
                    if (day > 28) {
                        System.out.println("Error: February can only have 28 days.");
                        return false;
                    }
                }
                break;
            case 4: case 6: case 9: case 11:
                if (day > 30) {
                    System.out.println("Error: The month can only have 30 days.");
                    return false;
                }
                break;
        }
        return true;
    }

    // Verifies if the format and delimiter are correct
    public static boolean checkDlcFormat(String format, String dlc) {
        int count = 0;
        for (char c : format.toCharArray()) {
            if (c == 'D' || c == 'd' || c == 'M' || c == 'm' || c == 'Y' || c == 'y' || c == '0') { }
            else if (c == dlc.charAt(0)) {
                count += 1;
            }
        }
        return count == 2;
    }

    // Removes all whitespace characters from a string
    public static String removeWhitespace(String input) {
        return input.replaceAll("\\s+", "");
    }

    // Prompts the user to enter DOB or AGE
    public static String takeInp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the DOB or AGE: ");
        String inp = sc.nextLine();
        if (inp.startsWith("DOB=") || inp.startsWith("AGE=") || inp.startsWith("dob=") || inp.startsWith("age=")){
            return inp;
        }
        else {
            System.out.println("Enter a valid DOB or AGE.");
            return takeInp();
        }
    }

    // Prompts the user to enter a reference date
    public static String takeRef(String inp) {
        Scanner sc = new Scanner(System.in);
        if (inp.startsWith("DOB=") || inp.startsWith("dob=")) {
            System.out.println("Do you want to take reference date as today?(y/n): ");
            String x = sc.nextLine();
            switch (x){
                case "n" : case "N" :
                    System.out.println("Enter the reference date: ");
                    return sc.nextLine();
                case "y" : case "Y" :
                    System.out.println("Enter today's date: ");
                    return sc.nextLine();
                default:
                    System.out.println("Enter a valid answer.");
                    takeRef(inp);
                    break;
            }
        }
        else {
            System.out.println("Enter today's date: ");
            return sc.nextLine();
        }
        return "";
    }

    // Prompts the user to enter the date format
    public static String takeFormat() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the date format: ");
        return sc.nextLine();
    }

    // Prompts the user to enter the delimiter
    public static String takeDlc() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the delimiter: ");
        String dlc = sc.nextLine();
        if (!dlc.equals("-") && !dlc.equals("/") && !dlc.equals(".")) {
            System.out.println("Invalid delimiter, please enter the delimiter again.");
            return takeDlc();
        }
        return dlc;
    }

    // Checks if a year is a leap year
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Returns the number of days in a given month of a year
    public static int daysInMonth(int month, int year) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> isLeapYear(year) ? 29 : 28;
            default -> throw new IllegalArgumentException("Invalid month: " + month);
        };
    }
    
    // Ensures the given date is before or equal to the reference date
    public static boolean checkDate(int userDay, int userMonth, int userYear, int currentDay, int currentMonth, int currentYear) {
        return userYear <= currentYear && (userYear != currentYear || userMonth <= currentMonth) && (userYear != currentYear || userMonth != currentMonth || userDay <= currentDay);
    }

    // Calculates the age based on DOB and the reference date
    public static void ageFinder(int[] dateParts, int[] curParts) {
        int userDay = dateParts[0], userMonth = dateParts[1], userYear = dateParts[2];
        int day = curParts[0], month = curParts[1], year = curParts[2];

        if (!checkDate(userDay, userMonth,userYear, day, month, year)) {
            throw new IllegalArgumentException("Entered date is invalid. Please enter a valid date again.");
        }

        if (!isValidInput(day, month, year)) {
            throw new IllegalArgumentException();
        }

        if (userDay > day) {
            month = month - 1;
            day = day + daysInMonth(month, year);
        }

        if (userMonth > month) {
            year = year - 1;
            month = month + 12;
        }

        int diffDay = day - userDay;
        int diffMonth = month - userMonth;
        int diffYear = year - userYear;

        if (diffMonth == 12) {
            diffYear += 1;
            diffMonth = 0;
        }

        if (diffDay >= 0 && diffMonth >= 0 && diffYear >= 0) {
            System.out.println("Your age is (DD, MM, YYYY): " + diffDay + " days, " + diffMonth + " months, " + diffYear + " years.");
        }
        else {
            throw new IllegalArgumentException("Entered date is after the reference/today's date. Please try again.");
        }
    }
    
    // Calculates the DOB based on AGE and the reference date
    public static void dobFinder(int[] ageParts, int[] curParts, String dlc) {
        int ageYears = ageParts[2];
        int ageMonths = ageParts[1];
        int ageDays = ageParts[0];

        int currentDay = curParts[0];
        int currentMonth = curParts[1];
        int currentYear = curParts[2];

        int birthYear = currentYear - ageYears;
        int birthMonth = currentMonth - ageMonths;
        int birthDay = currentDay - ageDays;

        if (birthMonth <= 0) {
            birthMonth += 12;
            birthYear--;
        }

        if (birthDay <= 0) {
            int prevMonth = birthMonth - 1;
            if (prevMonth <= 0) {
                prevMonth = 12;
                birthYear--;
            }
            birthDay += daysInMonth(prevMonth, birthYear);
            birthMonth--;
            if (birthMonth <= 0) {
                birthMonth += 12;
                birthYear--;
            }
        }

        if (!isValidDate(birthDay, birthMonth, birthYear)) {
            throw new IllegalArgumentException("Calculated date is invalid. Please check your input.");
        }

        System.out.println("Your DOB is on (DD, MM, YYYY): " + birthDay + dlc + birthMonth + dlc + birthYear + ".");
    }
    
    // Parses date parts from a given string based on the format and delimiter
    public static int[] dateFinder(String inp, String format, String dlc) {
        String[] parts = inp.split(dlc);
        int day, month, year;
        int dCount = 0, mCount = 0, yCount = 0;
        for (int i = 0; i < format.length(); i++) {
            char formatChar = format.charAt(i);

            if (formatChar == 'D' || formatChar == 'd') {
                dCount += 1;
            }
            else if (formatChar == 'M' || formatChar == 'm') {
                mCount += 1;
            }
            else if (formatChar == 'Y' || formatChar == 'y' || formatChar == '0') {
                yCount += 1;
            }
            else {
            }
        }
        if (dCount == 2 && mCount == 2 && yCount == 4) {}
        else throw new IllegalArgumentException("Date format and the specified input/reference do not match.");
        if (format.equalsIgnoreCase("dd" + dlc + "mm" + dlc + "yyyy") || format.equalsIgnoreCase("DD" + dlc + "MM" + dlc + "YYYY")) {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } else if (format.equalsIgnoreCase("mm" + dlc + "dd" + dlc + "yyyy") || format.equalsIgnoreCase("MM" + dlc + "DD" + dlc + "YYYY")) {
            month = Integer.parseInt(parts[0]);
            day = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } else if (format.equalsIgnoreCase("yyyy" + dlc + "mm" + dlc + "dd") || format.equalsIgnoreCase("YYYY" + dlc + "MM" + dlc + "DD")) {
            year = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            day = Integer.parseInt(parts[2]);
        } else {
            throw new IllegalArgumentException("Invalid date format.");
        }
        return new int[] { day, month, year };
    }
}
