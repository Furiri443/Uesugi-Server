import java.nio.file.*;
import java.util.*;
import com.emu.tqqserver.proto.pkg_proto.*;
import com.fasterxml.jackson.databind.*;

public class TestPuzzleStart {
    public static void main(String[] args) throws Exception {
        String json = Files.readString(Paths.get("d:/pcap/decoded/v1_43_440/puzzle/start/Proto.PuzzleStart.json"));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        byte[] data = Base64.getDecoder().decode(root.get(0).get("raw_payload_b64").asText());
        try {
            PuzzleStart p = PuzzleStart.parseFrom(data);
            System.out.println(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
