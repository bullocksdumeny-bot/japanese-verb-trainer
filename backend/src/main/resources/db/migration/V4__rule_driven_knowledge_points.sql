CREATE TABLE knowledge_point (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(200) NOT NULL,
  jlpt_level VARCHAR(2) NOT NULL,
  conjugation_type VARCHAR(50) NOT NULL,
  verb_class VARCHAR(30) NOT NULL,
  summary VARCHAR(500) NOT NULL,
  identification_rule VARCHAR(1000) NOT NULL,
  transformation_formula VARCHAR(500) NOT NULL,
  explanation VARCHAR(2000) NOT NULL,
  examples_json VARCHAR(4000) NOT NULL,
  common_mistakes_json VARCHAR(4000) NOT NULL,
  display_order INTEGER NOT NULL DEFAULT 0,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_knowledge_point_level_form
  ON knowledge_point(jlpt_level, conjugation_type, display_order);

ALTER TABLE training_session
  ADD COLUMN knowledge_point_id BIGINT REFERENCES knowledge_point(id);

ALTER TABLE training_question
  ADD COLUMN knowledge_point_id BIGINT REFERENCES knowledge_point(id),
  ADD COLUMN mistake_type VARCHAR(80);

ALTER TABLE knowledge_point_mastery
  ADD COLUMN knowledge_point_id BIGINT REFERENCES knowledge_point(id),
  ADD COLUMN mistake_pattern VARCHAR(80);

CREATE UNIQUE INDEX uq_mastery_knowledge_point_id
  ON knowledge_point_mastery(knowledge_point_id)
  WHERE knowledge_point_id IS NOT NULL;

