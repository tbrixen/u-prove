import java.math.BigInteger;

/**
 * Created by tbrixen on 26/11/14.
 */
public class Group {

    private BigInteger p;
    private BigInteger q;
    private BigInteger generator;
    private byte[] domainParameterSeed;

    public Group(String oid){
        if (oid.equals("1.3.6.1.4.1.311.75.1.1.1")){
            p = new BigInteger
                    ("ef0990061db67a9eaeba265f1b8fa12b553390a8175bcb3d0c2e5ee5dfb826e229ad37431148ce31f8b0e531777f19c1e381c623e600bff7c55a23a8e649ccbcf833f2dba99e6ad66e52378e92f7492b24ff8c1e6fb189fa8434f5402fe415249ae02bf92b3ed8eaaaa2202ec3417b2079da4f35e985bb42a421cfaba8160b66949983384e56365a4486c046229fc8c818f930b80a60d6c2c2e20c5df880534d4240d0d81e9a370eef676a1c3b0ed1d8ff30340a96b21b89f69c54ceb8f3df17e31bc20c5b601e994445a1d347a45d95f41ae07176c7380c60db2aceddeeda5c5980964362e3a8dd3f973d6d4b241bcf910c7f7a02ed3b60383a0102d8060c27",
                        16);

            q = new BigInteger
                    ("c8f750941d91791904c7186d62368ec19e56b330b669d08708f882e4edb82885",
                        16);

            generator = new BigInteger
                    ("bca29a2d4b226f594591ecedbd1859ccb0ba3d20186b30e0ffbf05ba25788a6720005194c1f005b2ced980ca160254bb48a0e2d756ddcc919afe9017a47905154177fb2c37fb6cc0f4423e8f4a8b8376e0043dddf06255050523d4ee1f68748d0d415732686f01d88d98c75bd1e25fa48cd5bf4cc69b6d67bf0dd5c9cf18ee91ae17ebf128151286de3ab17ac4025a91168d42532144b7357e423f1b8d9dbcee68df89b44150e496ff6d416e4376e2daf9e422807d276572cec335d0587a5d798022415e3737326251d304fd7129183357ef9c8d194447705360b5bb270a2ce6194e5894c1fafad3ca78af080f500227564d43cb63462b1084e9ccd55d002e19",
                            16);

            domainParameterSeed = hexStringToByteArray
                    ("227cc83035ac2c68e6b4e5fe4b59c0a84ae80330f380de03223e378136d76fc0");
        }
    }

    public Object[] getBytesToHash()
    {

        return new Object[] {p, q, generator};
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getGenerator() {
        return generator;
    }

    public byte[] getDomainParameterSeed() {
        return domainParameterSeed;
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
