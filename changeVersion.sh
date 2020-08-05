#!/bin/sh

[ $# -ne 2 ] && { echo "Usage: $0 currentVersion newVersion.";exit 1; }

echo changing version from $1 to $2.

# find . -type f -name pom.xml -exec gsed -i "s/$1/$2/" {} \;
find . -type f -name pom.xml -exec sed -i '' "s/$1/$2/" {} \;

echo complete.
