package educational.dmitriigurylev;



public class UtilityClass {
    public static int binaryToDecimal(String binaryString) {
        int decimal = 0;
        int length = binaryString.length();
        for (int i = 0; i < length; i++) {
            if (binaryString.charAt(i) == '1') {
                decimal += (int) Math.pow(2, length - i - 1);
            }
        }
        return decimal;
    }

    public static int[] binaryStringToDecimalString(String bitString) {
        int[] decimalIntArray = new int[9];
        int i = 0;
        while (!bitString.isEmpty()) {
            decimalIntArray[i++] = UtilityClass.binaryToDecimal(bitString.substring(0, 8));
            bitString = bitString.substring(8);
        }
        while (true) {
            if (i>=9) break;
            decimalIntArray[i++] = 236;
            if (i>=9) break;
            decimalIntArray[i++] = 17;
        }
        return decimalIntArray;
    }
}
