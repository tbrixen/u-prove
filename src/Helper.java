import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by tbrixen on 25/11/14.
 */
public class Helper {
    public static byte[] hash(Object[] message) {

        String algorithm = "SHA-256";



        String toHash = "";
        int elementCount = message.length;
        if (elementCount > 1){
            toHash = convertIntTo32BitHex(message.length);
        }

        for (int i = 0; i < elementCount; i++){
            Object element = message[i];

            if (element == null){
                toHash += "00000000";
            } else if (element.getClass().equals(String.class)){
                int length = ((String) message[i]).length();

                String lengthPadding = "";
                if (length > 2 ){
                    lengthPadding = convertIntTo32BitHex(length/2);
                }

                toHash += lengthPadding + message[i];
            }
        }


        byte[] messageBytes = hexStringToByteArray(toHash);

        System.out.println(convertByteArrayToHexString(messageBytes));

        System.out.println(Arrays.toString(messageBytes));

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            byte[] hashedBytes = digest.digest(messageBytes);

            return hashedBytes;
        } catch (Exception ex) {
            System.out.println("Error exception");
        }
        return null;
    }

    private static String convertIntTo32BitHex(int number){
        String hexString = Integer.toHexString(number);

        int length = hexString.length();
        String padding = "";
        for (int i = length; i < 8; i++) {
            padding += "0";
        }

        return padding + hexString;
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();


    }


    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
