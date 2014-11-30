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
        FieldZq fz = new FieldZq();

        Helper helper = new Helper();

        // Should be computed
        BigInteger xt = helper.computeXt(null,null,null,null,null,null);

        BigInteger[] xs = new BigInteger[5];
        for (int i = 0; i < e.length; i++){
            xs[i] = helper.computeXi(gq.getQ(), e[i], A[i]);
        }

        Issuer issuer = new Issuer(xt, xs);

        // Compute gamma
        BigInteger g0 = issuer.getG0();
        BigInteger gamma = g0;
        for (int i = 1; i<=5; i++){
            gamma = gamma.multiply(xs[0]).mod(gq.getP());
        }
        gamma = gamma.multiply(xt).mod(gq.getP());

        Prover prover = new Prover(gamma, g0);

        // Precompute for both
        issuer.issuanceProtocolPrecompute(gamma);
        prover.issuanceProtocalProcompute();

        // We "send" the first message from issuer to the prover
        prover.secondMessage(issuer.getSigmaZ(), issuer.getSigmaA(), issuer
                .getSigmaB());




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

