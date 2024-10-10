package kr.co.common.cache

data class CacheEntry<VALUE>(
    val value: VALUE,
    val expiryTime: Long,
)