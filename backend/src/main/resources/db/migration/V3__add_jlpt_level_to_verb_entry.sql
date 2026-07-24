ALTER TABLE verb_entry
  ADD COLUMN jlpt_level VARCHAR(2),
  ADD COLUMN common_rank INTEGER;

ALTER TABLE verb_entry
  ADD CONSTRAINT chk_verb_jlpt_level
    CHECK (jlpt_level IS NULL OR jlpt_level IN ('N5', 'N4', 'N3', 'N2')),
  ADD CONSTRAINT chk_verb_common_rank
    CHECK (common_rank IS NULL OR common_rank > 0);

CREATE INDEX idx_verb_jlpt_common_rank
  ON verb_entry(jlpt_level, common_rank);
