package com.github.ckpjasher.blueprintbuild.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BpbPermission {
    BPB_SAVE("bpb.save"),
    BPB_LOAD("bpb.load"),
    BPB_REMOVE("bpb.remove"),
    BPB_RENAME("bpb.rename"),
    BPB_LIST("bpb.list"),
    BPB_LIST_MY("bpb.list.my"),
    BPB_PROJECT_LIST("bpb.project.list"),
    BPB_PROJECT_ADD("bpb.project.add"),
    BPB_PROJECT_REMOVE("bpb.project.remove"),
    BPB_PROJECT_BUILDER_ADD("bpb.project.builder.add"),
    BPB_PROJECT_BUILDER_REMOVE("bpb.project.builder.remove"),
    BPB_OP_ADD("bpb.op.add");
    private final String name;

    public String getName(String string) {
        return name + "." + string;
    }

    public static String getPermission(BpbPermission permission, String str) {
        return permission.getName() + "." + str;
    }
}