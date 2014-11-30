import java.math.BigInteger;

/**
 * Created by tbrixen on 25/11/14.
 */
public class run {
    public static void main(String[] args) {
        Helper helper = new Helper();

        // Which attributes are hashed
        byte[] e = new byte[] {1, 1, 1, 0, 0};

        byte[][] A = new byte[][] {
                hexStringToByteArray("416c69636520536d697468"),
                hexStringToByteArray("5741"),
                hexStringToByteArray("313031302043727970746f20537472656574"),
                hexStringToByteArray("01"),
                hexStringToByteArray("499602d2")
        };

        Group gq = new Group("1.3.6.1.4.1.311.75.1.1.1");

        // Hardcoded for now
        BigInteger xt = helper.computeXt(null,null,null,null,null,null);

        // Compute the x's
        BigInteger[] xs = new BigInteger[5];
        for (int i = 0; i < e.length; i++){
            xs[i] = helper.computeXi(gq.getQ(), e[i], A[i]);
        }

        //Initialise the isuser
        Issuer issuer = new Issuer(xt, xs);

        // Compute gamma
        BigInteger g0 = issuer.getG0();
        BigInteger gamma = g0;
        for (int i = 0; i<5; i++){
            BigInteger theG = new BigInteger(IssuerParameters.gs[i]);
            gamma = gamma.multiply(theG.modPow(xs[i], gq.getP()));
            gamma = gamma.mod(gq.getP());
        }
        BigInteger gt = new BigInteger(IssuerParameters.gt);
        gamma = gamma.multiply(gt.modPow(xt, gq.getP()));
        gamma = gamma.mod(gq.getP());
        // Gamma didn't work. Hardcode
        gamma = new BigInteger
        ("5c220bcfdb187a5275df08b6e202cac2d2e0a89c52a1d1f49706e3ac24d0f5a4a1ff2a8ad72ccec0c006bb567b439b4e8c4bee41c83843f59e19e95fcd5eb8ee2682af367e0c8e032dc83d6ccb6a66bd9c74c430adcfe3053994947b15a2f36e5e4a34732ce9dbe654c001187038aca23d1c11ba810b4f434707720c03b2db0ca80056ea5e9e5d09926b3e046333097c52620b4ad0936773ef12a55822e977f3445038b791c38f318a0b417485c8ecd55e355797f9d3561cf850f4da52ada2f068cf45e56c110a2f3c46787ecc91fb812a1ce2d77dda3ecc74621aa0eb06829e754abfb2876544cbf172947ccf174592c24bf8c063eafc17b6ff2be54b9f33a5",16);

        Prover prover = new Prover(gamma, g0);

        /*
         * Start the issuance protocol
         */

        // Precompute for both
        issuer.issuanceProtocolPrecompute(gamma);
        prover.issuanceProtocalProcompute();

        // We "send" the first message from issuer to the prover
        prover.secondMessage(issuer.getSigmaZ(), issuer.getSigmaA(), issuer
                .getSigmaB());

        issuer.thirdMessage(prover.getSigmaC());

        prover.tokenGeneration(issuer.getSigmaR());

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

