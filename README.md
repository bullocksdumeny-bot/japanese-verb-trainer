# Japanese Verb Trainer

面向中文母语 JLPT N5–N3 学习者的本地日语动词活用训练器。正确答案仅由
本地 JMdict 分类与 Java 规则引擎产生；AI 只解释、举例和分析错误。

## 一键启动

1. 复制 `.env.example` 为 `.env`，按需填写 AI 配置。
2. 在项目根目录运行：

```bash
docker compose up --build
```

浏览器访问 <http://localhost:8088>。PostgreSQL 数据保存在 Docker volume 中。

## 功能

- 本地查询、JMdict 标签分类与全部 17 种活用
- 策略模式规则引擎：五段、一段、する、来る；YAML 例外优先
- 每日训练、即时判定、变化步骤、学习统计
- SM-2 思想的间隔复习（1、4 天，之后按易度系数扩展）
- OpenAI Compatible AI 中文解释；Key 仅保存在后端
- N5～N2 分级智能训练：到期复习、历史错误、薄弱知识点、新词与最近重复综合评分
- 规则驱动学习：等级 → 活用主题 → 具体知识点 → 规则详情 → 20 题专项训练
- N5 首批知识点覆盖五段、一段、する、来る的ます形与て形；后续等级沿用同一模型扩充
- 六种训练模式、六种题型、17 种活用范围、持久化训练会话与训练总结
- JMdict XML 流式导入：`POST /api/verbs/import`，multipart 字段名 `file`

首次启动包含一小组覆盖 N5–N3 常见规则的种子词，便于立即训练。完整词典可从
EDRDG 获取后通过导入接口写入 PostgreSQL。许可见 [LICENSE-JMDICT.md](LICENSE-JMDICT.md)。

## 开发与测试

前端：`cd frontend && npm install && npm run dev`。

后端要求 Java 21：`cd backend && mvn test && mvn spring-boot:run`。

若本机没有 Java 21，可直接使用 Docker 构建测试环境：

```bash
docker run --rm -v "${PWD}/backend:/app" -w /app maven:3.9-eclipse-temurin-21 mvn test
```

API：

- `GET /api/verbs/search?q=帰る`
- `GET /api/verbs/{id}`
- `GET /api/training/daily`
- `POST /api/training/submit`
- `GET /api/training/stats`
- `POST /api/ai/explain`
- `POST /api/v1/training/sessions`
- `POST /api/v1/training/sessions/{sessionId}/questions/{questionId}/answer`
- `GET /api/v1/training/sessions/{sessionId}/summary`
- `GET /api/v1/training/sessions/policy/{level}`
- `GET /api/v1/knowledge-points?level=N5`
- `GET /api/v1/knowledge-points/{code}`

创建专项训练时，在原有 Session 请求中传入 `knowledgePointCode`。后端会强制使用该
知识点的 JLPT 等级、动词类别和活用类型筛选题目；例如 `N5_TE_ICHIDAN` 的题目不会
出现五段、する或来る动词。未传该字段时保持原有智能训练行为。

JLPT 数据与智能出题细节见 [JLPT 数据说明](docs/jlpt-level-data.md)、
[智能选题说明](docs/smart-question-selection.md) 和 [架构说明](docs/architecture.md)。

Ubuntu 个人服务器的生产部署、备份、恢复和更新步骤见
[Ubuntu 部署说明](docs/deployment-ubuntu.md)。

## 架构约束

`knowledge` 决定“正在学习哪条规则”，`conjugation` 是确定性领域核心并决定答案，
不依赖 AI 或数据库。词典只负责识别类别；
AI 请求必须携带规则引擎已经算出的 `correct`，且 AI 返回值从不进入判分流程。
