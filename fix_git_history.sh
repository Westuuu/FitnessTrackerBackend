#!/bin/bash
REPO_PATH=$1
START_COMMIT=$2

cd "$REPO_PATH"
# Create a temporary branch to store the original state
git checkout -b temp-fix-orig
CURRENT_HEAD=$(git rev-parse HEAD)

# Start from the base commit
git checkout -b convention-fixed "$START_COMMIT"

# Get the list of commits to rewrite (from $START_COMMIT to $CURRENT_HEAD)
COMMITS=($(git rev-list --reverse "$START_COMMIT..$CURRENT_HEAD"))

for commit in "${COMMITS[@]}"; do
    # Extract historical metadata
    AN=$(git show -s --format=%an "$commit")
    AE=$(git show -s --format=%ae "$commit")
    AD=$(git show -s --format=%ai "$commit")
    CN=$(git show -s --format=%cn "$commit")
    CE=$(git show -s --format=%ce "$commit")
    CD=$(git show -s --format=%ci "$commit")
    MSG=$(git log -1 --format=%B "$commit")
    TREE=$(git rev-parse "$commit^{tree}")
    PARENT=$(git rev-parse HEAD)

    # Create new commit with exact historical info
    NEW_SHA=$(GIT_AUTHOR_NAME="$AN" GIT_AUTHOR_EMAIL="$AE" GIT_AUTHOR_DATE="$AD" \
              GIT_COMMITTER_NAME="$CN" GIT_COMMITTER_EMAIL="$CE" GIT_COMMITTER_DATE="$CD" \
              git commit-tree "$TREE" -p "$PARENT" -m "$MSG")
    
    git reset --hard "$NEW_SHA"
done

# Cleanup temporary branch
git branch -D temp-fix-orig
