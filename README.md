# ZhiHu-Crawler
简单的Java爬虫项目, 爬取知乎用户信息, 目前已爬取80w条.

使用WebMagic框架, Maven构建项目

# 数据展示
![数据展示](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/img/%E6%95%B0%E6%8D%AE%E5%B1%95%E7%A4%BA.png)

# 运行环境
JDK版本:10   低版本需手动替换代码中的var

数据库支持:SQL Server/MySql

# 运行
主程序入口:[Main.java](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/src/main/java/Crawler/Main.java)

初次运行请配置[jdbc.properties](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/config/jdbc.properties)和[cookies.txt](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/config/cookies.txt)

添加-s命令行参数可配置是否清空原有数据/线程数等

### 数据库配置
默认使用MySql持久化数据, 填入基本信息即可.

也支持持久化至本地的SQL Server, 请在启动程序时添加-s参数配置启动, 采用Windows身份验证.

### Cookies配置
在浏览器中登录知乎, 在开发者工具(F12)中找到请求头cookies字段中的z_c0键值对,填入cookies.txt中.

数量不限, 爬取速度(由线程数和爬取间隔决定)可随cookies数的增加而增加, 同时也可减少账号暂时被封的风险

### 运行示例
![运行示例](https://github.com/Sword-And-Rose/ZhiHu-Crawler/blob/master/img/%E8%BF%90%E8%A1%8C%E7%A4%BA%E4%BE%8B.png)

爬取过程中按Enter键停止爬虫(注:意外/强行关闭可能导致爬取记录丢失/下次爬取时向数据库中插入重复的值)
# 可视化分析
待更新
