#!/usr/bin/env bash
set -Eeuo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"
COMPOSE=(docker compose -f compose.prod.yaml --env-file .env)

diagnostics() {
  echo "Deployment failed. Current service status:" >&2
  "${COMPOSE[@]}" ps >&2 || true
  "${COMPOSE[@]}" logs --tail=80 backend frontend db >&2 || true
}
trap diagnostics ERR

command -v docker >/dev/null || { echo "Docker is required." >&2; exit 1; }
docker compose version >/dev/null
[[ -f .env ]] || { echo "Missing $ROOT_DIR/.env" >&2; exit 1; }
permissions="$(stat -c '%a' .env)"
[[ "$permissions" == "600" ]] || { echo ".env must have mode 600 (current: $permissions)." >&2; exit 1; }
set -a
source ./.env
set +a

"${COMPOSE[@]}" config --quiet
echo "Building frontend..."
"${COMPOSE[@]}" build frontend
echo "Building backend..."
"${COMPOSE[@]}" build backend
echo "Starting services..."
"${COMPOSE[@]}" up -d

deadline=$((SECONDS + 180))
while (( SECONDS < deadline )); do
  states="$("${COMPOSE[@]}" ps --format json 2>/dev/null || true)"
  if grep -q '"Health":"healthy"' <<<"$states" && [[ "$(grep -o '"Health":"healthy"' <<<"$states" | wc -l)" -ge 3 ]]; then
    "${COMPOSE[@]}" ps
    echo "Deployment healthy: http://$(hostname -I | awk '{print $1}'):${HTTP_PORT:-80}"
    exit 0
  fi
  sleep 5
done
echo "Timed out waiting for healthy containers." >&2
exit 1
