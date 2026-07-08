package com.emu.tqqserver.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataTable<T extends BaseDef> implements Iterable<T> {
    private final Map<Integer, T> map;
    private final List<T> list;

    public DataTable() {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();
    }

    public T get(int id) {
        return map.get(id);
    }

    @SuppressWarnings("unchecked")
    public void add(Object def) {
        T casted = (T) def;
        list.add(casted);
        map.put(casted.getId(), casted);
    }

    public Collection<T> values() {
        return list;
    }

    public boolean contains(int id) {
        return map.containsKey(id);
    }

    public int size() {
        return list.size();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
