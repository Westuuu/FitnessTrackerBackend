#!/bin/bash
git checkout -b convention-perfect 27a6f20

process_commit() {
    local orig_sha=$1
    local new_msg=$2
    
    # Get original dates
    AD=$(git show -s --format=%ai "$orig_sha")
    AN=$(git show -s --format=%an "$orig_sha")
    AE=$(git show -s --format=%ae "$orig_sha")
    
    # Cherry-pick without committing
    git cherry-pick -n "$orig_sha"
    
    # Commit with original metadata and new message
    GIT_AUTHOR_NAME="$AN" GIT_AUTHOR_EMAIL="$AE" GIT_AUTHOR_DATE="$AD" \
    GIT_COMMITTER_NAME="$AN" GIT_COMMITTER_EMAIL="$AE" GIT_COMMITTER_DATE="$AD" \
    git commit -m "$new_msg"
}

process_commit "0a184f0" "feat(gym): implement gym selection and admin approval flow"
process_commit "b2fc2e9" "fix(gym): resolve mapper crashes and improve null safety"
process_commit "91ba2ed" ".env integration"
process_commit "ac89047" "goal endpoint fix"
process_commit "6bbaebd" "fix: optimize training plan loading and weight constraints"
process_commit "0496505" "fix: implement automatic goal progress tracking"
process_commit "4ab9423" "fix: refine trainee dashboard data and exceptions"
process_commit "cbf7e72" "feat: improve admin and trainer dashboards"
process_commit "8c2da0e" "feat: complete trainer dashboard"
process_commit "6a77be5" "fix: resolve trainer creation 500 and overhaul registration"
process_commit "51bdd7f" "global handler revert"
process_commit "1083ca2" "fix: enhance session and metric processing and sync global exception handling"
process_commit "557697f" "feat: refactor training plans and cleanup domains"
process_commit "20fd19b" "feat: reset database and consolidate seed data"
