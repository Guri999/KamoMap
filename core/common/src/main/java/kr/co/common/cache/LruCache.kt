package kr.co.common.cache

class LruCache<VALUE>(
    private val maxSize: Int,
    private val expiryDuration: Long,
) : LinkedHashMap<Any, CacheEntry<VALUE>>(maxSize, 0.75f, true) {

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Any, CacheEntry<VALUE>>?): Boolean {
        return size > maxSize
    }

    fun putValue(key: Any, value: VALUE) {
        put(key, CacheEntry(value, System.currentTimeMillis() + expiryDuration))
    }

    fun getValue(key: Any): VALUE? {
        val entry = super.get(key)
        return if (entry != null && entry.expiryTime > System.currentTimeMillis()) {
            entry.value
        } else {
            remove(key)
            null
        }
    }
}