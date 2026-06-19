package me.spectral8420.petPickup.database;

import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDatabase {
    private static JedisPool jedisPool;

    public static boolean connect(String host, int port, String password) {
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();

            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);

            poolConfig.setTimeBetweenEvictionRunsMillis(30000);
            jedisPool = new JedisPool(poolConfig, host, port, 5000, password);

            Jedis jedis = jedisPool.getResource();

            jedis.set("initialTest", "initialTestValue");
            jedis.get("initialTest");

            ConsoleHelper.sendMessage(ChatColor.GREEN + "Connected to the configured Redis database on port " + port);
            return true;
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Failed to connect to redis database: " + e);
            return false;
        }
    }

    public static boolean saveString(String key, String value) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
            return true;
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Failed to save string to redis database: " + e);
            return false;
        }
    }

    public static String getString(String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }

        catch (Exception e) {
            return "";
        }
    }

    public static boolean deleteKey(String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
            return true;
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Failed to delete string to redis database: " + e);
            return false;
        }
    }

    public static boolean exists(String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Failed to check for existing value in redis database: " + e);
            return false;
        }
    }

    public static void close() {
        jedisPool.close();
    }
}
