#!/usr/bin/env bash
set -Eeuo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"
set -a
source ./.env
set +a
BACKUP_DIR="$ROOT_DIR/backups"
mkdir -p "$BACKUP_DIR"
chmod 700 "$BACKUP_DIR"
file="$BACKUP_DIR/verbtrainer-$(date -u +%Y%m%dT%H%M%SZ).sql.gz"
docker compose -f compose.prod.yaml --env-file .env exec -T db \
  pg_dump --clean --if-exists --no-owner -U "$POSTGRES_USER" "$POSTGRES_DB" | gzip -9 >"$file"
chmod 600 "$file"
mapfile -t old < <(find "$BACKUP_DIR" -maxdepth 1 -type f -name 'verbtrainer-*.sql.gz' -printf '%T@ %p\n' | sort -rn | tail -n +8 | cut -d' ' -f2-)
for target in "${old[@]}"; do [[ -n "$target" && "$target" == "$BACKUP_DIR/"* ]] && rm -- "$target"; done
echo "$file"
