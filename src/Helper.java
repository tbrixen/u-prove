import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by tbrixen on 25/11/14.
 */
public class Helper {
    public static String hashString(Object[] message, String algorithm)
            throws Exception {


        byte[] messageBytes = null;
        if (message == null){
            messageBytes = hexStringToByteArray("00000000");
        }

        if (message == null){
            messageBytes = hexStringToByteArray("00000000");
        } else {
            //messageBytes = message.getBytes("UTF-8");
        }

        System.out.println(Arrays.toString(messageBytes));
        //messageBytes = hexStringToByteArray("01");

        System.out.println(Arrays.toString(messageBytes));

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            byte[] hashedBytes = digest.digest(messageBytes);

            return convertByteArrayToHexString(hashedBytes);
        } catch (Exception ex) {
            throw new Exception(
                    "Could not generate hash from String", ex);
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();


    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
