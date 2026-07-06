import com.emu.tqqserver.proto.pkg_proto.StoredData;
import java.io.FileInputStream;

public class TestParse {
    public static void main(String[] args) throws Exception {
        try (FileInputStream fis = new FileInputStream("gotopazu/user_load_default.bin")) {
            StoredData data = StoredData.parseFrom(fis);
            System.out.println("Parsed successfully!");
            System.out.println("Cards: " + data.getCardCount());
            System.out.println("Items: " + data.getItemCount());
            System.out.println("Units: " + data.getUnitCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
