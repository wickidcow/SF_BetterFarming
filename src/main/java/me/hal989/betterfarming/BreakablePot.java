package me.hal989.betterfarming;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

import static me.hal989.betterfarming.BetterFarming.breakablePot;
import static me.hal989.betterfarming.BetterFarming.greenRupee;

public class BreakablePot extends SlimefunItem {

    public BreakablePot(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        addItemHandler((ItemUseHandler) this::onItemRightClick);
    }

    private void onItemRightClick(PlayerRightClickEvent event) {
        event.cancel();
        Player player = event.getPlayer();
        player.getInventory().removeItem(breakablePot.clone());

        int reward = ThreadLocalRandom.current().nextDouble() > 0.6 ? 5 : 4;
        for (int i = 0; i < reward; i++) {
            player.getInventory().addItem(greenRupee.clone());
        }
    }
}
