CREATE TABLE verb_entry (
 id BIGSERIAL PRIMARY KEY, lemma VARCHAR(255) NOT NULL, reading VARCHAR(255) NOT NULL,
 meanings VARCHAR(1000) NOT NULL, verb_class VARCHAR(30) NOT NULL, jmdict_tags VARCHAR(255)
);
CREATE INDEX idx_verb_lemma ON verb_entry(lemma);
CREATE INDEX idx_verb_reading ON verb_entry(reading);
CREATE TABLE study_item (
 id BIGSERIAL PRIMARY KEY, verb_id BIGINT NOT NULL, form VARCHAR(50) NOT NULL,
 repetitions INT NOT NULL DEFAULT 0, error_count INT NOT NULL DEFAULT 0, correct_streak INT NOT NULL DEFAULT 0,
 ease_factor DOUBLE PRECISION NOT NULL DEFAULT 2.5, interval_days INT NOT NULL DEFAULT 0,
 last_reviewed TIMESTAMP, next_review TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT uq_study_verb_form UNIQUE(verb_id, form)
);
CREATE TABLE recent_query(id BIGSERIAL PRIMARY KEY,verb_id BIGINT,queried_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE favorite(verb_id BIGINT PRIMARY KEY);
INSERT INTO verb_entry(lemma,reading,meanings,verb_class,jmdict_tags) VALUES
('書く','かく','写；书写','GODAN','v5k'),('読む','よむ','读；阅读','GODAN','v5m'),('遊ぶ','あそぶ','玩；游玩','GODAN','v5b'),
('泳ぐ','およぐ','游泳','GODAN','v5g'),('話す','はなす','说；交谈','GODAN','v5s'),('帰る','かえる','回去；回家','GODAN','v5r'),
('食べる','たべる','吃','ICHIDAN','v1'),('見る','みる','看','ICHIDAN','v1'),('する','する','做','SURU','vs-i'),
('勉強する','べんきょうする','学习','SURU','vs'),('来る','くる','来','KURU','vk'),('行く','いく','去','GODAN','v5k-s'),
('ある','ある','有；存在（无生命）','GODAN','v5r-i'),('くださる','くださる','赐予（尊敬）','GODAN','v5r-i'),
('なさる','なさる','做（尊敬）','GODAN','v5r-i'),('いらっしゃる','いらっしゃる','在；去；来（尊敬）','GODAN','v5r-i'),('おっしゃる','おっしゃる','说（尊敬）','GODAN','v5r-i');
