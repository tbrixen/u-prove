/**
 * Created by tbrixen on 25/11/14.
 */
public class run {
    public static void main(String[] args) {

        Helper helper = new Helper();



        String message = "01";

        try {
            System.out.println("     " +
                    helper.hash(new Object[] {null} ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println
                ("0x01 " +
                        "4bf5122f344554c53bde2ebb8cd2b7e3d1600ad631c385a5d7cce23c7785459a");

        System.out.println("null " +
                "df3f619804a92fdb4057192dc43dd748ea778adc52bc498ce80524c014b81119");
    }

}

// null: df3f619804a92fdb4057192dc43dd748ea778adc52bc498ce80524c014b81119
// 0x01: 4bf5122f344554c53bde2ebb8cd2b7e3d1600ad631c385a5d7cce23c7785459a