import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by tbrixen on 30/11/14.
 */
public class FieldZq {
    private final BigInteger q = new BigInteger
                    ("c8f750941d91791904c7186d62368ec19e56b330b669d08708f882e4edb82885",
                            16);

    public BigInteger getRandomElement(){
        SecureRandom r = new SecureRandom();
        BigInteger bi = new BigInteger(q.bitLength(), r);

        // Check to see if bi is < q
        while (bi.compareTo(q) >= 0){
            bi = new BigInteger(q.bitLength(), r);
        }
        return bi;
    }
}
