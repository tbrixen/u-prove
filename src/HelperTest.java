import junit.framework.Assert;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

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

        byte[] actual = helper.hash(new Object[] {new byte[] {0x01}});

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testHashOctetString()
    {

        byte[] expected = hexStringToByteArray
                ("16df7d2d0c3882334fe0457d298a7b2413e1e5b7a880f0b5ec79eeeae7f58dd8");

        byte[] actual = helper.hash(new Object[] {new byte[] {0x01, 0x02,
                0x03, 0x04, 0x05}});

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testHashList()
    {

        byte[] expected = hexStringToByteArray
            ("dfd6a31f867566ffeb6c657af1dafb564c3de74485058426633d4b6c8bad6732");

        byte[] actual = helper.hash(new Object[] {
                new byte[] {0x01},
                new byte[] {0x01, 0x02,  0x03, 0x04, 0x05},
                null});

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testHashBigInt()
    {
        byte[] expected = hexStringToByteArray
                ("468dde0a72339482252ab47ebebf4fc11a057ebace81e4d03b9347eece889380");

        byte[] actual = helper.hash(new Object[] {
                new BigInteger("254666256150")});

        assertArrayEquals(expected, actual);

    }

    @Test
    public void testHashGroup()
    {
        byte[] expected = hexStringToByteArray
        ("7b36c8a3cf1552077e1cacb365888d25c9dc54f3faed7aff9b11859aa8e4ba06");

        byte[] actual = helper.hash( new Object[] {
                new Group("1.3.6.1.4.1.311.75.1.1.1")});

        assertArrayEquals(expected, actual);
    }


    @Test
    public void testComputeX1()
    {
        BigInteger expected = new BigInteger
                ("3e4668267d6a6fe778ec3a189b384b44d029f3edc3532d618b88a729adaea673"
                        ,16);
        Group g = new Group("1.3.6.1.4.1.311.75.1.1.1");

        BigInteger actual = helper.computeXi(
                g.getQ(),
                (byte) 0x01,
                hexStringToByteArray("416c69636520536d697468"));

        assertEquals(expected, actual);
    }


    @Test
    public void testComputeX2()
    {
        BigInteger expected = new BigInteger
                ("af93c647ca51d4c950a616f6aa4cca9c3995589b0710783c3e3a513caf244772"
                        ,16);
        Group g = new Group("1.3.6.1.4.1.311.75.1.1.1");

        BigInteger actual = helper.computeXi(
                g.getQ(),
                (byte) 0x01,
                hexStringToByteArray("5741"));

        assertEquals(expected, actual);
    }


    @Test
    public void testComputeX3()
    {
        BigInteger expected = new BigInteger
                ("58f98bdb5985d501eac1de1057505c3782948c1b5949261d67cdeddf1bf49a5c"
                        ,16);
        Group g = new Group("1.3.6.1.4.1.311.75.1.1.1");

        BigInteger actual = helper.computeXi(
                g.getQ(),
                (byte) 0x01,
                hexStringToByteArray("313031302043727970746f20537472656574"));

        assertEquals(expected, actual);
    }


    @Test
    public void testComputeX4()
    {
        BigInteger expected = new BigInteger ("1" ,16);
        Group g = new Group("1.3.6.1.4.1.311.75.1.1.1");

        BigInteger actual = helper.computeXi(
                g.getQ(),
                (byte) 0x00,
                hexStringToByteArray("01"));

        assertEquals(expected, actual);
    }


    @Test
    public void testComputeX5()
    {
        BigInteger expected = new BigInteger ("499602d2",16);
        Group g = new Group("1.3.6.1.4.1.311.75.1.1.1");

        BigInteger actual = helper.computeXi(
                g.getQ(),
                (byte) 0x00,
                hexStringToByteArray("499602d2"));

        assertEquals(expected, actual);
    }

    @Test
    public void testComputeXt()
    {

        BigInteger expected = new BigInteger
                ("347e50f40edac4a1867f9d50827188324498d407a32945545bf9ef217eb23937"
                ,16);

        Group group = new Group("1.3.6.1.4.1.311.75.1.1.1");
        BigInteger actual = helper.computeXt(
                group,
                new GroupElement(), //g
                null, // gd
                null, //e
                null, //s
                null // TI
        );

        assertEquals(expected, actual);
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