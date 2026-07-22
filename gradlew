#!/bin/sh
export GRADLE_USER_HOME="$PWD/.gradle"
cd "$(dirname "$0")"
exec gradle-8.2/bin/gradle "$@"
