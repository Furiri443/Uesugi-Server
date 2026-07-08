package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "relationship.json")
public class RelationshipDef extends BaseDef {
    private int id;

    @SerializedName("relationship_id")
    private int relationshipId;

    private int dearlevel;

    public int getRelationshipId() {
        return relationshipId;
    }

    public int getDearlevel() {
        return dearlevel;
    }

    @Override
    public int getId() {
        return id;
    }
}