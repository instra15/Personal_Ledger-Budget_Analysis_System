# 轻量复式记账与预算执行分析系统

> Personal Ledger & Budget Analysis System（PLBAS）

---

## 一、项目定位

一款基于**复式记账**核心原理的个人财务管理系统，内置**预算目标→执行→预警**三级业务闭环，实现从交易录入、借贷平衡校验到预算监控与报表分析的完整财务管理链路。

---

## 二、项目规模

| 维度 | 数量 |
|------|------|
| 数据库表 | 5 张 |
| RESTful 接口 | 约 13 个 |
| 核心模块 | 4 个（账户与复式记账、分类标签管理、预算设定与执行预警、统计报表） |
| 代码包层级 | controller / service / repository / entity / dto / exception / enums |

---

## 三、项目目录结构树

```
src/main/java/com/plbas/
├── PlbasApplication.java              # Spring Boot 启动类
├── config/
│   └── OpenApiConfig.java             # Swagger 3 / OpenAPI 配置
├── controller/
│   ├── AccountController.java         # 账户管理接口
│   ├── CategoryController.java        # 分类标签接口
│   ├── TransactionController.java     # 复式记账交易接口
│   ├── BudgetController.java          # 预算设定与预警接口
│   └── ReportController.java          # 统计报表接口
├── service/
│   ├── AccountService.java            # 账户服务接口
│   ├── impl/
│   │   ├── AccountServiceImpl.java
│   │   ├── CategoryServiceImpl.java
│   │   ├── TransactionServiceImpl.java  # 核心：复式记账事务编排
│   │   ├── BudgetServiceImpl.java       # 预算预警逻辑
│   │   └── ReportServiceImpl.java
│   └── dto/
│       ├── AccountCreateDTO.java
│       ├── TxCreateDTO.java            # 交易创建入参（含借贷双方）
│       ├── BudgetCreateDTO.java
│       └── CategoryCreateDTO.java
├── repository/
│   ├── AccountRepository.java         # extends JpaRepository
│   ├── CategoryRepository.java
│   ├── TransactionRepository.java
│   ├── EntryRepository.java
│   └── BudgetRepository.java
├── entity/
│   ├── Account.java
│   ├── Category.java                  # @Enumerated 方向枚举
│   ├── Transaction.java              # 交易主表，共享 txNo
│   ├── Entry.java                    # 分录表，一借一贷
│   └── Budget.java
├── exception/
│   ├── BusinessException.java         # 通用业务异常基类
│   └── GlobalExceptionHandler.java   # @RestControllerAdvice 全局处理
└── enums/
    ├── AccountType.java               # ASSET / LIABILITY / EQUITY
    ├── EntryDirection.java            # DEBIT / CREDIT
    └── CategoryDirection.java         # INCOME / EXPENSE

src/main/resources/
└── application.yml                    # 数据源、JPA、日志配置

src/test/java/com/plbas/
├── service/                           # Service 层单元测试
└── controller/                        # MockMvc 接口测试
```

---

## 四、数据库表设计

### 4.1 表清单

| 表名 | 主键 | 外键 / 唯一约束 | 核心字段 | 说明 |
|------|------|------------------|----------|------|
| `account` | `id` bigint | `name` 唯一 | `name`, `type` (ENUM), `balance` decimal(14,2) | 账户表（现金/银行卡/信用卡） |
| `category` | `id` bigint | (`name`, `direction`) 联合唯一 | `name`, `direction` (ENUM), `description` | 收支分类标签 |
| `transaction` | `id` bigint | `tx_no` 唯一 | `tx_no` varchar(32), `date` date, `remark` varchar(255) | 交易主表，一笔交易一条记录 |
| `entry` | `id` bigint | `transaction_id` → transaction.id | `transaction_id`, `account_id`, `category_id`, `amount` decimal(14,2), `direction` ENUM | 分录表，每笔≥2条 |
| `budget` | `id` bigint | (`category_id`, `year_month`) 唯一 | `category_id`, `year_month` char(7), `budget_amount` decimal(14,2), `threshold_percent` decimal(5,2), `current_spent` decimal(14,2) | 月度预算 |

### 4.2 表间关系

```
account 1 ──< entry >── 1 transaction 1 ──< entry >── 1 category
                                              │
                                          budget (多对一 category)
```

- **transaction ↔ entry**：一对多，一笔交易至少两条分录（一借一贷），共享 `tx_no`
- **entry → account**：多对一，每条分录归属一个账户
- **entry → category**：多对一，每条分录指定一个分类
- **budget → category**：多对一，每个预算绑定一个支出分类

### 4.3 核心约束

