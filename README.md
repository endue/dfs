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
