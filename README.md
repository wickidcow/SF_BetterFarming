<div align="center">

# SF_BetterFarming — Slimefun Legacy
### Classic BetterFarming preserved for modern Minecraft

[![Build](https://github.com/wickidcow/SF_BetterFarming/actions/workflows/maven.yml/badge.svg)](https://github.com/wickidcow/SF_BetterFarming/actions/workflows/maven.yml)
[![License](https://img.shields.io/badge/Maintained%20distribution-GPLv3-blue)](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE)

</div>

> [!IMPORTANT]
> **SF_BetterFarming is an unofficial, independently maintained downstream fork.** It is maintained by `wickidcow` for AlbionMC and the wider Slimefun community.
>
> **NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

## Preserved progression

The Apple Hoes, Rupees, Breakable Pot, Kokiri Sword, Magical Sword and Master Sword retain their established item IDs, recipes and gameplay role. The modern Copper Rupee Sword and Copper Rupee Spear remain additive items rather than replacements.

## 1.0.2 modernization

- Leaf handling now uses Minecraft's/Bukkit's `Tag.LEAVES` instead of a fixed list, so future vanilla tree families naturally participate without rewriting the classic Apple Hoe mechanic.
- Modern vegetation support remains for Dry Grass, Bush, Firefly Bush, Wildflowers, Cactus Flower and Leaf Litter.
- Apple Hoe, Golden Apple Hoe, Enchanted Golden Apple Hoe, Kokiri Sword, Copper Rupee Sword, Copper Rupee Spear and Magical Sword drop chances are configurable in `config.yml`.
- Shipped defaults are identical to 1.0.1 behavior. The Master Sword keeps its original full-health/fallback logic unchanged.

Release JAR: `SF_BetterFarming1.0.2.jar`

Built with Java 25 targeting Java 21 bytecode. Slimefun Legacy is the primary target; shared API compatibility is retained for Slimefun United, SlimefunGuguProject/Slimefun4 and original Slimefun4-compatible implementations. Paper is primary with Purpur, Folia and Leaf as compatibility targets.

No direct GuizhanLib dependency is used.

## Credits and license

Original BetterFarming authorship and its MIT notice remain preserved for upstream-derived material. The maintained `wickidcow` distribution and later modifications are distributed under the [GNU General Public License v3.0](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE).

## Independence and trademarks

**NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

This project is independent and is not affiliated with, endorsed by, sponsored by, approved by, or operated by Mojang Studios or Microsoft Corporation. Third-party names and trademarks remain the property of their respective owners.