- **借贷平衡**：同一 `tx_no` 下所有 `entry.amount` 按方向求和 = 0
- **流水号唯一**：`tx_no` 由 UUID 生成，全局唯一

---

## 五、核心功能模块

### 5.1 账户与复式记账模块（核心创新点）

| 接口 | 方法 | 路径 | 技术点 |
|------|------|------|--------|
| 创建账户 | POST | `/api/accounts` | `@Valid` + DTO 校验（`@NotBlank`, `@Positive`） |
| 查询所有账户 | GET | `/api/accounts` | 简单查询返回列表 |
| 新增交易（复式记账） | POST | `/api/transactions` | `@Transactional` 事务、构造双分录、借贷平衡断言、直接更新余额 |
| 查询交易列表（分页） | GET | `/api/transactions` | `Pageable` 分页查询 |
| 查询单笔交易详情 | GET | `/api/transactions/{txNo}` | 返回 DTO（含完整分录列表） |

**知识点映射**：`@Transactional`、`@Valid`/`@Validated`、`JpaRepository`、方法命名查询、DTO 分层

### 5.2 分类标签管理模块

| 接口 | 方法 | 路径 | 技术点 |
|------|------|------|--------|
| 创建分类 | POST | `/api/categories` | `@Valid` + `@Enumerated` 存储方向枚举 |
| 查询分类列表 | GET | `/api/categories` | 简单查询返回列表 |

**知识点映射**：`@Enumerated`、方法命名查询、`@NotBlank` 校验

### 5.3 预算设定与执行预警模块

| 接口 | 方法 | 路径 | 技术点 |
|------|------|------|--------|
| 设定月度预算 | POST | `/api/budgets` | `@Valid` 校验金额 > 0，方法命名查询检查是否重复设定 |
| 查询预算执行情况 | GET | `/api/budgets/{id}/execution` | `@Query` JPQL 聚合当月支出总额，返回执行进度 |
| 查询预算列表 | GET | `/api/budgets` | `Pageable` 分页查询 |
| 删除预算 | DELETE | `/api/budgets/{id}` | 简单删除 |

**业务闭环**：每次新增支出交易 → 自动累加 `budget.current_spent` → 若 ≥ `budget_amount × threshold_percent` → 在响应体中附加 `warning` 字段 → 前端弹窗提醒 → 用户调整消费或调高预算

**知识点映射**：`@Query` 聚合（SUM）、自定义异常 `BusinessException`、`@RestControllerAdvice` + `@ExceptionHandler`、DTO 分层

### 5.4 统计报表模块

| 接口 | 方法 | 路径 | 技术点 |
|------|------|------|--------|
| 月度收支趋势 | GET | `/api/reports/monthly-trend` | `@Query` GROUP BY `year_month` + `direction` |
| 分类支出占比 | GET | `/api/reports/category-pie` | `@Query` GROUP BY `category_id` JOIN `category` |
| 账户余额汇总 | GET | `/api/reports/account-balances` | `findAll` + 组装结果，含总资产/总负债计算 |

**知识点映射**：`@Query` GROUP BY 聚合、DTO 分层、`@Schema` 文档注解、JOIN 查询

---

## 六、技术实现亮点

### 6.1 复式记账的事务原子性与借贷平衡校验

在 `TransactionServiceImpl.createTransaction()` 上标注 `@Transactional(rollbackFor = Exception.class)`，保证以下步骤全或无：

1. UUID 生成全局唯一 `txNo`
2. 持久化 `Transaction` 主记录
3. 构建 ≥2 条 `Entry`（一条 DEBIT + 一条 CREDIT），关联同一 `txNo`
4. 分别更新涉及账户的 `balance`（借记加、贷记减）
5. 借贷平衡断言：`debitSum.equals(creditSum)`，不成立则抛 `BusinessException` 触发回滚

```java
@Transactional(rollbackFor = Exception.class)
public TransactionDTO create(TxCreateDTO dto) {
    String txNo = UUID.randomUUID().toString().replace("-", "");
    Transaction tx = transactionRepo.save(new Transaction(txNo, dto.getDate(), dto.getRemark()));

    List<Entry> entries = dto.getEntries().stream()
        .map(e -> new Entry(tx, e.getAccountId(), e.getCategoryId(), e.getAmount(), e.getDirection()))
        .collect(toList());

    BigDecimal debitSum  = sumByDirection(entries, DEBIT);
    BigDecimal creditSum = sumByDirection(entries, CREDIT);
    if (debitSum.compareTo(creditSum) != 0) {
        throw new BusinessException("借贷不平衡：借方 " + debitSum + "，贷方 " + creditSum);
    }

    entryRepo.saveAll(entries);
    updateAccountBalances(entries); // 直接更新余额
    return toDTO(tx, entries);
}
```

