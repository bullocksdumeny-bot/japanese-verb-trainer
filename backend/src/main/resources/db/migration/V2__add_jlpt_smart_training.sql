CREATE TABLE jlpt_vocabulary_levels(
 id BIGSERIAL PRIMARY KEY,dictionary_entry_id BIGINT NOT NULL REFERENCES verb_entry(id) ON DELETE CASCADE,
 jlpt_level VARCHAR(8) NOT NULL,source VARCHAR(30) NOT NULL,confidence VARCHAR(12) NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT uq_jlpt_entry_level UNIQUE(dictionary_entry_id,jlpt_level)
);
CREATE INDEX idx_jlpt_level ON jlpt_vocabulary_levels(jlpt_level);
CREATE INDEX idx_jlpt_entry ON jlpt_vocabulary_levels(dictionary_entry_id);
CREATE TABLE knowledge_point_mastery(
 id BIGSERIAL PRIMARY KEY,knowledge_point_type VARCHAR(40) NOT NULL,knowledge_point_key VARCHAR(255) NOT NULL,
 attempt_count INT NOT NULL DEFAULT 0,correct_count INT NOT NULL DEFAULT 0,wrong_count INT NOT NULL DEFAULT 0,correct_streak INT NOT NULL DEFAULT 0,
 last_attempt_at TIMESTAMP WITH TIME ZONE,last_wrong_at TIMESTAMP WITH TIME ZONE,mastery_score DOUBLE PRECISION NOT NULL DEFAULT 0,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT uq_knowledge_point UNIQUE(knowledge_point_type,knowledge_point_key)
);
CREATE INDEX idx_mastery_score ON knowledge_point_mastery(mastery_score);
CREATE TABLE training_session(
 id BIGSERIAL PRIMARY KEY,jlpt_level VARCHAR(8) NOT NULL,mode VARCHAR(30) NOT NULL,requested_count INT NOT NULL,
 actual_count INT NOT NULL DEFAULT 0,shortage_reason VARCHAR(500),created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE training_question(
 id BIGSERIAL PRIMARY KEY,session_id BIGINT NOT NULL REFERENCES training_session(id) ON DELETE CASCADE,verb_id BIGINT NOT NULL REFERENCES verb_entry(id),
 question_type VARCHAR(50) NOT NULL,conjugation_type VARCHAR(50) NOT NULL,dictionary_form VARCHAR(255) NOT NULL,reading VARCHAR(255),
 prompt VARCHAR(1000) NOT NULL,correct_answer VARCHAR(255) NOT NULL,options_json VARCHAR(1000),knowledge_point_keys VARCHAR(1000) NOT NULL,
 selection_reasons VARCHAR(1000) NOT NULL,priority_score DOUBLE PRECISION NOT NULL,submitted_answer VARCHAR(255),correct BOOLEAN,answered_at TIMESTAMP WITH TIME ZONE,
 CONSTRAINT uq_session_question UNIQUE(session_id,verb_id,conjugation_type,question_type)
);
CREATE INDEX idx_question_session ON training_question(session_id);
INSERT INTO jlpt_vocabulary_levels(dictionary_entry_id,jlpt_level,source,confidence)
SELECT id,CASE
 WHEN lemma IN ('行く','来る','する','食べる','見る') THEN 'N5'
 WHEN lemma IN ('読む','書く','話す','帰る','遊ぶ') THEN 'N4'
 WHEN lemma IN ('勉強する','泳ぐ','ある') THEN 'N3'
 ELSE 'N2' END,'SEED_DATA','MEDIUM' FROM verb_entry;
