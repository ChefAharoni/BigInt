import java.math.BigInteger;

/*******************************************************************************
 * Name          : BigInt.java
 * Author        : Amit Aharoni
 * Version       : 1.0
 * Date          : July 18, 2025
 * Last modified : Friday, July 25th, 2025
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
        // make equal length (pad with zeroes)
        // add in reverse (index at length - 1)
        // divide sum by 10 = result is carry to the next computation
        // mod sum by 10 = this is the new sum
        // return the answer in reverse
        if (str1.length() != str2.length())
        {
            int delta = Math.abs(str1.length() - str2.length());

            if (str1.length() > str2.length())
                str2 = padZeroes(str2, delta); // add zeroes to str1
            else // str2.length > str1.length
                str1 = padZeroes(str1, delta); // add zeroes to str2
        }

        StringBuilder result = new StringBuilder();
        int sum;
        int carry = 0;

        for (int i = str1.length() - 1; i >= 0; i--)
        {
            // 0 is 48 in ASCII
            sum = (str1.charAt(i) - '0') + (str2.charAt(i) - '0') + carry;
            carry = sum / 10;
//            sum %= 10;
            result.append(sum % 10);
        }

        // emit the final carry (if exists)
        if (carry != 0)
            result.append(carry);

        return removeLeadingZeros(result.reverse().toString());
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

        if (str1.equals("0") || str1.equals("-0")) return str2;
        if (str2.equals("0") || str2.equals("-0")) return str1;

        String absStr1 = isStr1Negative ? str1.substring(1) : str1;
        String absStr2 = isStr2Negative ? str2.substring(1) : str2;

        // both positive
        if (!isStr1Negative && !isStr2Negative)
            return sum(absStr1, absStr2);

        // both negative
        if (isStr1Negative && isStr2Negative)
            return "-" + sum(absStr1, absStr2);

        // str1 is positive, str2 is negative
        if (!isStr1Negative && isStr2Negative)
        {
            if (isAbsGreater(absStr1, absStr2))
                return sub(absStr1, absStr2);
            else
                return "-" + sub(absStr2, absStr1);
        }

        // str1 is negative, str2 is positive
        if (isStr1Negative && !isStr2Negative)
        {
            if (isAbsGreater(absStr2, absStr1))
                return sub(absStr2, absStr1);
            else
                return "-" + sub(absStr1, absStr2);
        }

        // (can't touch this)
        throw new IllegalStateException();
    }

    private static String padZeroes(String str, int delta)
    {
        // Repeat is same as for loop from i to delta, IntelliJ suggestions
        return "0".repeat(Math.max(0, delta)) + str;
    }

    /**
     * Subtracts two non-negative integers, whose digits are stored in Strings,
     * where the integer value of str1 >= integer value of str2.
     * @param str1 String 1, representing a non-negative integer
     * @param str2 String 2, representing a non-negative integer
     * @return the difference of the integers (str1 - str2)
     */
    private static String sub(String str1, String str2) {
        int str1len = str1.length(), str2len = str2.length();
        if (str1len != str2len)
        {
            int delta = Math.abs(str1len- str2len);

            if (str1.length() > str2.length())
                str2 = padZeroes(str2, delta); // add zeroes to str1
            else // str2.length > str1.length
                str1 = padZeroes(str1, delta); // add zeroes to str2
        }

        StringBuilder result = new StringBuilder();
        int carry = 0;

        for (int i = str1.length() - 1; i >= 0; i--)
        {
            // 0 is 48 in ASCII
            int diff = (str1.charAt(i) - '0') - (str2.charAt(i) - '0') - carry;
            if (diff < 0)
            {
                diff += 10;
                carry = 1;
            } else
                carry = 0;

            result.append(diff);
        }

        // remove trailing zeros from the beginning with regex
        return removeLeadingZeros(result.reverse().toString());
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

        if (str2.equals("0") || str2.equals("-0")) return removeLeadingZeros(str1);
        if (str1.equals("0") || str1.equals("-0"))
            // Keep the negative sign in the result: 0 - x = -x
            return str2.startsWith("-")
                    ? str2.substring(1)
                    : "-" + str2;


        // strip negative signs for helper methods
        String absStr1 = isStr1Negative ? str1.substring(1) : str1;
        String absStr2 = isStr2Negative ? str2.substring(1) : str2;

        // both positive
        if (!isStr1Negative && !isStr2Negative)
        {
            if (isStr1Greater)
                return sub(absStr1, absStr2);
            else
                return "-" + sub(absStr2, absStr1);
        }

        // both negative
        if (isStr1Negative && isStr2Negative)
        {
            if (!isStr1Greater)
                return sub(absStr2, absStr1); // call with bigger
            else  // if str1 IS greater
                return "-" + sub(absStr1, absStr2);
        }

        // str1 is positive, str2 is negative
        if (!isStr1Negative && isStr2Negative)
            return sum(absStr1, absStr2);

        // str1 is negative, str2 is positive
        if (isStr1Negative && !isStr2Negative)
            return "-" + sum(absStr1, absStr2);

        // (can't touch this)
        throw new IllegalStateException();
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
        str1 = padZeroes(str1, n - str1.length());
        str2 = padZeroes(str2, n - str2.length());

        /* ---------- 3. split into high / low parts ---------------- */
        String a1 = str1.substring(0, n - m);          // high
        String a0 = str1.substring(n - m);   // low
        String b1 = str2.substring(0, n - m);
        String b0 = str2.substring(n - m);

        /* ---------- 4. three recursive products ------------------- */
        String c2 = mult(a1, b1);                            // a1·b1
        String c0 = mult(a0, b0);                            // a0·b0
        String c1 = mult(sum(a1, a0), sum(b1, b0));  // (a1+a0)(b1+b0)
        c1 = sub(sub(c1, c2), c0);                   // (a1+a0)(b1+b0)-c2-c0

        /* ---------- 5. combine the three parts -------------------- */
        String part1 = shift(c2, 2 * m);   // c2 · 10^(2m)
        String part2 = shift(c1,     m);      // c1 · 10^m
        return sum(sum(part1, part2), c0);
    }

    private static int parseInt(String str)
    {
        try {
            return Integer.parseInt(str);
        }  catch (NumberFormatException e)
        {
            BigInteger maxInt = BigInteger.valueOf(Integer.MAX_VALUE);
            BigInteger minInt = BigInteger.valueOf(Integer.MIN_VALUE);
            BigInteger n;
            if (str.charAt(0) == '-')
                n = new BigInteger(str.substring(1));
            else
                n = new BigInteger(str);
            System.out.println("n > Integer.Max_Value? " +
                    (n.compareTo(maxInt) > 0));
            System.out.println("n < Integer.MinValue? " +
                    (n.compareTo(minInt) < 0));

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

        // determine sign of the result
        boolean neg1 = str1.charAt(0) == '-';
        boolean neg2 = str2.charAt(0) == '-';
        boolean resultNegative = neg1 ^ neg2;

        // strip leading '-' for the helpers
        String a = neg1 ? str1.substring(1) : str1;
        String b = neg2 ? str2.substring(1) : str2;

        // compute the unsigned product
        String result = mult(a, b);

        // normalize to avoid "-0"
        if (result.equals("0")) {
            return "0";
        }

        // prefix minus if needed
        return resultNegative
                ? "-" + result
                : result;
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
        if (args.length != 3)
        {
            System.err.println("Usage: java BigInt <operation> <integer a> <integer b>");
            System.exit(1);
        }
        String operation = args[0];
        String result = null;

        try
        {
            result = switch (operation)
            {
                case "add" -> add(args[1], args[2]);
                case "sub" -> subtract(args[1], args[2]);
                case "mult" -> multiply(args[1], args[2]);
                default ->
                        throw new NumberFormatException(
                                "Error: Unknown operation '" + operation +
                                        "'. Allowed operations: add, sub, mult.");
            };
        } catch (NumberFormatException e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        String formattedResult = addCommas(result);

        System.out.println(formattedResult);
    }
}
