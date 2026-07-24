# JLPT level data

JLPT 官方不公开固定、完整的词汇等级表。本项目的等级是个人学习用途的近似分类，
不是官方认证。仓库只附带用于开发与测试的小型种子数据，不包含商业词表。

等级与 JMdict 分开保存在 `jlpt_vocabulary_levels`。没有记录的词是
`UNCLASSIFIED`，不会自动推断，也不会进入默认智能训练。

导入支持 UTF-8 CSV/TSV，必需列：

```text
dictionary_form,reading,jlpt_level
```

可选列为 `source` 和 `confidence`。运行：

```bash
java -jar app.jar --import-jlpt-levels=/data/jlpt-verbs.csv
```

导入同时匹配原形与读音；唯一匹配才写入。多候选和无匹配会进入报告，不会静默选择。
相同词条与等级重复导入保持幂等。数据发布者需自行确认来源许可证。
