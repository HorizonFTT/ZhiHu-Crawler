# ZhiHu-Crawler
简单的Java爬虫项目, 爬取知乎用户信息, 目前已爬取80w条.

使用WebMagic框架, Maven构建项目

## 数据展示
![数据展示](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/img/%E6%95%B0%E6%8D%AE%E5%B1%95%E7%A4%BA.png)

## 运行环境
JDK版本: __10__   低版本需手动替换代码中的var

数据库支持:SQL Server/MySql

## 运行
主程序入口: [Main.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Crawler/Main.java)

初次运行请配置[jdbc.properties](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/config/jdbc.properties)和[cookies.txt](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/config/cookies.txt)

添加 __-s__ 命令行参数可配置是否清空原有数据/线程数等,默认线程数为10

### 数据库配置
默认使用MySql持久化数据, 填入基本信息即可.

也支持持久化至本地的SQL Server, 请在启动程序时添加 __-s__ 参数配置启动, 采用Windows身份验证.

### Cookies配置
在浏览器中登录知乎, 在开发者工具(F12)中找到请求头cookies字段中的 __z_c0__ 键值对,填入cookies.txt中.

数量不限, 爬取速度(由线程数和爬取间隔决定)可随cookies数的增加而增加, 同时也可减少账号暂时被封的风险

### 运行示例
![运行示例](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/img/%E8%BF%90%E8%A1%8C%E7%A4%BA%E4%BE%8B.png)

爬取过程中按 __Enter__ 键停止爬虫(__注:意外/强行关闭可能导致爬取记录丢失/下次爬取时向数据库中插入重复的值__)

平均爬取速度: 1w条/h(5账号,10线程,2000+random(1000)sleep)

![Sql访问情况](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/img/Sql%E8%AE%BF%E9%97%AE%E6%83%85%E5%86%B5.png)

## 程序分析
所用框架语法参见[WebMagic文档](http://webmagic.io/docs/zh/)

项目分为主程序模块[Crawler](https://github.com/Sword-And-Rose/ZhiHu-Crawler/tree/master/src/main/java/Crawler),爬虫组件模块[Assembly](https://github.com/Sword-And-Rose/ZhiHu-Crawler/tree/master/src/main/java/Assembly),持久化模块[Database](https://github.com/Sword-And-Rose/ZhiHu-Crawler/tree/master/src/main/java/Database)

### Crawler
*  [Main.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Crawler/Main.java): 爬虫的配置,启动和终止

### Assembly
*  [DatabasePipeline.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Assembly/DatabasePipeline.java): 爬取信息的输出和数据的持久化

*  [MyDownloader.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Assembly/MyDownloader.java): 在每次下载前更新请求头信息

*  [MySpider.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Assembly/MySpider.java): cookies信息的处理,账号切换和爬取间隔的设置等

*  [Processor.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Assembly/Processor.java): 爬虫过程主体,包括信息的抽取,后续Url的获取等

### Database
*  [Database.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Database/Database.java): 实现操作数据库相关的功能

*  [User.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Database/User.java): 用户信息类

## 可视化分析
待更新
