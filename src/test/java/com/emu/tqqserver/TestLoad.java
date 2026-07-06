package com.emu.tqqserver;

import com.emu.tqqserver.proto.pkg_proto.Nocontent;
import com.google.protobuf.util.JsonFormat;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestLoad {
    public static void main(String[] args) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get("gotopazu/user_load_default.bin"));
        Nocontent nocontent = Nocontent.parseFrom(data);
        System.out.println(JsonFormat.printer().print(nocontent));
    }
}
