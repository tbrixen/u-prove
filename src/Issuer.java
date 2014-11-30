import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by tbrixen on 26/11/14.
 */
public class Issuer {
    String UIDp = IssuerParameters.UIDp;
    Group group;
    String UIDh = IssuerParameters.UIDh;
    byte[][] gs = IssuerParameters.gs;
    GroupElement gt;
    byte[] e; // Which attributes are revealed/hidden
    byte[][] A; // The attributes
    String S; //applications specific for specification for the issuer
    // parameters and tokens issued using them.
    Helper helper = new Helper();
    FieldZq zq = new FieldZq();

    BigInteger y0; // Secret key
    BigInteger g0;
    BigInteger sigmaZ;

    // For precomp
    BigInteger sigmaA;
    BigInteger sigmaB;


    /*
     * For when issuing:
     */
    //helper.ComputeXt("UIDp", "TI", false, "gd_d");
    public Issuer(BigInteger xt, BigInteger[] xs){

        group = new Group("1.3.6.1.4.1.311.75.1.1.1");
        getGi(1);

        // Generate y0
        y0 = zq.getRandomElement();
        g0 = group.getGenerator().modPow(y0, group.getP());

    }

    public BigInteger getG0() {
        return g0;
    }

    public void issuanceProtocolPrecompute(BigInteger gamma){
        sigmaZ = gamma.modPow(y0, group.getP());

        BigInteger w = zq.getRandomElement();
        sigmaA = group.getGenerator().modPow(w, group.getP());
        sigmaB = gamma.modPow(w, group.getP());
    }

    public BigInteger getSigmaA(){return sigmaA;}
    public BigInteger getSigmaB(){return sigmaB;}
    public BigInteger getSigmaZ(){return sigmaZ;}

    public GroupElement getGi (int i){
        System.out.println(Arrays.toString(gs[i]));
        return null;
    }

}
