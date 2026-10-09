#!/usr/bin/env bash
# 開発用エントリポイント
# 1. 初回コンパイル
# 2. ソース変更を監視して継続コンパイル (gradle -t)
# 3. bootRun で起動し、DevTools がクラス変更を検知して自動再起動
set -euo pipefail

./gradlew classes

./gradlew --continuous classes -q &
CONTINUOUS_PID=$!
trap 'kill "$CONTINUOUS_PID" 2>/dev/null || true' EXIT

./gradlew bootRun
