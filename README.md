# 使用 OpenDaylight 开发自己的控制器应用

### 搭建项目骨架
[官方地址](https://docs.opendaylight.org/en/stable-neon/developer-guide/developing-apps-on-the-opendaylight-controller.html?highlight=mvn%20archetype%3Agenerate)

ODL 提供了官方的 archetypes，可以通过命令行或者IDE集成简化项目创建的工作。可以在ODL Nexus仓库查看 archetypes 的[版本](https://nexus.opendaylight.org/#nexus-search;gav~org.opendaylight.archetypes~opendaylight-startup-archetype~~~~kw,versionexpand)，选择自己需要的版本，尽量选择稳定版的最新版本。

现在创建一个叫`labcontroller `的项目，基于 ODL Neon SR2 版本。

```
mvn archetype:generate \
-DarchetypeGroupId=org.opendaylight.archetypes \
-DarchetypeArtifactId=opendaylight-startup-archetype \
-DarchetypeVersion=1.1.2 \
-DarchetypeRepository=https://nexus.opendaylight.org/content/repositories/public/ \
-DarchetypeCatalog=remote \
-DinteractiveMode=false \
-DgroupId=com.xietaojie.lab \
-Dpackage=com.xietaojie.lab \
-DartifactId=labcontroller \
-Dversion=1.0.0 \
-Dcopyright=xietaojie \
-DcopyrightYear=2019
```
框架生成完后的目录结构：

```
labcontroller
├── api        | 通过 YANG 定义项目的模型和接口
├── artifacts  | 项目 archetype
├── cli        | karaf cli命令开发
├── features   | 添加需要依赖的Feature，以及定义自己的 feature 提供给外部
├── impl       | 业务逻辑实现
├── it         | 集成测试
├── karaf      | 打包目录
├── src        | 
└── pom.xml    | 项目根 pom 文件
```

编译：`mvn -T 4 clean install -Dmaven.test.skip=true -DskipTests -Dcheckstyle.skip=true -Dmaven.javadoc.skip=true`

编译之后，会在 karaf 模块下的 target 目录下生成包，jar/tar.gz/zip格式的包都有，解压后其实就是 assembly 目录下的内容，可执行文件在 bin 子目录里。想要在前台启动项目，只需要执行`./karaf`命令，即可完成启动并进入 karaf 命令行。在后台启动项目，可以执行`./start`命令，然后通过`./karaf -u karaf`命令进入 karaf 命令行模式。
