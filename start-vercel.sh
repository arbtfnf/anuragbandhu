#!/bin/sh
set -e
PORT="${PORT:-80}"
echo "anuragbandhu nginx on ${PORT}, Spring Boot on 8080"

sed "s/LISTEN_PORT/${PORT}/g" /etc/nginx/nginx.vercel.conf > /tmp/nginx.conf

JAVA="/opt/java/openjdk/bin/java"
"$JAVA" \
  -Xms64m \
  -Xmx384m \
  -Djava.security.egd=file:/dev/./urandom \
  -Dspring.jmx.enabled=false \
  -jar /app/app.jar \
  --server.port=8080 \
  --server.address=127.0.0.1 \
  >>/proc/1/fd/1 2>&1 &
JAVA_PID=$!

nginx -c /tmp/nginx.conf &
NGINX_PID=$!

term() {
  kill -TERM "$JAVA_PID" "$NGINX_PID" 2>/dev/null || true
  wait "$JAVA_PID" "$NGINX_PID" 2>/dev/null || true
}
trap term TERM INT

# Keep both processes. `exec nginx` would SIGHUP the JVM.
wait "$JAVA_PID" "$NGINX_PID"
