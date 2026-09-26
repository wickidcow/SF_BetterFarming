package me.hal989.betterfarming;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public final class Utils {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    private Utils() {}

    public static void send(Player player, String message) {
        player.sendMessage(Component.text("[BetterFarming] ", NamedTextColor.LIGHT_PURPLE).append(LEGACY.deserialize(message)));
    }

    public static int itemsInInventory(Inventory inventory, Material... search) {
        List<Material> wanted = Arrays.asList(search);
        int found = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null && wanted.contains(item.getType())) {
                found += item.getAmount();
            }
        }
        return found;
    }
}
