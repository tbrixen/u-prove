import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelperTest {
    @Test
    public void thisAlwaysPasses() {

    }

    @Test
    public void testAdd()
    {
        Helper helper = new Helper();

        int expected = 23;
        int actual = helper.ab();
        assertEquals(expected, actual);
    }

    @Test
    public void testHashNullByte()
    {
        Helper helper = new Helper();

        byte[] expected = hexStringToByteArray
            ("df3f619804a92fdb4057192dc43dd748ea778adc52bc498ce80524c014b81119");

        byte[] actual = helper.hash(new Object[]{null});

        assertArrayEquals(expected, actual);
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