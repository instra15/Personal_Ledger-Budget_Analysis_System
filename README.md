# PLBAS — 轻量复式记账与预算执行分析系统
 
> Personal Ledger & Budget Analysis System
 
一款基于**复式记账**核心原理的个人财务管理系统，内置 **预算目标 → 执行 → 预警** 三级业务闭环，实现从交易录入、借贷平衡校验到预算监控与报表分析的完整财务管理链路。
 
---
 
## 技术栈
 
| 层级 | 技术 |
|------|------|
| 运行时 | Java 17、Spring Boot 4.0.7 |
| 持久层 | Spring Data JPA、MySQL |
| 校验 | Jakarta Validation (`@Valid`, `@NotBlank`, `@Positive`…) |
| 文档 | springdoc-openapi 3.0.2 + Knife4j 4.5.0 |
| 工具 | Lombok |
| 构建 | Maven |
 
---
 
## 快速开始
 
### 1. 环境要求
 
- JDK 17+
- MySQL 8.0+
- Maven 3.8+
 
### 2. 创建数据库
 
```sql
CREATE DATABASE pleas CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```
 
数据库名与 `src/main/resources/application.properties` 中 `spring.datasource.url` 对应。
 
### 3. 配置数据库密码
 
在 `src/main/resources/application.properties` 中，密码通过环境变量注入：
 
```properties
spring.datasource.password=${DATABASE_PASSWORD}
```
 
启动前请设置环境变量：
 
```bash
export DATABASE_PASSWORD=your_mysql_password
```
 
### 4. 启动项目
 
```bash
./mvnw spring-boot:run
```
 
启动成功后访问：
- **Swagger UI**：`http://localhost:8080/swagger-ui.html`
- **Knife4j 增强 UI**：`http://localhost:8080/doc.html`
 
---
 
## 项目结构
 
```
src/main/java/com/plbas/plbas/
├── PersonalLedgerBudgetAnalysisSystemApplication.java   # Spring Boot 启动类
├── Response.java                                        # 统一响应封装
├── config/
│   └── Config.java                                      # OpenAPI 文档配置
├── controller/
│   ├── AccountController.java                           # 账户管理
│   ├── CategoryController.java                          # 收支分类管理
│   ├── TransactionController.java                       # 复式记账交易
│   ├── BudgetController.java                            # 预算设定与预警
│   └── ReportController.java                            # 统计报表
├── entity/
│   ├── Account.java                                     # 账户
│   ├── Category.java                                    # 收支分类
│   ├── Transaction.java                                 # 交易主表
│   ├── Entry.java                                       # 会计分录（借/贷）
│   └── Budget.java                                      # 月度预算
├── enums/
│   ├── AccountType.java                                 # ASSET / LIABILITY / EQUITY
│   ├── EntryDirection.java                              # DEBIT / CREDIT
│   └── CategoryDirection.java                           # INCOME / EXPENSE
├── exception/
│   ├── BusinessException.java                           # 业务异常
│   └── GlobalExceptionHandler.java                      # 全局异常处理
├── repository/                                          # JPA Repository
├── service/
│   ├── DTO/                                             # 数据传输对象
│   ├── Impl/                                            # Service 接口与实现
│   └── VO/                                              # 视图对象
```
 
---
 
## 核心功能
 
### 一、账户与复式记账（核心创新点）
 
| 操作 | 方法 | 路径 |
|------|------|------|
| 创建账户 | POST | `/api/account/add` |
| 查询所有账户 | GET | `/api/account/get` |
| 新增交易（复式记账） | POST | `/api/transaction/add` |
| 查询交易列表（分页） | GET | `/api/transaction/get` |
| 查询单笔交易详情 | GET | `/api/transaction/get/{txNo}` |
 
**核心逻辑**（`TransactionServiceImpl.java`）：
 
1. 生成 UUID 全局唯一流水号 `txNo`
2. 持久化交易主记录 `Transaction`
3. 构建 ≥2 条分录 `Entry`（一条 DEBIT + 一条 CREDIT）
4. **借贷平衡断言**：借方合计 ≠ 贷方合计 → 抛 `BusinessException` 触发回滚
5. 更新涉及账户余额（借加贷减）
6. 同步更新预算消耗 & 检查阈值预警
 
整个过程在 `@Transactional` 保护下执行，全或无。
 
### 二、分类标签管理
 
| 操作 | 方法 | 路径 |
|------|------|------|
| 创建分类 | POST | `/api/category/add` |
| 查询分类列表 | GET | `/api/category/get` |
 
分类带有方向枚举 `INCOME` / `EXPENSE`，通过 `@Enumerated(EnumType.STRING)` 持久化。
 
### 三、预算设定与执行预警
 
| 操作 | 方法 | 路径 |
|------|------|------|
| 设定月度预算 | POST | `/api/budget/add` |
| 查询预算详情 | GET | `/api/budget/get/{id}` |
| 查询预算列表（分页） | GET | `/api/budget/get` |
| 删除预算 | DELETE | `/api/budget/delete/{id}` |
 
**业务闭环**：
 
```
设定目标 → 执行交易 → 实时累计判断 → 阈值预警 → 调整行为
```
 
每笔支出交易在同一个 `@Transactional` 内自动累加 `Budget.currentSpent`；若达到 `budgetAmount × thresholdPercent`（默认 80%），响应体携带 `warning` 字段提示超支。
 
### 四、统计报表
 
| 操作 | 方法 | 路径 |
|------|------|------|
| 月度收支趋势 | GET | `/api/report/get/monthTrend?yearMonth=&direction=` |
| 分类支出占比 | GET | `/api/report/get/categoryPie` |
| 账户总余额 | GET | `/api/report/get/totalBalance` |
 
---
 
## 数据模型
 
### 表间关系
 
```
account 1 ──< entry >── 1 transaction 1 ──< entry >── 1 category
                                               │
                                           budget (多对一 category)
```
 
### 核心约束
 
- **借贷平衡**：同一 `tx_no` 下所有分录金额按 DEBIT/CREDIT 方向求和必须相等
- **流水号唯一**：`tx_no` 由 UUID 生成，全局唯一
- **预算唯一**：同一分类 + 同一月份只能设定一个预算（联合唯一约束）
 
---
 
## 统一响应格式
 
所有接口返回 `Response<T>` 结构：
 
```json
{
  "data": { ... },
  "success": true,
  "errorMsg": null
}
```
 
异常时 `success = false`，`errorMsg` 携带错误信息，由 `GlobalExceptionHandler` 统一处理。
 
---
 
## 配置说明
 
配置文件：`src/main/resources/application.properties`
 
| 配置项 | 说明 |
|--------|------|
| `spring.datasource.url` | MySQL 连接地址，数据库名 `pleas` |
| `spring.datasource.password` | 通过 `${DATABASE_PASSWORD}` 环境变量注入 |
| `logging.file.name` | 日志文件路径 `logs/PLBAS.log`，单文件最大 10MB，保留 7 天 |
| `springdoc.swagger-ui.path` | Swagger UI 访问路径 |
 
---
 
## 许可证
 
本项目仅供个人学习与参考使用。