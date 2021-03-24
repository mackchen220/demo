package org.jeecg.modules.commons.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.commons.RedisKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Log4j2
public class RedisLock {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 重试时间
     */
    private final static int RETRY_MILLIS = 100;

    /**
     * 获取锁
     *
     * @param userId
     * @param expireStr
     * @return 获取锁成功返回ture，超时返回false
     * @throws Exception
     */
    public boolean lock(String dataId, String userId, String expireStr) {
        String lockKey = dataId + RedisKey.KEY_SPLIT + RedisKey.LOCK_KEY + RedisKey.KEY_SPLIT + userId;

        return lock(expireStr, lockKey);
    }


    /**
     * 获取锁
     *
     * @param userId
     * @param expireStr
     * @return 获取锁成功返回ture，超时返回false
     * @throws Exception
     */
    public boolean lock(String dataId, String userId, String expireStr, String key) {
        String lockKey = dataId + RedisKey.KEY_SPLIT + key + RedisKey.KEY_SPLIT + userId;
        return lock(expireStr, lockKey);
    }

    private boolean lock(String expireStr, String lockKey) {
        while (true) {
            try {
                if (redisTemplate.opsForValue().setIfAbsent(lockKey, expireStr)) {
                    return true;
                }
                //redis里key的时间
                String currentValue = (String) redisTemplate.opsForValue().get(lockKey);
                //判断锁是否已经过期，过期则重新设置并获取
                if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
                    //设置锁并返回旧值
                    String oldValue = (String) redisTemplate.opsForValue().getAndSet(lockKey, expireStr);
                    //比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
                    if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                        return true;
                    }
                }
                //延时
                try {
                    Thread.sleep(RETRY_MILLIS);
                } catch (InterruptedException e) {
                    log.error("【redis分布式锁】sleep异常,lockKey:" + lockKey + ",error:" + e.getMessage(), e);
                }
            } catch (Exception e) {
                log.error("【redis分布式锁】加锁异常,lockKey:" + lockKey + ",error:" + e.getMessage());
            }
        }
    }

    /**
     * 释放获取到的锁
     *
     * @param dataId
     * @param userId
     * @param value
     */
    public void unlock(String dataId, String userId, String value, String key) {
        String lockKey = dataId + RedisKey.KEY_SPLIT + key + RedisKey.KEY_SPLIT + userId;
        unlock(value, lockKey);
    }

    private void unlock(String value, String lockKey) {
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(lockKey);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.delete(lockKey);
            }
        } catch (Exception e) {
            log.error("【redis分布式锁】解锁异常:" + e.getMessage(), e);
        }
    }

    /**
     * 释放获取到的锁
     *
     * @param dataId
     * @param userId
     * @param value
     */
    public void unlock(String dataId, String userId, String value) {
        String lockKey = dataId + RedisKey.KEY_SPLIT + RedisKey.LOCK_KEY + RedisKey.KEY_SPLIT + userId;
        unlock(value, lockKey);
    }


}
