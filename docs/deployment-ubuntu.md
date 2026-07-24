# Ubuntu production deployment

## Architecture and requirements

Only the frontend Nginx port is published. Nginx serves the Vue production build and proxies
`/api/` to an internal Spring Boot container; PostgreSQL is reachable only on the Compose
network. Recommended minimum: Ubuntu, 2 CPU, 2 GB RAM, 20 GB free disk and 2 GB swap.

## First deployment

Install Docker Engine with the Compose plugin, then place the project in
`/opt/japanese-verb-trainer`. Create the production environment:

```bash
cd /opt/japanese-verb-trainer
cp .env.example .env
sed -i "s|replace-with-a-strong-random-password|$(openssl rand -base64 32 | tr -d '/+=' | head -c 32)|" .env
chmod 600 .env
chmod +x scripts/*.sh
./scripts/deploy.sh
./scripts/backup.sh
```

If port 80 is already occupied, set `HTTP_PORT=8088` in `.env` and redeploy. Do not stop an
unknown web service merely to free the port.

## Updating

After uploading or pulling the new source:

```bash
cd /opt/japanese-verb-trainer
./scripts/backup.sh
./scripts/deploy.sh
```

No deployment script deletes the named database volume.

## Operations

```bash
docker compose -f compose.prod.yaml --env-file .env ps
docker compose -f compose.prod.yaml --env-file .env logs -f --tail=100 backend
docker compose -f compose.prod.yaml --env-file .env restart
docker compose -f compose.prod.yaml --env-file .env stop
docker volume inspect japanese-verb-trainer-data
```

Backups are written to `/opt/japanese-verb-trainer/backups`, mode `600`, and the newest seven
are retained:

```bash
./scripts/backup.sh
RESTORE_CONFIRM=YES ./scripts/restore.sh backups/verbtrainer-YYYYMMDDTHHMMSSZ.sql.gz
```

Restore replaces database objects contained in the selected dump. Take a fresh backup first.

## AI configuration

Edit only the server-side `.env` and set `AI_ENABLED`, `AI_BASE_URL`, `AI_API_KEY` and
`AI_MODEL`, then run:

```bash
docker compose -f compose.prod.yaml --env-file .env up -d --force-recreate backend
```

## Firewall and Tencent Cloud

Keep SSH port 22 open. Open TCP 80 for HTTP. Open TCP 443 only after adding a domain and TLS
termination. Do not open 5432 or 8080. If `HTTP_PORT=8088` is used temporarily, open TCP 8088
instead of 80. Apply the same inbound rules in the Tencent Cloud security group.

For UFW, first inspect existing rules. A minimal additive configuration is:

```bash
sudo ufw allow OpenSSH
sudo ufw allow 80/tcp
sudo ufw status
```

Do not reset an existing firewall.

## Low-memory server

The production Compose limits Java heap to 512 MB, backend container memory to 640 MB,
PostgreSQL to 384 MB and Nginx to 96 MB. PostgreSQL uses 128 MB shared buffers, 2 MB work
memory and 30 connections. If `swapon --show` is empty, a 2 GB swapfile may be created after
confirming `/swapfile` does not already exist.

## Troubleshooting

- Port conflict: `sudo ss -lntup | grep -E ':80|:8088'`.
- Unhealthy service: inspect `docker compose ... ps` and the last 100 log lines.
- Migration failure: inspect backend logs for Flyway; never edit an applied migration.
- Database persistence: confirm `docker volume inspect japanese-verb-trainer-data`.
- Memory pressure: `free -h` and `docker stats --no-stream`.
- Blank frontend/API errors: verify `/health`, `/api/health`, and Nginx proxy logs.
