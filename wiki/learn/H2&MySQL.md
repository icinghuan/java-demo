# H2数据库和MySQL的兼容问题

**H2数据库是一个开源的关系型数据库**

```
Welcome to H2, the Java SQL database. The main features of H2 are:

Very fast, open source, JDBC API
Embedded and server modes; in-memory databases
Browser based Console application
Small footprint: around 2 MB jar file size
```
使用H2内存数据库来模拟数据库环境，能够保证单元测试不依赖于数据库。

但是H2和MySQL还是略有不同的，在开发时写的SQL都是以MySQL为准，很有可能在H2数据库中无法运行或遇到冲突。

### 1. 索引名全局唯一
索引名全局唯一，如果起了重复的索引名，H2将会报错：`Cause: org.H2.jdbc.JdbcSQLException: Index "CREATED_AT" already exists;`
解决方法：
- 起名时要起一个全局不重复的名字
- 不起索引名，这样MySQL和H2都会产生一个默认索引名，且在H2中不会发生索引名重复的问题。

但不起索引名，以后修改索引的时候会有新的问题。MySQL默认命名和H2不一样，会导致有个执行不了，这时只有再修改原先的sql，起一个索引名才能解决。

### 2. H2中 tinyint(1) 不转为boolean



### 3. H2 不支持 SELECT FOR UPDATE



### 4. H2 不支持 UPDATE JOIN

```sql
UPDATE t1 JOIN t2 ON t1.account_id = t2.account_no  set created_by_user_id = user_id;
```

在H2中使用JOIN会报错`Cause: org.h2.jdbc.JdbcSQLException: Syntax error`

解决办法:
```sql
update t1 set created_by_user_id = (select user_id from t2 where t1.account_id = t2.account_no);
```

写到other_scripts中，不放在H2中执行

### 5. H2不支持表级别的Comment
```sql
CREATE TABLE `test` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `value` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'value',
  `desc` varchar(100) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `created_at` (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'comment';
```
在H2中，列级别的COMMENT是支持的，但是表级别的COMMENT不支持

解决方法：删掉表级别的COMMENT即可

### 6. H2不支持`\'`与`"`
有如下SQL，其中一个字段值中有单引号：
```sql
INSERT INTO `test`(`value`, `desc`) VALUES (0,'\'abc\'');
```
MySQL支持双引号包含字符串，可以把`'\'abc\''`改为`"'abc'"`。
而在H2中，这两种写法都会报错`Cause: org.h2.jdbc.JdbcSQLException: Syntax error in SQL statement`

解决方法：写到other_scripts中，不放在H2中执行
  
### 7. 列别名无法用于子查询
如下SQL可以在MySQL中执行，但是不能再H2中执行，这里把查询出来的StartAreaCity字段作为StartAreaCityText字段的子查询使用

```sql
<sql id="Base_Column_List" >
    Id, StartArea, ArrivalArea, Updater, UpdateTime, Status
       , (select pid from ddc_area where id = (select pid from ddc_area where id = ddc_line.StartArea)) StartAreaCity
       , (select area from ddc_area where id =  StartAreaCity) StartAreaCityText
</sql >
```
  
只得修改成如下：

```sql
<sql id="Base_Column_List" >
    Id, StartArea, ArrivalArea, Updater, UpdateTime, Status
       , (select pid from ddc_area where id = (select pid from ddc_area where id = ddc_line.StartArea)) StartAreaCity
       , (select area from ddc_area where id = (select pid from ddc_area where id = (select pid from ddc_area where id = ddc_line.StartArea))) StartAreaCityText
</sql >
```
  
### 8. @:语法不支持
在MySQL中实现取行号时，采用了如下方法：

```sql
<select id="selectByExample" resultMap="BaseResultMap" parameterType="com.szyciov.entity.chargerule.PriceRuleExample" >
    select
    <if test="distinct" >
      distinct
    </if >
    <include refid="Base_Column_List" />
    , (@rownum := @rownum + 1) as RowNum
    from ddc_price_rule, (select @rownum := #{page.begin} ) r
    <if test="_parameter != null" >
      <include refid="Example_Where_Clause" />
    </if >
    <if test="orderByClause != null" >
      order by ${orderByClause}
    </if >
    <if test="page != null" >
      limit #{page.begin} , #{page.length}
    </if >
</select >
```

其中@rownum的写法H2不支持，只能采用了程序的方式来实现行号。


**由于H2和MySQL的不同，在开发时，如果做了数据库改动，最好在提交前跑一下单元测试，看一下H2数据库建表时是否报错，确保不会因数据库的改动导致单测运行失败。**


---

### 参考

http://www.h2database.com/html/grammar.html

http://www.H2database.com/html/features.html#compatibility

http://www.alanzeng.cn/2016/07/unit-test-h2-database/

http://matthewcasperson.blogspot.com/2013/07/exporting-from-MySQL-to-H2.html