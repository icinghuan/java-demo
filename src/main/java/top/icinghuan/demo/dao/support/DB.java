package top.icinghuan.demo.dao.support;

import java.util.concurrent.ConcurrentHashMap;

public class DB {

    private static final ConcurrentHashMap<String, Database> DATABASES = new ConcurrentHashMap<>();

    public static Database db() {
        return db("default");
    }

    public static Database db(String name) {
        return DATABASES.computeIfAbsent(name, Database::new);
    }
}
