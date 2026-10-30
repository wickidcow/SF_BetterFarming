#!/usr/bin/env python3
"""Keep the 26.3 compatibility lane on stable API releases, not pre-release alphas."""
from pathlib import Path
import unittest

ROOT = Path(__file__).resolve().parents[1]
WORKFLOW = ROOT / ".github/workflows/maven.yml"
LANE = "      - name: Verify stable Paper 26.3 API source compatibility\n"
NEXT = "      - name: Build universal release JAR from 1.21.11 baseline\n"


def verify(workflow: str) -> None:
    if workflow.count(LANE) != 1 or workflow.count(NEXT) != 1:
        raise ValueError("expected a single stable 26.3 lane and universal baseline")
    prefix, remainder = workflow.split(LANE, 1)
    lane, suffix = remainder.split(NEXT, 1)
    if "test_stable_paper_compile_policy.py' -v" not in prefix:
        raise ValueError("policy regression must run on every build")
    if "Verify Paper 1.21.11 baseline" not in prefix or "Verify Paper 26.2 primary compatibility" not in prefix:
        raise ValueError("minimum and supported compatibility lanes must remain")
    if '-Dpaper.version="[26.3.build,26.4.build)"' not in lane:
        raise ValueError("stable 26.3 API range must be explicit and bounded")
    if any(value in lane for value in ("alpha", "-rc-", "SNAPSHOT", "[26.3.build,)", "26.4.build,26.5.build")):
        raise ValueError("pre-release or unbounded 26.3 compatibility is not accepted")
    if "mvn -B -Dmaven.test.skip=true clean package" not in lane:
        raise ValueError("this is a real API compilation, not a string-only check")
    if "-Dpaper.version=1.21.11-R0.1-SNAPSHOT" not in suffix:
        raise ValueError("universal Java21 JAR must retain 1.21.11 baseline")
    if "javap -verbose -classpath" not in suffix or "major version: 65" not in suffix:
        raise ValueError("Java21 distributable guard must remain")
    if "if: startsWith(github.ref, 'refs/tags/v')" not in suffix:
        raise ValueError("immutable tag-only release guard must remain")


class StablePaperCompilePolicyTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.workflow = WORKFLOW.read_text(encoding="utf-8")

    def test_current_policy(self):
        verify(self.workflow)

    def mutation(self, before, after):
        self.assertEqual(self.workflow.count(before), 1)
        with self.assertRaises(ValueError):
            verify(self.workflow.replace(before, after, 1))

    def test_alpha_replacement_is_rejected(self):
        self.mutation('-Dpaper.version="[26.3.build,26.4.build)"',
                      '-Dpaper.version=26.3-rc-3.build.1-alpha')

    def test_unbounded_version_range_is_rejected(self):
        self.mutation('-Dpaper.version="[26.3.build,26.4.build)"',
                      '-Dpaper.version="[26.3.build,)"')

    def test_wrong_major_api_range_is_rejected(self):
        self.mutation('-Dpaper.version="[26.3.build,26.4.build)"',
                      '-Dpaper.version="[26.4.build,26.5.build)"')

    def test_universal_baseline_is_not_raised(self):
        self.mutation('-Dpaper.version=1.21.11-R0.1-SNAPSHOT',
                      '-Dpaper.version="[26.3.build,26.4.build)"')

    def test_build_cannot_be_replaced_by_noop(self):
        self.mutation("mvn -B -Dmaven.test.skip=true clean package",
                      "echo 26.3 compatibility passed")

    def test_policy_cannot_be_skipped(self):
        self.mutation("test_stable_paper_compile_policy.py' -v",
                      "test_stable_paper_compile_policy.py' --skip")

    def test_tag_only_publisher_must_remain(self):
        self.mutation("if: startsWith(github.ref, 'refs/tags/v')",
                      "if: github.event_name == 'workflow_dispatch'")


if __name__ == "__main__":
    unittest.main()
