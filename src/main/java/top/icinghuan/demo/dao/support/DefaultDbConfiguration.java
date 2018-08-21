package top.icinghuan.demo.dao.support;

import com.github.pagehelper.PageHelper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.*;

import java.util.Properties;

@org.springframework.context.annotation.Configuration
public class DefaultDbConfiguration {

    public static Configuration defaultConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setLogImpl(Slf4jImpl.class);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        Config config = ConfigFactory.load();
        if (config.hasPath("database.defaultStatementTimeout")) {
            configuration.setDefaultStatementTimeout(config.getInt("database.defaultStatementTimeout"));
        } else {
            configuration.setDefaultStatementTimeout(30);
        }

        registerTypeHandler(configuration);

        PageHelper pageHelper = new PageHelper();
        pageHelper.setProperties(defaultPageHelperProperties());
        configuration.addInterceptor(pageHelper);
        return configuration;
    }

    private static void registerTypeHandler(Configuration configuration) {
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
        registry.register(InstantTypeHandler.class);
        registry.register(LocalDateTimeTypeHandler.class);
        registry.register(LocalDateTypeHandler.class);
        registry.register(LocalTimeTypeHandler.class);
        registry.register(ZonedDateTimeTypeHandler.class);
    }

    public static PooledDataSource defaultDataSource() {
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setPoolMaximumActiveConnections(20);
        pooledDataSource.setPoolMaximumIdleConnections(10);
        pooledDataSource.setPoolPingEnabled(true);
        pooledDataSource.setPoolPingQuery("SELECT 1");
        pooledDataSource.setPoolMaximumCheckoutTime(20000);
        pooledDataSource.setPoolPingConnectionsNotUsedFor(20000);
        return pooledDataSource;
    }


    private static Properties defaultPageHelperProperties() {
        Properties properties = new Properties();
        // 4.0.0 以后版本可以不设置该参数
        properties.setProperty("dialect", "mysql");
        // 该参数默认为false
        // 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
        // 和startPage中的pageNum效果一样
        properties.setProperty("offsetAsPageNum", "true");
//            该参数默认为false
//            设置为true时，使用RowBounds分页会进行count查询
        properties.setProperty("rowBoundsWithCount", "true");
//            设置为true时，如果pageSize = 0 或者RowBounds.limit = 0 就会查询出全部的结果
//           （相当于没有执行分页查询，但是返回结果仍然是Page类型）
        properties.setProperty("pageSizeZero", "true");
//            3.3 .0 版本可用 - 分页参数合理化，默认false禁用
//            启用合理化时，如果pageNum< 1 会查询第一页，如果pageNum > pages会查询最后一页
//            禁用合理化时，如果pageNum< 1 或pageNum > pages会返回空数据
        properties.setProperty("reasonable", "false");
//            支持通过Mapper接口参数来传递分页参数
        properties.setProperty("supportMethodsArguments", "true");
//            always总是返回PageInfo类型, check检查返回类型是否为PageInfo, none返回Page
        properties.setProperty("returnPageInfo", "none");
        return properties;
    }
}
