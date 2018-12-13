package com.n26.cache;

public class CacheFactory {
    public enum CacheType{
        ConcurrentHashMap,
        Caffeine
    }

    public static IStatCache getCache(CacheType cacheType){
        switch (cacheType){
            case ConcurrentHashMap:
                return StatisticsCMCache.getInstance();
            case Caffeine:
                return StatisticsCaffeineCache.getInstance();
        }

        return null;
    }
}
