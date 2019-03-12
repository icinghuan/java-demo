# H2数据库和MySQL的兼容问题

H2数据库：开源的关系型数据库

```
Welcome to H2, the Java SQL database. The main features of H2 are:

Very fast, open source, JDBC API
Embedded and server modes; in-memory databases
Browser based Console application
Small footprint: around 2 MB jar file size
```

MySQL数据库


h2数据库的坑？ 比如索引名，sql略有不同（alter column），tinyint(1) 0/1不转为false/true
不起索引名，以后修改索引的时候会有新的问题。mysql默认命名和H2不一样，会导致有个执行不了
H2 不支持 SELECT ... For Update
可能H2对临时表支持不够友好，放在migration中会报语法错误，放在这里
和Mysql的兼容性问题

https://blog.csdn.net/achuo/article/details/80624223

1. 索引名全局不重复
不起索引名，以后修改索引的时候会有新的问题。mysql默认命名和H2不一样，会导致有个执行不了

2. tinyint(1) 0/1不转为false/true

3. 

http://matthewcasperson.blogspot.com/2013/07/exporting-from-mysql-to-h2.html

```

```

1) 不支持表级别的Comment
有表SQL如下：

CREATE TABLE `ddc_line` (
  `Id` varchar(36) NOT NULL COMMENT '序号',
  `StartArea` int(11) DEFAULT NULL COMMENT '出发区域',
  `ArrivalArea` int(11) DEFAULT NULL COMMENT '目的区域',
  `Updater` varchar(36) DEFAULT NULL COMMENT '更新人',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间' ,
  `Status` int(11) DEFAULT NULL COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT= '区域路线信息列表' ;
列名后面的COMMENT是支持的，但是最后面) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT= '区域路线信息列表' ; 中的COMMENT不支持。删掉后面的COMMENT即可。

2) 插入语句的单引号中的\'不支持
有如下SQL，其中一个字段存的就是另一个SQL，里面带有单引号：

INSERT INTO `dataauthorityconfig` VALUES ( '1', '部门权限', 'select d.UserId, a.RoleId,b.Id DynamicId,b.DeptName DynamicName,c.ConfigName,c.ConfigType,a.RootDynamicId\n  from RoleDataAuthority a\n left join Dept b on a.DynamicId=b.Id\n left join DataAuthorityConfig c on a.DataAuthorityConfigId=c.Id\n left join RoleUser d on d.RoleId=a.RoleId\n left join `User` e on d.UserId=e.Id\n where a.`Status`=1 and b.`Status`=1 and d.`Status`=1 and e.`Status`=1\n and c.Id={0} and e.LoginName=\'{1}\'', '1', '2', null, null , '2016-05-27 14:30:49' , '1' , '1' , null, '1');
MySQL支持双引号包含字符串，可以把内容中包含的单引号改为双引号，但其他情况可能会涉及到业务调整。另外，不能将包含字符串的单引号改为双引号，H2会把双引号中的内容当做列名处理。

3) H2 UNIQUE KEY是数据库级别的
H2 UNIQUE KEY不是表级别的，MySQL是表级别的，转为H2后容易出现UNIQUE KEY重复。删掉UNIQUE KEY或者修改KEY的名称即可。

4) 无法执行多个Update语句
如下SQL配置可以在MySQL中执行多次Update，但是H2执行多条就会报错，说parameterIndex有问题，执行一条没有问题。这个问题暂时没有替代解决方案，我的单元测试就只测试了插入一条数据。

</update >
    <update id="deleteByParam" parameterType="com.szyciov.entity.chargerule.FloatRatio" >
       update ddc_float_ratio set status = 2 where status = 1
       <if test="type != 0">
              and type = ${type}
       </if >
       <if test="year != 0">
              and year = ${year}
       </if >
       <if test="month != 0">
              and month = ${month}
       </if >
  </update >
5) 列别名无法用于子查询
如下SQL可以在MySQL中执行，但是不能再H2中执行，这里把查询出来的StartAreaCity字段作为StartAreaCityText字段的子查询使用

  <sql id="Base_Column_List" >
    Id, StartArea, ArrivalArea, Updater, UpdateTime, Status
       , (select pid from ddc_area where id = (select pid from ddc_area where id = ddc_line.StartArea)) StartAreaCity
       , (select area from ddc_area where id =  StartAreaCity) StartAreaCityText
  </sql >
只得修改成如下：

  <sql id="Base_Column_List" >
    Id, StartArea, ArrivalArea, Updater, UpdateTime, Status
       , (select pid from ddc_area where id = (select pid from ddc_area where id = ddc_line.StartArea)) StartAreaCity
       , (select area from ddc_area where id = (select pid from ddc_area where id = (select pid from ddc_area where id = ddc_line.StartArea))) StartAreaCityText
  </sql >
6) @:语法不支持
在MySQL中实现取行号时，采用了如下方法：

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
其中@rownum的写法H2不支持，我只能采用了程序的方式来实现行号。

但是H2支持@，参见http://www.h2database.com/html/grammar.html#set__。


---

参考：

https://github.com/h2database/h2database

https://blog.csdn.net/achuo/article/details/80624223

http://www.h2database.com/html/features.html#compatibility