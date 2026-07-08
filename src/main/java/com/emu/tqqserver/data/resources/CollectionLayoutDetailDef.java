package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "collection_layout_detail.json")
public class CollectionLayoutDetailDef extends BaseDef {
    private int id;

    @SerializedName("collection_layout_id")
    private int collectionLayoutId;

    @SerializedName("seq_id")
    private int seqId;

    @SerializedName("coordinate_x")
    private int coordinateX;

    @SerializedName("coordinate_y")
    private int coordinateY;

    private int size;

    public int getCollectionLayoutId() {
        return collectionLayoutId;
    }

    public int getSeqId() {
        return seqId;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int getId() {
        return id;
    }
}