package com.emu.tqqserver.data;

public abstract class BaseDef implements Comparable<BaseDef> {

    public abstract int getId();

    public void onLoad() {

    }

    @Override
    public int compareTo(BaseDef o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
