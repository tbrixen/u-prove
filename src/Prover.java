import java.math.BigInteger;

/**
 * Created by tbrixen on 26/11/14.
 */
public class Prover {
    BigInteger gamma;
    BigInteger g0;
    Group gq = new Group("1.3.6.1.4.1.311.75.1.1.1");
    FieldZq zq = new FieldZq();

    // For precompute
    BigInteger h;
    BigInteger t1;
    BigInteger t2;
    BigInteger alpha;
    BigInteger beta1;
    BigInteger beta2;
    BigInteger alphaInverse;

    // For second messge
    public Prover(BigInteger gamma, BigInteger g0 ) {
        this.gamma = gamma;
        this.g0 = g0;
    }


    public void issuanceProtocalProcompute() {
        alpha = zq.getRandomElement();
        beta1 = zq.getRandomElement();
        beta2 = zq.getRandomElement();

        h = gamma.modPow(alpha, gq.getP());
        t1 = g0.modPow(beta1, gq.getP());
        t1 = t1.modPow(beta2, gq.getP());
        t2 = h.modPow(beta2, gq.getP());

        alphaInverse = alpha.modInverse(gq.getQ());
    }

    public void secondMessage(BigInteger sigmaZ, BigInteger sigmaA,
                              BigInteger sigmaB){
    }


}
