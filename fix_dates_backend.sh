#!/bin/bash
git checkout -b fix-dates-backend dev
COMMITS=($(git rev-list --reverse 27a6f20..HEAD))

for commit in "${COMMITS[@]}"; do
    AUTHOR_DATE=$(git show -s --format=%ai $commit)
    GIT_COMMITTER_DATE="$AUTHOR_DATE" git commit-tree $(git rev-parse $commit^{tree}) -p $(git rev-parse HEAD) -m "$(git log -1 --format=%B $commit)" > new_commit
    NEW_SHA=$(cat new_commit)
    git reset --hard $NEW_SHA
done
rm new_commit