### 6.2 业务闭环设计：预算目标 → 执行 → 预警

**完整闭环流程**：

```
┌──────────────┐     ┌──────────────┐     ┌──────────────────┐     ┌──────────────┐     ┌──────────────┐
│  ① 设定目标   │ ──→ │  ② 执行交易   │ ──→ │  ③ 实时累计判断   │ ──→ │  ④ 阈值预警   │ ──→ │  ⑤ 调整行为   │
│ POST /budgets│     │ POST /txs    │     │ budget.current_  │     │ response 带  │     │ 减少消费/    │
│ 设定金额+阈值 │     │ 支出分录入库  │     │ spent += amount  │     │ warning 字段 │     │ 调高预算     │
└──────────────┘     └──────────────┘     └──────────────────┘     └──────────────┘     └──────┬───────┘
                                                                                            │
                                                                                            ↓
                                                                                  回到 ② 继续监控
```

- **设定**：`POST /api/budgets` 为支出分类设定月度额度 + 预警阈值（默认 80%）
- **执行**：每笔支出交易在 `@Transactional` 内同步 `UPDATE budget SET current_spent = current_spent + ? WHERE id = ?`
- **判断**：若 `current_spent ≥ budget_amount × threshold_percent`，在响应体附加 `warning: true`
- **反馈**：Controller 返回 HTTP 200 + warning 标识，不抛异常、不回滚
- **调整**：用户根据预警信息自主决策，形成完整闭环

### 6.3 全局异常处理体系

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResult> handleBusiness(BusinessException e) {
        return badRequest(ApiResult.error(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult> handleValid(MethodArgumentNotValidException e) {
        // 提取 @Valid 校验失败字段信息
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return badRequest(ApiResult.error(msg));
    }
}
```

### 6.4 接口文档自动化（springdoc-openapi）

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI plbasOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("PLBAS API").version("1.0").description("轻量复式记账与预算执行分析系统"));
    }
}
```

每个 Controller / 方法 / DTO 字段均标注：
- 类级别：`@Tag(name = "复式记账", description = "借贷平衡的交易管理")`
- 方法级别：`@Operation(summary = "新增交易", description = "一笔交易必须包含至少一借一贷两条分录")`
- 字段级别：`@Schema(description = "交易金额，必须大于0", example = "128.50")`

### 6.5 分页与聚合查询

```java
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 方法命名查询
    Optional<Transaction> findByTxNo(String txNo);

    // @Query JPQL
    @Query("SELECT t FROM Transaction t WHERE t.date BETWEEN :start AND :end ORDER BY t.date DESC")
    Page<Transaction> findByDateRange(@Param("start") LocalDate s, @Param("end") LocalDate e, Pageable p);
}
```

### 6.6 DTO 分层

| 类型 | 用途 | 典型注解 |
|------|------|----------|
| DTO（入参） | 接收前端请求体/参数 | `@NotBlank`, `@Positive`, `@Valid` 嵌套校验 |
| Entity | JPA 持久化映射 | `@Enumerated`, `@ManyToOne` |
| DTO（出参） | 返回前端展示 | 不含懒加载关联，扁平化结构 |

---

## 七、个人职责与收获

### 职责

- 独立完成需求分析、数据库设计与技术选型，从零搭建 Spring Boot 项目骨架
- 负责全部后端代码开发（4 个模块、13 个接口），编写 OpenAPI 注解与单元测试
- 设计复式记账核心事务编排逻辑与预算预警闭环机制

### 收获

- **事务理解的深化**：复式记账强制借贷平衡，让我真正掌握 `@Transactional` 的回滚边界，而非停留在"加个注解就行"的层面
- **业务思维的提升**：预算预警闭环让我学会将业务规则（阈值判断、累计统计）转化为技术约束，体会到"设定→执行→反馈→调整"的产品闭环设计思路
- **JPA 常用特性的熟练运用**：`@Query` 聚合、方法命名查询、`@Enumerated` 枚举映射等，在常规查询场景下充分利用 JPA 的面向对象优势

---

## 八、难点与解决方案

### 难点一：复式记账的余额更新一致性

**问题**：多笔交易涉及同一账户时，需要保证余额更新不会出错

**方案**：
- 所有余额更新放在 `@Transactional` 内，依赖数据库事务保证原子性
- 按账户 ID 顺序更新，减少并发场景下的锁竞争

### 难点二：预算预警的实时性与准确性

**问题**：每次新增支出都要累计预算消耗，需保证累计值与交易提交的一致性

**方案**：
- 在 `Budget` 表增加 `current_spent` 冗余字段，交易提交时在同一个 `@Transactional` 内更新
- 利用数据库行锁保证原子性，无需额外中间件

---

*— 文档结束 —*
