package otus.cachehw;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {

    public Map<K, V> whm = new WeakHashMap<>();
    private SoftReference<List<HwListener>> listenerSoftReference = new SoftReference<>(new ArrayList<>());

//Надо реализовать эти методы

    @Override
    public void put(K key, V value) {
        listenerSoftReference.get().forEach(hwListener -> {
            hwListener.notify(key, value, "ADDED VALUE");
        });
        whm.put(key, value);
    }

    @Override
    public void remove(K key) {
        whm.remove(key);
    }

    @Override
    public V get(K key) {
        if (key != null && whm.get(key) != null) {
            listenerSoftReference.get().forEach(hwListener -> {
                hwListener.notify(key, whm.get(key), " GET VALUE");
            });
            return whm.get(key);
        }
        return null;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerSoftReference.get().add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listener = null;
    }
}
