import java.math.BigInteger;

/**
 * Created by tbrixen on 26/11/14.
 */
public class Prover {
    BigInteger gamma;
    BigInteger g0;
    Group gq = new Group("1.3.6.1.4.1.311.75.1.1.1");
    FieldZq zq = new FieldZq();
    Helper helper = new Helper();

    // For precompute
    BigInteger h;
    BigInteger t1;
    BigInteger t2;
    BigInteger alpha;
    BigInteger beta1;
    BigInteger beta2;
    // Private key
    BigInteger alphaInverse;

    // For second messge
    BigInteger sigmaZPrime;
    BigInteger sigmaAPrime;
    BigInteger sigmaBPrime;
    BigInteger sigmaCPrime;
    BigInteger sigmaC;
    public Prover(BigInteger gamma, BigInteger g0 ) {
        this.gamma = gamma;
        this.g0 = g0;
    }


    public void issuanceProtocalProcompute() {
        alpha = zq.getRandomElement();
        beta1 = zq.getRandomElement();
        beta2 = zq.getRandomElement();

        h = gamma.modPow(alpha, gq.getP());
        BigInteger t1a = g0.modPow(beta1, gq.getP());
        BigInteger t1b = (gq.getGenerator()).modPow(beta2, gq.getP());

        t1 = t1a.multiply(t1b).mod(gq.getP());
        t2 = h.modPow(beta2, gq.getP());

        alphaInverse = alpha.modInverse(gq.getQ());

    }

    public void secondMessage(BigInteger sigmaZ, BigInteger sigmaA,
                              BigInteger sigmaB){
        sigmaZPrime = sigmaZ.modPow(alpha, gq.getP());
        sigmaAPrime = t1.multiply(sigmaA).mod(gq.getP());

        sigmaBPrime = sigmaZPrime.modPow(beta1, gq.getP());
        sigmaBPrime = sigmaBPrime.multiply(t2);
        sigmaBPrime = sigmaBPrime.multiply(sigmaB.modPow(alpha, gq.getP()));
        sigmaBPrime = sigmaBPrime.mod(gq.getP());

        sigmaCPrime = helper.hashToZq(new Object[] {
                h,
                null,
                sigmaZPrime,
                sigmaAPrime,
                sigmaBPrime
        }, gq.getQ());

        sigmaC = (sigmaCPrime.add(beta1)).mod(gq.getQ());

    }

    public BigInteger getSigmaC() {return sigmaC;}


    public void tokenGeneration(BigInteger sigmaR) {
        BigInteger sigmaRPrime = sigmaR.add(beta2).mod(gq.getQ());

        // LeftSide
        BigInteger ls = sigmaAPrime.multiply(sigmaBPrime).mod(gq.getP());

        // RightSide
        BigInteger rs1 = (gq.getGenerator().multiply(h)).modPow(sigmaRPrime, gq
                .getP());
        BigInteger rs2 = (g0.multiply(sigmaZPrime)).modPow(sigmaCPrime
                .negate(), gq.getP());
        BigInteger rs = rs1.multiply(rs2).mod(gq.getP());
        if (ls.equals(rs)){
            System.out.println("Success. The token is valid.");
        } else {
            System.out.println("Fail. The token is not valid.");
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
}
