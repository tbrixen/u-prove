import java.math.BigInteger;

/**
 * Created by tbrixen on 25/11/14.
 */
public class run {
    public static void main(String[] args) {
        String UIDp = IssuerParameters.UIDp;
        String UIDh = IssuerParameters.UIDh;

        // Which attributes are hashed
        byte[] e = new byte[] {1, 1, 1, 0, 0};

        byte[][] A = new byte[][] {
                "attribute 0".getBytes(),
                "attribute 1".getBytes(),
                "attribute 2".getBytes(),
                "attribute 3".getBytes(),
                "attribute 4".getBytes()
        };

        Group gq = new Group("1.3.6.1.4.1.311.75.1.1.1");

        Helper helper = new Helper();

        // Should be computed
        BigInteger xt = helper.computeXt(null,null,null,null,null,null);

        BigInteger[] xs = new BigInteger[5];
        for (int i = 0; i < e.length; i++){
            xs[i] = helper.computeXi(gq.getQ(), e[i], A[i]);
        }

        BigInteger test = new BigInteger("254666256150");
        System.out.println(test.toByteArray().length);

        // Should be computed
        BigInteger gamma = new BigInteger
                ("67106c2e235c854e033693c6f3c64736f2bab35b5a0a3c936f2bd77a1f7bb80f9e6b98274428a73378bfc6cab2a6c4c00842448c1053c8a27b198929e3f96b5d14ddee25b8c3c27f3519e0126a7439d4fcf1d5da0ed8f79f11cc8d7ebd709f265935845cf4169e5dcae9f6025f80ac15e196e9200525e29a2419539877ceeb4a4ecbcb93669ff37ca68bd9f082ca582ddc20b4f5b3a20144f9a20dc8e0ca77d118b3c74d014f44329f643e8396616d4e55479b471381ef67025d6701348f0aa6e61740659522f92aef68c1ce2a505a61cffde2578fee82066f52bb766403ca1b0b8632b9236a87ed26fbdc8148e1ca9c9f0102df0ed7610fc0f72d89587b9967"
                        ,16);
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}

