## 项目介绍

Freedom Boot 是一款基于 Spring Boot 2.0 + MySQL 开发的后台管理系统，提供用户身份认证和授权的核心功能，同时扩展系统监控、代码生成和文件管理功能，是开发者接私活的神器。

[![](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/moonlightL/ml-blog/blob/master/LICENSE)
![](https://img.shields.io/badge/language-Java-blue.svg)

## 项目特点

```
1.界面美观、简洁，内置丰富的主题
2.目录结构分层明了，便于维护、扩展开发
3.代码高度抽象，约定大于配置
4.内置权限管理功能，灵活控制模块、菜单以及页面按钮
5.内部扩展模块，即插即用：
    - 代码生成器功能，帮助开发者生成前后端代码文件，大大节省 70% 开发时间
    - 系统监控功能，方便用户查看和监控服务器相关信息
    - 文件管理功能，集成七牛云、OSS 对象管理工具
    - 任务管理功能，动态添加和管理定时器任务
6.前端封装 freedom-core 工具，方便灵活易用，是不熟练前端技术的开发者的福音
```

## 项目功能

```
1.用户管理：提供用户数据管理以及角色授权功能
2.角色管理：提供角色数据管理以及权限授权功能
3.权限管理：提供权限数据管理功能
4.日志管理：提供用户操作日志查询功能
5.数据表管理：提供一键生成代码功能
6.系统信息：提供查询服务器信息功能
7.SQL监控：提供查看数据库连接池等信息功能
8.文件上传：提供文件管理功能
9.文件配置：提供管理文件配置功能
10.任务管理：提供动态添加和管理定时器任务功能
```

## 项目结构

```
freedom-boot
├─freedom-boot-business                  -- 业务模块
├─freedom-boot-common                    -- 通用模块  
├─freedom-boot-core                      -- 核心模块
├─freedom-boot-extensions                -- 扩展模块
│  ├─freedom-boot-extensions-file        -- 扩展模块-文件
│  ├─freedom-boot-extensions-generator   -- 扩展模块-代码生成器
│  ├─freedom-boot-extensions-monitor     -- 扩展模块-系统监控
│  └─freedom-boot-extensions-task        -- 扩展模块-定时器任务
└─freedom-boot-web                       -- 启动模块（管理总配置文件和启动类）
```

## 技术选型

Freedom Boot 的诞生离不开以下框架，在此感谢开源的贡献者们：

### 后端

名称 | 描述 | 地址
---|--- | ---
Spring Boot 2.1.5 | 优秀的 web 应用开发框架 | [前往](https://github.com/spring-projects/spring-boot)
MyBatis 3.4.6 | 简单易用的持久层框架 | [前往](https://github.com/mybatis/mybatis-3)
tkMapper 2.1.5 | 通用 Mapper 框架 | [前往](https://gitee.com/free/Mapper)
PageHelper 1.2.10 | tkMapper 作者编写另一套分页框架 | [前往](https://gitee.com/free/Mybatis_PageHelper)
Shiro 1.4.0 | Apache 下简单易用的身份认证授权框架架 | [前往](http://shiro.apache.org/)
Flywaydb 5.2.4 | 数据库版本管理工具 | [前往](https://flywaydb.org/)
Druid 1.1.10 | 阿里巴巴开源的数据库连接池框架 | [前往](https://github.com/alibaba/druid)
Lombok 1.18.8 | 使用注解形式来简化 Java 代码 | [前往](https://www.projectlombok.org/)
hibernate-validator 6.0.14 | 后端数据校验 | [前往](https://hibernate.org/validator/)
thymeleaf 3.0.11 | web 开发模板引擎 | [前往](https://www.thymeleaf.org/)
oshi 3.13.2| 搜集服务器信息 | [前往](https://github.com/oshi/oshi)
EasyCaptcha 1.5.0 | 图形验证码 | [前往](https://github.com/whvcse/EasyCaptcha)
ip2region 1.7.2| 最自由的ip地址查询库 | [前往](https://github.com/lionsoul2014/ip2region)
quartz 2.3.1 | 作业调度框架 | [前往](http://www.quartz-scheduler.org/)

### 前端

名称 | 描述 | 地址
---|--- | ---
jQuery| JavaScript 库 | [前往](https://jquery.com/)
layer| 非常好用的弹层工具 | [前往](http://layer.layui.com/)
bootstrap | 用于开发响应式布局、移动设备优先的 WEB 项目 | [前往](https://getbootstrap.com/)
bootstrap-table | bootstrap 风格的 table 工具 | [前往](https://bootstrap-table.com/)
bootstrap-datepicker| bootstrap 风格的日期工具 | [前往](https://bootstrap-datepicker.readthedocs.io/en/latest/)
bootstrap-select | bootstrap 风格的 select 工具 | [前往](http://developer.snapappointments.com/bootstrap-select/)
BootstrapMenu| bootstrap 风格的右键菜单 | [前往](http://www.jq22.com/jquery-info5376)
jquery-treegrid | 表格树工具 | [前往](http://maxazan.github.io/jquery-treegrid/)
bootstrap-validator| 前端数据校验工具 | [前往](http://bootstrapvalidator.votintsev.ru/getting-started/)
jquery-tab | 自定义的页面列表 Tab 插件 | [前往](https://github.com/moonlightL/jquery-tab)
jsencrypt | 加密工具 | [前往](http://travistidwell.com/jsencrypt/)
zTree | 多功能的“树”插件 | [前往](http://www.treejs.cn/v3/api.php)

## 安装

```
1.下载项目 git clone https://github.com/moonlightL/freedom-boot.git

2.修改项目中数据库的连接配置(url、username、password)

3.在数据库中创建上述 url 对应的数据库名称，默认数据库名为 freedom-boot

4. mvn clean 后启动项目即可(在 freedom-boot-web 中启动)
```

管理员账户： admin/admin 

游客账户： guest/123456

## 效果图

![](http://images.extlight.com/freedom-boot-01.jpg)

![](http://images.extlight.com/freedom-boot-02.jpg)

![](http://images.extlight.com/freedom-boot-03.jpg)

![](http://images.extlight.com/freedom-boot-04.jpg)

![](http://images.extlight.com/freedom-boot-05.jpg)

![](http://images.extlight.com/freedom-boot-06.jpg)

![](http://images.extlight.com/freedom-boot-07.jpg)

![](http://images.extlight.com/freedom-boot-08.jpg)


## 日志

```
2019-08-13 正式开源
2019-08-14 新增定时器任务模块
```