# Architecture

系统保持单用户、本地部署。`dictionary` 负责 JMdict 识别，`conjugation` 是确定性
正确答案来源，`smart` 负责 JLPT 分级、选题、会话和掌握度，`ai` 只提供可降级的解释。

训练创建一次批量读取最多 500 个当前等级动词，再批量读取学习记录、知识点掌握状态
和最近题目，在内存展开、评分和去重。会话及题目在同一事务保存。答题使用
`sessionId + questionId` 校验归属并拒绝重复提交。

正确答案不进入发题 DTO。提交后服务器用持久化的本地引擎答案判分，同时更新
`knowledge_point_mastery` 与原有 `study_item` 复习排程。
