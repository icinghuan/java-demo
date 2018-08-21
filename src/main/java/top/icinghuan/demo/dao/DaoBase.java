package top.icinghuan.demo.dao;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import top.icinghuan.demo.dao.support.DB;

import java.util.List;

public abstract class DaoBase {

    @Autowired
    @Setter
    protected TransactionTemplate transactionTemplate;

    protected <T> T mapper(Class<T> clazz) {
        return DB.db().getSession().getMapper(clazz);
    }

    protected <T> T mapper(String db, Class<T> clazz) {
        return DB.db(db).getSession().getMapper(clazz);
    }

    protected static <T> T firstOrNull(List<T> list) {
        return list.size() > 0 ? list.get(0) : null;
    }
}
