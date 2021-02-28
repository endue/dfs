# 说明
模拟dfs实现文件的上传、备份功能

# 2021-1-1
## 实现功能
### namenode
- 实现服务注册
- 实现服务心跳
- 实现服务定时清理
- 实现目录创建以及同步到editlog中
- 实现editlog的刷盘
### datanode
- 服务注册
- 定时心跳
## 架构图
![架构图](img/2021-01-01/img.jpg)

# 2021-1-3
## 实现功能
### namenode
- 实现backupnode定时拉取文件
## backupnode
- 实现定时拉取namenode上文件
- 实现checkpoint定时刷新内存文件目录树到磁盘

# 2021-2-28
## 实现功能
### datanode
- 实现datanode启动时基于checkpoint文件名恢复已经checkpoint的maxTxid,不需要每次fetch的时候都从0开始避免了重启时EditlogFetcher.fetchedMaxTxid为0的问题
- 解决namenode收到backupnode上报checkpoint文件时，创建文件路径不存在的问题
![架构图](img/2021-01-01/img.jpg)