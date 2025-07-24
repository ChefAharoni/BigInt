/*******************************************************************************
 * Name          : BigInt.java
 * Author        : Amit Aharoni
 * Version       : 1.0
 * Date          : July 18, 2025
 * Last modified : Put the date here.
 * Description   : Adds, subtracts, and multiplies two large integers
 *                 efficiently using Strings.
 ******************************************************************************/
public class BigInt {

    private static int checkValidity(String input) {
        int start = 0, len = input.length();
        if (len >= 1 && input.charAt(0) == '-') {
            if (len < 2) {
                return 1;
            }
            start = 1;
        }
        for (int i = start; i < len; i++) {
            char c = input.charAt(i);
            if (c < '0' || c > '9') {
                return i;
            }
        }
        return len > 0 ? -1 : 0;
    }

    // taken either negative, positive or zero
    private static void validateParameters(String str1, String str2)
            throws NumberFormatException {
        int index;
        if ((index = checkValidity(str1)) != -1) {
            throw new NumberFormatException(
                "Error: Invalid value for integer a. Problem at index " +
                index +  "." + System.lineSeparator() + str1 +
                System.lineSeparator() + " ".repeat(index) + "^" +
                (index >= str1.length() ? " Missing " : " Invalid ") + "digit");
        }
        if ((index = checkValidity(str2)) != -1) {
            throw new NumberFormatException(
                "Error: Invalid value for integer b. Problem at index " +
                index + "." + System.lineSeparator() + str2 +
                System.lineSeparator() + " ".repeat(index) + "^" +
                (index >= str2.length() ? " Missing " : " Invalid ") + "digit");
        }
    }

    private static String removeLeadingZeros(String s) {
        // Do not remove zeros before a negative sign.
        if (s.charAt(0) == '-') {
            return s;
        }
        int end = s.length();
        if (end == 1) {
            return s;
        }
        int i = 0;
        for (; i < end; i++) {
            if (s.charAt(i) != '0') {
                break;
            }
        }
        // If all digits were 0, return a single 0.
        return i != end ? s.substring(i) : "0";
    }

