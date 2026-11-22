#!/usr/bin/env bash
# Done-when check for ENG-3: the generate task must produce a Spring interface
# for GET /api/health, and changing the response shape in the contract must
# change the generated code.
set -euo pipefail
cd "$(dirname "$0")/.."

OUT=build/generated/openapi/src/main/java/com/ryanbendall/website/api
CONTRACT=../contract/openapi.yaml

rm -rf build/generated
./gradlew --quiet generateApi
grep -q "interface HealthApi" "$OUT/HealthApi.java"
grep -q "/api/health" "$OUT/HealthApi.java"
grep -q "StatusEnum status" "$OUT/model/Health.java"

# Change the response shape, regenerate, expect the generated model to change.
cp "$CONTRACT" "$CONTRACT.bak"
trap 'mv "$CONTRACT.bak" "$CONTRACT"' EXIT
sed -i.sed 's/status:/state:/; s/- status/- state/' "$CONTRACT" && rm -f "$CONTRACT.sed"
./gradlew --quiet generateApi
grep -q "StateEnum state" "$OUT/model/Health.java"
# Not written as `! grep`: bash exempts a negated command from `set -e`, so that form can never fail.
if grep -q "StatusEnum status" "$OUT/model/Health.java"; then
  echo "FAIL: old field 'status' is still generated" >&2
  exit 1
fi
echo "codegen check passed"
