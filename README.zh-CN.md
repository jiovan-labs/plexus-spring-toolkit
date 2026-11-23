[English](README.md) | [简体中文](README.zh-CN.md) | [繁體中文](README.zh-TW.md)

# Plexus Spring 工具包

玖漫 plexus-spring-toolkit 是面向 Spring 体系的工具类集合，用于增强 Spring 的能力。

## 快速开始

- 导入依赖

```xml
    <dependency>
        <groupId>com.jiovan.plexus</groupId>
        <artifactId>plexus-spring-toolkit</artifactId>
        <version>LATEST</version>
    </dependency>
```

- 其中包含许多有用的包，可能对你大有帮助，可按需使用。

| 包          | 描述                                                       |
|-------------|------------------------------------------------------------|
| annotation  | 标注在元素上以启用某些能力                                 |
| audit       | 针对 Spring Data 的审计，如关系型数据库与 Mongo            |
| cache       | 对 Spring Cache 的增强                                     |
| cglib       | 对 cglib 的增强组件，如 Bean 复制器                        |
| constant    | 各类常量                                                   |
| context     | 对 Spring 上下文的支持                                     |
| enumeration | 实用枚举                                                   |
| event       | 自定义事件与监听器                                         |
| exception   | 用于满足自定义需求的扩展异常                               |
| filter      | Spring 的附加过滤器                                        |
| general     | 通用实体与 BSON 文档                                       |
| identity    | 帮助生成标识符，通常为 UUID                                |
| interceptor | Web 拦截器类                                               |
| jackson     | Jackson 的额外组件，包括序列化器、反序列化器、策略、处理器 |
| message     | 对消息源的支持                                             |
| processor   | 处理器的实现类                                             |
| registrar   | 对注册器的支持                                             |
| resolver    | 对解析器的支持                                             |
| security    | 对 Spring Security 的扩展类                                |
| servlet     | 对 Servlet 的支持                                          |
| structure   | 用于表示数据组合的多种结构                                 |
| support     | 继承 Spring 组件以实现特定用途的组件                       |
| util        | 可加速开发的强大工具类                                     |

## 文档

- GitHub：https://github.com/jiovan-labs/plexus-spring-toolkit

## 环境要求

- jdk 17+

## 许可证

本项目采用 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) 许可证。

请参阅 `NOTICE.txt` 文件了解所需的声明与归属信息。

## 捐赠

喜欢这个工具包吗？欢迎[向我们捐赠](https://jiovan.com/donation)以支持开发。

## 版权

北京玖漫科技有限公司

## 网站

- 玖漫：https://jiovan.com
