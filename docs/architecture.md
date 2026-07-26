# Architecture

系统保持单用户、本地部署。`dictionary` 负责 JMdict 识别，`knowledge` 负责可教学的
规则目录，`conjugation` 是确定性正确答案来源，`smart` 负责 JLPT 分级、选题、会话和
掌握度，`ai` 只提供可降级的解释。

规则驱动主链路：

```text
Knowledge Point
  → Rule Detail
  → Training Session (knowledge_point_id)
  → Questions (同一 verb_class + conjugation_type)
  → Deterministic Conjugation
  → Knowledge Point Mastery + SM-2
```

`knowledge_point` 保存名称、等级、判断条件、变化公式、解释、示例和常见错误。
Session 与 Question 均保存外键，错误题额外保存 `mistake_type`，所以掌握度与错误分析
不再只能落到某个动词。旧的字符串知识标签暂时保留，用于兼容既有统计数据。

训练创建一次批量读取最多 500 个当前等级动词，再批量读取学习记录、知识点掌握状态
和最近题目，在内存展开、评分和去重。会话及题目在同一事务保存。答题使用
`sessionId + questionId` 校验归属并拒绝重复提交。

正确答案不进入发题 DTO。提交后服务器用持久化的本地引擎答案判分，同时更新
`knowledge_point_mastery` 与原有 `study_item` 复习排程。