    private static boolean isGreater(String str1, String str2, int index) {
        for (int i = index; i < str1.length(); i++) {
            if (str1.charAt(i) < str2.charAt(i)) {
                return false;
            }
            if (str1.charAt(i) > str2.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isGreater(String str1, String str2) {
        int s1Length = str1.length(),
            s2Length = str2.length();
        boolean isStr1Negative = str1.charAt(0) == '-',
                isStr2Negative = str2.charAt(0) == '-';
        if (!isStr1Negative && !isStr2Negative) {
            if (s1Length > s2Length) {
                return true;
            }
            if (s1Length < s2Length) {
                return false;
            }
            return isGreater(str1, str2, 0);
        }
        if (isStr1Negative && isStr2Negative) {
            if (s1Length > s2Length) {
                return false;
            }
            if (s1Length < s2Length) {
                return true;
            }
            return isGreater(str2, str1, 1);
        }
        return !isStr1Negative;
    }

    private static boolean isAbsGreater(String str1, String str2) {
        if (str1.charAt(0) == '-') {
            str1 = str1.substring(1);
        }
        if (str2.charAt(0) == '-') {
            str2 = str2.substring(1);
        }
        return isGreater(str1, str2);
    }

    /**
     * Adds two non-negative integers, whose digits are stored in Strings.
     * @param str1 String 1, representing a non-negative integer
     * @param str2 String 2, representing a non-negative integer
     * @return the sum of the integers
     */
    private static String sum(String str1, String str2) {
        // TODO
        return "";
    }

    /**
     * Adds two integers, which can be negative, positive, or zero, and whose
     * digits are stored in Strings.
     * Calls sum(String str1, String str2) or sub(String str1, String str2) to
     * do the majority of the work.
     * @param str1 String 1, representing an integer
     * @param str2 String 2, representing an integer
     * @return the sum of the integers
     */
    public static String add(String str1, String str2) {
        validateParameters(str1, str2);
        boolean isStr1Negative = str1.charAt(0) == '-',
                isStr2Negative = str2.charAt(0) == '-';
        // TODO
        return "";
    }

    /**
     * Subtracts two non-negative integers, whose digits are stored in Strings,
     * where the integer value of str1 >= integer value of str2.
     * @param str1 String 1, representing a non-negative integer
     * @param str2 String 2, representing a non-negative integer
     * @return the difference of the integers (str1 - str2)
     */
    private static String sub(String str1, String str2) {
        // TODO
        return "";
    }

    /**
     * Subtracts two integers, which can be negative, positive, or zero, and
     * whose digits are stored in Strings.
     * Calls sub(String str1, String str2) or sum(String str1, String str2) to
     * do the majority of the work.
     * @param str1 String 1, representing an integer
     * @param str2 String 2, representing an integer
     * @return the difference of the integers (str1 - str2)
     */
    public static String subtract(String str1, String str2) {
        validateParameters(str1, str2);
        boolean isStr1Greater = isGreater(str1, str2),
                isStr1Negative = str1.charAt(0) == '-',
                isStr2Negative = str2.charAt(0) == '-';
        // TODO
        return "";
    }

    private static int nextPowerOf2(int n) {
        int pow = 2;
        while (pow < n) {
            pow *= 2;
        }
        return pow;
    }

    private static String shift(String s, int k)
    {
        if (s.equals("0")) return s;
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        // line below equals to `for` loop from 0 to `k`. IntelliJ suggestion
        sb.append("0".repeat(Math.max(0, k)));
        return sb.toString();
    }

    /**
     * Performs the Karatsuba algorithm for multiplying two non-negative
     * integers. The base case is when both str1 and str2 contain 1 digit.
     * @param str1 String 1, representing a non-negative integer
     * @param str2 String 2, representing a non-negative integer
     * @return the product of the integers
     */
    private static String mult(String str1, String str2)
    // My version of this worked only for 2 digit multiplication
    // Used LibreChat (Columbia's experimental AI) to diagnose and solve
    {

        /* ---------- 1. base cases --------------------------------- */
        if (str1.equals("0") || str2.equals("0")) return "0";

        // single–digit × single–digit → do it directly
        if (str1.length() == 1 && str2.length() == 1)
        {
            int prod = (str1.charAt(0) - '0') * (str2.charAt(0) - '0');
            return Integer.toString(prod);
        }

        /* ---------- 2. prepare the strings ------------------------ */
        int n = Math.max(str1.length(), str2.length());   // common length
        int m = n / 2;                                    // split position

        // left-pad the shorter operand with zeros
        str1 = String.format("%" + n + "s", str1).replace(' ', '0');
        str2 = String.format("%" + n + "s", str2).replace(' ', '0');

        /* ---------- 3. split into high / low parts ---------------- */
        String a1 = str1.substring(0, n - m);          // high
        String a0 = str1.substring(n - m);   // low
        String b1 = str2.substring(0, n - m);
        String b0 = str2.substring(n - m);

        /* ---------- 4. three recursive products ------------------- */
        String c2 = mult(a1, b1);                            // a1·b1
        String c0 = mult(a0, b0);                            // a0·b0
        String c1 = mult(addInts(a1, a0), addInts(b1, b0));  // (a1+a0)(b1+b0)
        c1 = subInts(subInts(c1, c2), c0);                   // (a1+a0)(b1+b0)-c2-c0

        /* ---------- 5. combine the three parts -------------------- */
        String part1 = shift(c2, 2 * m);   // c2 · 10^(2m)
        String part2 = shift(c1,     m);      // c1 · 10^m
        return addInts(addInts(part1, part2), c0);
    }

    private static String addInts(String str1, String str2)
    {
        int x = parseInt(str1);
        int y = parseInt(str2);

        return String.valueOf(x + y);
    }

    private static String subInts(String str1, String str2)
    {
        int x = parseInt(str1);
        int y = parseInt(str2);

        return String.valueOf(x - y);
    }

    private static int parseInt(String str)
    {
        try {
            return Integer.parseInt(str);
        }  catch (NumberFormatException e)
        {
            throw new NumberFormatException("Failed to parse int: " + str);
        }
    }

    /**
     * Multiplies two integers, which can be negative, positive, or zero, and
     * whose digits are stored in Strings.
     * Calls mult(String str1, String str2) to do the majority of the work.
     * @param str1 String 1, representing an integer
     * @param str2 String 2, representing an integer
     * @return the product of the integers
     */
    public static String multiply(
            String str1, String str2) throws NumberFormatException {
        validateParameters(str1, str2);
        // TODO
        return mult(str1, str2);
    }

    private static String addCommas(String str) {
        StringBuilder builder = new StringBuilder(4 * str.length() / 3);
        for (int i = str.length() - 1, count = 0; i >= 0; i--) {
            builder.append(str.charAt(i));
            if (++count == 3 && (i > 0 && str.charAt(i - 1) != '-')) {
                builder.append(",");
                count = 0;
            }
        }
        return builder.reverse().toString();
    }

    public static void main(String[] args) {
        // TODO
        String rawResult = multiply(args[0], args[1]);
        String multResult = addCommas(rawResult);

        System.out.println("Result: " + multResult);
        System.out.println("Raw Result: " + rawResult);
//        System.out.println(multiply("2351", "1234"));
    }
}

/*
example for negative numbers:
e.g. -5 and -3 --> -5 + -3
"-" + "8"
-5 + 3
"-" + (5-3)
-3 + 5 = 5 - 3
 */