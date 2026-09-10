<div align="center">

# SF_BetterFarming — Slimefun Legacy
### Maintained BetterFarming progression for modern Paper servers

BetterFarming adds special hoes, Rupees, breakable pots, and grass-based rewards while preserving the recovered original progression.

[![Build](https://github.com/wickidcow/SF_BetterFarming/actions/workflows/maven.yml/badge.svg)](https://github.com/wickidcow/SF_BetterFarming/actions/workflows/maven.yml)
[![Slimefun Legacy](https://img.shields.io/badge/Slimefun-Legacy-6bd425)](https://github.com/wickidcow/Slimefun-Legacy)
[![Paper](https://img.shields.io/badge/Server-Paper%2026.2-blue)](https://papermc.io/)
[![Java](https://img.shields.io/badge/Build-Java%2025-orange)](https://adoptium.net/)
[![License](https://img.shields.io/badge/Maintained%20distribution-GPLv3-blue)](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE)

[Releases](https://github.com/wickidcow/SF_BetterFarming/releases) · [Builds](https://github.com/wickidcow/SF_BetterFarming/actions) · [Issues](https://github.com/wickidcow/SF_BetterFarming/issues)

</div>

> [!IMPORTANT]
> **SF_BetterFarming is an unofficial, independently maintained downstream fork of BetterFarming.** It is maintained by `wickidcow` for [AlbionMC.com](https://albionmc.com) and the wider Slimefun community. It is not an official release of the original BetterFarming project, the original Slimefun project, Slimefun United, or the SlimefunGuguProject.
>
> **NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

---
## What is SF_BetterFarming?

BetterFarming adds special farming tools, Rupees, breakable pots, and sword-based grass rewards. This maintained line restores a fully English-facing build from the recovered `d8b712f` source lineage and updates it for current Minecraft, Paper, and Slimefun APIs.

The gameplay is event-driven and does not create its own repeating scheduler. Modern leaf types including mangrove, cherry, azalea and pale oak leaves are recognized, and current `SHORT_GRASS` naming is used.

---
## Download and build

Release JARs use the maintained Slimefun-addon naming convention:

`SF_BetterFarming1.0.0.jar`

The project builds with **Java 25** while targeting **Java 21 bytecode**. The production compatibility baseline is **Slimefun Legacy 4.1.48 on Paper 26.2**. Development builds are available from GitHub Actions and versioned release JARs are published on the Releases page.

---
## Compatibility

Primary target: **Slimefun Legacy**.

Compatibility is also validated against Slimefun United, SlimefunGuguProject/Slimefun4, and original Slimefun4-compatible APIs. Paper is the primary server family; Purpur, Folia and Leaf are compatibility targets.

This maintained fork has no direct CS-CoreLib dependency and avoids external Slimefun utility-library dependencies. Cross-fork support stays on the shared Slimefun addon API wherever practical.

---
## English-first maintenance

The inherited translation fork contained non-English category, item, lore and research text. The maintained `wickidcow` line replaces those strings with English and keeps player-facing source/resources English-first.

---
## Credits and project lineage

Original BetterFarming project authorship belongs to **Gavin296, HAL989, and the original contributors**. Modern compatibility, English maintenance, and Slimefun Legacy integration are maintained by **wickidcow**.

This fork exists to preserve and maintain that work for current servers—not to replace the original developers or claim their work as its own. Upstream authorship, copyright notices, and applicable license obligations remain respected.

---
## Independence, trademarks and non-affiliation

**NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

SF_BetterFarming and this maintenance fork are independent community projects. They are not affiliated with, endorsed by, sponsored by, approved by, or operated by Mojang Studios or Microsoft Corporation. References to Minecraft, Slimefun, Paper, upstream projects, companies, products, or communities are for identification, compatibility, attribution, and interoperability only.

Minecraft, Mojang Studios, Microsoft, and other third-party names, logos, brands, and trademarks remain the property of their respective owners. No sponsorship, partnership, ownership, or endorsement is claimed or implied.

---
## License

The maintained `wickidcow` distribution and its modifications are distributed under the [GNU General Public License v3.0](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE).

Original BetterFarming source was made available under the MIT License. The original Gavin296 copyright and MIT permission notice remain preserved in this repository and continue to apply to upstream-derived material; the maintained GPLv3 distribution does not erase or claim ownership of that upstream authorship.

Copyright in later modifications remains with the contributors who authored those changes.
