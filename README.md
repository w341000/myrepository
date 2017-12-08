# 物流管理系统

## 项目简介
项目主要解决了物流公司对于物流单,人员及区域信息的管理调度.并且使用shiro进行权限控制,实现对于不同的用户(如管理员、取派员及客服)能操作的功能及看到的页面都是不同的.  
我负责的是所后端代码的编写(包括工作单模块,用户模块及区域模块等),及项目的搭建和mysql中表的设计.


## 主要技术
- Struts2、Spring、Hibernate (老牌SSH框架)
- JSP、JSTL、jQuery、EasyUI (EasyUI的分页插件很好用)
- junit (进行单元测试非常好用)
- Shiro (权限管理框架)
- Ztree (可以方便的实现基于权限动态显示菜单)

## 开发工具和环境
IDE:MyEclipse，自带maven插件  
依赖管理:Maven  
web容器:Tomcat 7.0.53（Maven Tomcat Plugin）
Jdk版本:JDK 1.7  
数据库:Mysql 5.7   
缓存:EhCache  
操作系统:Win7 操作系统
版本管理:GIT(视频中使用svn)

## 项目结构
`src`目录下保存了项目源代码  
项目使用hibernate进行持久层的开发,所以不需要手动建表,只有有`bos`这个数据库(可以在`src/main/resource/jdbc.properties`文件中修改相关信息)便能自动新建所需的表.  
`pom.xml`内含有tomcat7的maven插件,直接在项目上右键`Run As Maven Build` ,然后在Goals中填写`tomcat7:run`即可  

## 项目收获  
该项目是第一次使用maven和git进行开发的项目,以前在开发小项目时就曾觉得jar包之前版本依赖的复杂和源代码想回滚的麻烦,不得不说maven+git解决了很多问题,项目本身是我个人独立开发,所以没有遇到代码冲突的情况.  
物流管理系统作为练习用的项目还是学到了非常多的知识,比如EasyUI分页和DataGrid控件,使用ztree来动态生成按钮刘表,在MyEclipse中使用Hibernate的反转引擎生成pojo和xml映射文件等,都大大提升了开发效率,简化了开发难度.
