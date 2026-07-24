#!/usr/bin/env bash
set -Eeuo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"
[[ "${RESTORE_CONFIRM:-}" == "YES" ]] || { echo "Set RESTORE_CONFIRM=YES to restore." >&2; exit 1; }
file="${1:?Usage: RESTORE_CONFIRM=YES scripts/restore.sh backups/file.sql.gz}"
resolved="$(realpath "$file")"
[[ -f "$resolved" && "$resolved" == "$ROOT_DIR/backups/"* ]] || { echo "Backup must be inside $ROOT_DIR/backups." >&2; exit 1; }
set -a
source ./.env
set +a
gunzip -c "$resolved" | docker compose -f compose.prod.yaml --env-file .env exec -T db \
  psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" "$POSTGRES_DB"
echo "Restore completed from $resolved"
