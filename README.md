# SF_BetterFarming

Maintained BetterFarming fork for modern Slimefun servers.

BetterFarming adds special hoes, Rupees, breakable pots and sword-based grass rewards. This fork restores a fully English-facing build from the recovered `d8b712f` source lineage and updates it for current Minecraft and Slimefun APIs.

## Compatibility

Primary targets are **Slimefun Legacy** and **Slimefun United**. Additional compatibility is tested against SlimefunGuguProject/Slimefun4 and original Slimefun4-compatible APIs.

Supported server families: Paper, Purpur, Folia and Leaf. Minecraft target: **1.21.11+**. Builds use Java 25 and target Java 21 bytecode.

The gameplay is event-driven and does not create its own repeating scheduler. Modern leaf types including mangrove, cherry, azalea and pale oak leaves are recognized, and current `SHORT_GRASS` naming is used.

## Dependency policy

This maintained fork has no direct CS-CoreLib dependency and avoids external Slimefun utility-library dependencies. Cross-fork support stays on the shared Slimefun addon API wherever possible.

## English-only maintenance

The inherited translation fork contained non-English category, item, lore and research text. The maintained `wickidcow` line replaces those strings with English and CI rejects new CJK-facing text in source/resources.

Original project by HAL989 and contributors. Modern compatibility maintenance is provided by `wickidcow`.
