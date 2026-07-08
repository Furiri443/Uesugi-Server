import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.emu.tqqserver.data.resources.ConstantDef;

public class TestGson {
    public static void main(String[] args) throws Exception {
        String json = Files.readString(Paths.get("gotopazu/master/constant.json"));
        List<ConstantDef> list = new Gson().fromJson(json, TypeToken.getParameterized(List.class, ConstantDef.class).getType());
        System.out.println("Loaded: " + (list != null ? list.size() : "null"));
    }
}
