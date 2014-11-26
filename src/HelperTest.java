import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelperTest {

    private Helper helper;
    @Before
    public void setUp()
    {
        helper = new Helper();
    }

    @Test
    public void testHashNullByte()
    {

        byte[] expected = hexStringToByteArray
                ("df3f619804a92fdb4057192dc43dd748ea778adc52bc498ce80524c014b81119");

        byte[] actual = helper.hash(new Object[] {null});

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testHashByte()
    {

        byte[] expected = hexStringToByteArray
                ("4bf5122f344554c53bde2ebb8cd2b7e3d1600ad631c385a5d7cce23c7785459a");

        byte[] actual = helper.hash(new Object[] {"01"});

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testHashOctetString()
    {

        byte[] expected = hexStringToByteArray
                ("16df7d2d0c3882334fe0457d298a7b2413e1e5b7a880f0b5ec79eeeae7f58dd8");

        byte[] actual = helper.hash(new Object[] {"0102030405"});

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testHashList()
    {

        byte[] expected = hexStringToByteArray
            ("dfd6a31f867566ffeb6c657af1dafb564c3de74485058426633d4b6c8bad6732");

        byte[] actual = helper.hash(new Object[] {"01", "0102030405", null});

        assertArrayEquals(expected, actual);
    }


    @Test
    public void testHashGroupA()
    {

        byte[] expected = hexStringToByteArray
                ("7b36c8a3cf1552077e1cacb365888d25c9dc54f3faed7aff9b11859aa8e4ba06");

        // 1.3.6.1.4.1.311.75.1.1.1
        byte[] actual = helper.hash(new Object[] {"01", "0102030405", null});

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