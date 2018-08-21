package top.icinghuan.demo.dao.support;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;
import top.icinghuan.demo.common.util.PwdUtils;

import javax.sql.DataSource;

@Slf4j
public class Database {

    private final String name;

    private final SqlSessionFactory sessionFactory;

    private final SqlSessionTemplate session;

    private final DataSource dataSource;

    private final TransactionTemplate transactionTemplate;

    public Database(String name) {
        this(name, null);
    }

    public Database(String name, String mapperPackage) {
        this.name = name;
        Configuration configuration = DefaultDbConfiguration.defaultConfiguration();

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfiguration(configuration);
        Config config = loadConfig(name);

        if (StringUtils.isEmpty(mapperPackage)) {
            mapperPackage = config.getString("mapperPackage");
            log.info("add mapper for database {}: {}", name, mapperPackage);
        }
        configuration.getMapperRegistry().addMappers(mapperPackage);
        dataSource = buildDataSource(config);
        sqlSessionFactoryBean.setDataSource(dataSource);

        try {
            sessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        session = new SqlSessionTemplate(sessionFactory);

        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(getDataSource());
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    private DataSource buildDataSource(Config dbConfig) {
        PooledDataSource dataSource = DefaultDbConfiguration.defaultDataSource();
        dataSource.setDriver(dbConfig.getString("driver"));
        dataSource.setUsername(dbConfig.getString("username"));
        dataSource.setUrl(dbConfig.getString("url"));
        dataSource.setPassword(PwdUtils.decrypt(dbConfig.getString("password")));
        return dataSource;
    }

    private Config loadConfig(String name) {
        Config config = ConfigFactory.load();
        String path = "database." + name;
        if (!config.hasPath(path)) {
            throw new RuntimeException("config not found for database." + name);
        }
        return config.getConfig(path);
    }

    public String getName() {
        return this.name;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public SqlSessionTemplate getSession() {
        return session;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }
}
