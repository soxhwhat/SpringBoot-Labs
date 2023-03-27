<https://www.iocoder.cn/Spring-Boot/MinIO/?github>
### MinIO简介
它适合存储海量的非结构化的数据，例如说图片、音频、视频等常见文件，备份数据、容器、虚拟机镜像等等，小到 1 KB，大到 5 TB 都可以支持。

国内阿里巴巴、腾讯、百度、华为、中国移动、中国联通等企业在使用 MinIO，甚至不少商业公司二次开发 MinIO 来提供商业化的云存储产品。

>疑问：为什么越来越少使用 FastDFS 实现文件存储服务呢？
部署运维复杂、无官方文档、缺乏长期维护的团队、性能较差、未提供 Docker & Kubernetes 集成方案等等原因。

### 安装
dockers：docker run -p 9000:9000 -p 9001:9001 -e "MINIO_ACCESS_KEY=admin" -e "MINIO_SECRET_KEY=password" minio/minio server /data --console-address ":9001" # /Users/yunai/minio 存储目录；--console-address 是 UI 界面的端口

