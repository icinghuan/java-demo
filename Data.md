##### 数据迁移
使用 [Mybatis Migrations](http://www.mybatis.org/migrations-maven-plugin/plugin-info.html)

生成器的配置文件在 src/mian/resource/generatorConfig.xml

新增一个表时，为了生成该表相关的代码，需要在配置文件中增加映射：

```
        <table schema="alpha" tableName="accounts" domainObjectName="Account">
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
        </table>
```
注意表名是复数，但是映射的Java对象一般是单数。

数据迁移的常见命令包括新建、应用、回滚、查看状态等

```
cd model
# 新建migration
mvn migration:new -Dmigration.description="create users"

# 应用migration到最新的变更
mvn migration:up

# 回滚上次变更
mvn migration:down

# 查看migration状态，可以展示哪些变更已经应用，哪些尚未应用
mvn migration:status
```

#### 代码生成
我们使用mybatis generator进行数据库表对应代码生成.

```
## 使用local的mysql, root账号, alpha数据库生成代码
## 生成的代码位于com.tigerbrokers.alpha.model.tables下
./generator

也可以直接运行以下maven命令：
 
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

```