package top.icinghuan.demo.dao.support;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
public class DaoConfiguration {

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(DB.db().getDataSource());
    }

    @Bean
    @Primary
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(txManager());
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory getSessionFactory() {
        return DB.db().getSessionFactory();
    }

    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate getSession() {
        return DB.db().getSession();
    }

    @Bean("dataSource")
    public DataSource getDataSource() {
        return (DataSource) DB.db().getDataSource();
    }



}