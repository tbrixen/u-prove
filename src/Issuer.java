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
    GroupElement g0;
    GroupElement gt;
    byte[] e; // Which attributes are revealed/hidden
    byte[][] A; // The attributes
    String S; //applications specific for specification for the issuer
    // parameters and tokens issued using them.
    Helper helper = new Helper();

    /*
     * For when issuing:
     */
    //helper.ComputeXt("UIDp", "TI", false, "gd_d");
    public Issuer(BigInteger xt, BigInteger[] xs){

        group = new Group("1.3.6.1.4.1.311.75.1.1.1");
        getGi(1);
    }

    public void issuanceProtocolPrecompute(byte[][] A, byte[] e){
    }

    public GroupElement getGi (int i){
        System.out.println(Arrays.toString(gs[i]));
        return null;
    }

}
