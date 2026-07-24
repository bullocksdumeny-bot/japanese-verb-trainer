# JLPT 动词等级数据

JLPT 官方不公开固定、完整的词汇等级表。本项目的等级用于个人学习，不代表官方完整词表。

JMdict 仍是唯一词典，负责动词原形、读音、类别和释义。`verb_entry` 上的 `jlpt_level` 与
`common_rank` 仅补充分级训练信息：

- `jlpt_level`：`N5`、`N4`、`N3` 或 `N2`；
- `common_rank`：等级内常用度，数字越小越优先。

基础数据维护在
`backend/src/main/resources/jlpt-verb-levels.json`。应用每次启动时按“原形 + 读音”精确匹配
JMdict 词条并同步两个字段。找不到或匹配不唯一的条目会记录警告并跳过。

分级训练只查询用户当前选择的精确等级，不累计低等级词汇；未分类词条不会进入分级训练。
扩充词表时直接编辑 JSON，重启应用即可同步。
