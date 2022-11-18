package otus.cachehw;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Supplier;

public class MyCache<K, V> implements HwCache<K, V> {

    public Map<K, V> whm = new WeakHashMap<>();
    private List<SoftReference<HwListener<K,V>>> listenerSoftReference = new ArrayList<>();

//Надо реализовать эти методы
    @Override
    public void put(K key, V value) {
        forEachListener(key, value, " ADDED VALUE");
        whm.put(key, value);
    }

    @Override
    public void remove(K key) {
        whm.remove(key);
    }

    @Override
    public V get(K key) {
        if (key != null && whm.get(key) != null) {
            forEachListener(key, whm.get(key), " GET VALUE");
            return whm.get(key);
        }
        return null;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerSoftReference.add(new SoftReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listener = null;
    }

    private void forEachListener(K key, V value, String action) {
        listenerSoftReference.forEach(hwListener -> {
            Objects.requireNonNull(hwListener.get()).notify(key, value, action);
        });
    }
}
