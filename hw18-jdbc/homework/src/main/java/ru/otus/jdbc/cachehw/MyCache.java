package ru.otus.jdbc.cachehw;


import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private Map<K, V> whm = new WeakHashMap<>();
    public ReferenceQueue<K> refQueue = new ReferenceQueue<>();
    HwListener hwListener;
//Надо реализовать эти методы

    @Override
    public void put(K key, V value) {
        whm.put(key, value);
        hwListener.notify(key, value, "object add to cache");
    }

    @Override
    public void remove(K key) {
        hwListener.notify(key, whm.get(key), "object will be deleted from the cache");
        whm.remove(key);
    }

    @Override
    public V get(K key) {
        if (key != null) {
            return whm.get(key);
        }
        return null;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        hwListener = listener;
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        hwListener = null;
    }
}
