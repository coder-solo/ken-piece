## 记录各种零碎内容

### Lombok的使用

Class: User(e.)

### 自定义枚举Valid

### 编译期注解

### Okhttp的使用

Class: OkhttpUtils
Reference: [Okhttp3基本使用](https://www.jianshu.com/p/da4a806e599b)

### Swagger2

Class: SwaggerConfig

### Redis

Class: RedisConfig,RedisServiceImpl
File: application.yml
Test: RedisTest,Swagger-RedisController
ps:不开启redis影响编译运行，代码已注释，需要的自己打开。

### Shrio

Class: PieceShiroConfig,PieceShiroRealm,PieceController
Test: Swagger-PieceController

### actuator

http://127.0.0.1:37927/actuator/prometheus

### 设计

#### 代理模式

Class: StaticProxySubject,StaticProxySubject
Test: ProxyTest

### 共通类

* CharacterUtils 字符共通类

### 有意思的代码

* Null 空对象的参数
