#!/usr/bin/env python3
"""Regression checks for BetterFarming's immutable, tag-only release contract.

No network, release mutation, or GitHub credentials are needed.
"""
from pathlib import Path
import re
import unittest

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github/workflows/maven.yml"
STEP = "      - name: Publish new immutable tag only\n"


def verify_policy(workflow: str) -> None:
    if workflow.count(STEP) != 1:
        raise ValueError("expected one canonical release step")
    prelude, release = workflow.split(STEP, 1)
    if "run: python3 -m unittest discover -s scripts -p 'test_immutable_release_policy.py' -v" not in prelude:
        raise ValueError("regression suite is not executed by CI")
    if "    tags: ['v*']" not in prelude:
        raise ValueError("version-tag push trigger is missing")
    if not re.search(r"(?m)^        if: startsWith\(github\.ref, 'refs/tags/v'\)$", release):
        raise ValueError("publication must be tag-only, not manual/branch")
    required = (
        'TAG="v${ARTIFACT_VERSION}"',
        'test "$GITHUB_REF" = "refs/tags/${TAG}"',
        'JAR="dist/${OUTPUT_NAME}"',
        'test -s "$JAR"',
        'if gh release view "$TAG" --repo "$GITHUB_REPOSITORY" >/dev/null 2>&1; then',
        'exit 1',
        'fi',
        'gh release create "$TAG" "$JAR" --verify-tag --repo "$GITHUB_REPOSITORY"',
    )
    for value in required:
        if value not in release:
            raise ValueError(f"missing immutable-release condition: {value}")
    if any(value in release for value in ("gh release upload", "--clobber", "gh release edit", "workflow_dispatch")):
        raise ValueError("existing release mutation or manual publishing is forbidden")
    view = release.index(required[4])
    reject = release.index("exit 1", view)
    close = release.index("fi", reject)
    create = release.index(required[7], close)
    if not view < reject < close < create:
        raise ValueError("existing release must be rejected before creating a new one")


class ImmutableReleasePolicyTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.workflow = WORKFLOW.read_text(encoding="utf-8")

    def test_current_tag_only_policy_passes(self):
        verify_policy(self.workflow)

    def assert_mutation_rejected(self, before: str, after: str):
        self.assertEqual(1, self.workflow.count(before), before)
        with self.assertRaises(ValueError):
            verify_policy(self.workflow.replace(before, after, 1))

    def test_manual_dispatch_cannot_publish(self):
        self.assert_mutation_rejected(
            "if: startsWith(github.ref, 'refs/tags/v')",
            "if: github.event_name == 'workflow_dispatch' || startsWith(github.ref, 'refs/tags/v')",
        )

    def test_mismatched_tag_is_rejected(self):
        self.assert_mutation_rejected(
            'test "$GITHUB_REF" = "refs/tags/${TAG}"',
            'test "$GITHUB_REF" != "refs/tags/${TAG}"',
        )

    def test_existing_release_check_cannot_be_inverted(self):
        self.assert_mutation_rejected(
            'if gh release view "$TAG" --repo "$GITHUB_REPOSITORY" >/dev/null 2>&1; then',
            'if ! gh release view "$TAG" --repo "$GITHUB_REPOSITORY" >/dev/null 2>&1; then',
        )

    def test_existing_release_must_fail_closed(self):
        self.assert_mutation_rejected("            exit 1\n", "            true\n")

    def test_existing_release_assets_cannot_be_uploaded(self):
        self.assert_mutation_rejected(
            'gh release create "$TAG" "$JAR" --verify-tag --repo "$GITHUB_REPOSITORY"',
            'gh release upload "$TAG" "$JAR" --clobber --repo "$GITHUB_REPOSITORY"',
        )

    def test_new_release_must_verify_existing_tag(self):
        self.assert_mutation_rejected(
            'gh release create "$TAG" "$JAR" --verify-tag --repo "$GITHUB_REPOSITORY"',
            'gh release create "$TAG" "$JAR" --repo "$GITHUB_REPOSITORY"',
        )

    def test_artifact_must_exist(self):
        self.assert_mutation_rejected('test -s "$JAR"', 'test -n "$JAR"')

    def test_policy_tests_must_run_in_ci(self):
        self.assert_mutation_rejected(
            "run: python3 -m unittest discover -s scripts -p 'test_immutable_release_policy.py' -v",
            "run: echo policy checks skipped",
        )


if __name__ == "__main__":
    unittest.main()
