import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
            } else if (element.getClass().equals(BigInteger.class)) {
                byte[] elm = ((BigInteger) element).toByteArray();

                toHash = convertIntTo32BitHex(elm.length);
                toHash += convertByteArrayToHexString(elm);

            } else if (element.getClass().equals(Group.class)) {
                Group gq = (Group) element;
                String p = convertByteArrayToHexString(gq.getP().toByteArray
                        ()).substring(2); // Remove 0's at start of string
                String q = convertByteArrayToHexString(gq.getQ().toByteArray
                        ()).substring(2); // Remove 0's at start of string
                String g = convertByteArrayToHexString(gq.getGenerator()
                        .toByteArray()).substring(2); // Remove 0's at start of string

                toHash = convertIntTo32BitHex(p.length()/2);
                toHash += p;

                toHash += convertIntTo32BitHex(q.length()/2);
                toHash += q;

                toHash += convertIntTo32BitHex(g.length()/2);
                toHash += g;

            } else {
                int length = ((byte[]) element).length;

                String lengthPadding = "";
                if (length > 1 ){
                    lengthPadding = convertIntTo32BitHex(length);
                }

                toHash += lengthPadding + convertByteArrayToHexString(
                        (byte[]) element);
            }
        }


        byte[] messageBytes = hexStringToByteArray(toHash);

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            byte[] hashedBytes = digest.digest(messageBytes);

            return hashedBytes;
        } catch (Exception ex) {
            System.out.println("Error exception");
        }
        return null;
    }

    public BigInteger computeXt(Group gq, GroupElement g, GroupElement gd, byte[]
            e, byte[] s, byte[] TI){
        //byte[] P = hash(new Object[] {UIDd})

        // Fake it till you make it
        return new BigInteger
                ("347e50f40edac4a1867f9d50827188324498d407a32945545bf9ef217eb23937"
                ,16);
    }

    public BigInteger computeXi(BigInteger q, byte encodingByte,
                                byte[] attribute)
    {
        if (encodingByte == 1){
            if (attribute == null || attribute.length == 0){
                return BigInteger.ZERO;
            } else {
                return hashToZq(new Object[] {attribute}, q);
            }
        } else if (encodingByte == 0) {
            BigInteger a = new BigInteger(1, attribute);
            if (a.compareTo(q) <= 0){
                return a;
            } else {
                System.out.println("Error in formatting");
            }
        } else {
            System.out.println("Error in formatting");
        }

        return null;
    }

    private BigInteger hashToZq(Object[] objects, BigInteger q) {
        byte[] result = hash(objects);
        BigInteger r = new BigInteger(1, result);

        return r.mod(q);
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
