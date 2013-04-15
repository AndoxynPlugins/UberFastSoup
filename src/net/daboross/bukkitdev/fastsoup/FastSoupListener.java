package net.daboross.bukkitdev.fastsoup;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author daboross
 */
public class FastSoupListener implements Listener {

    private static final int MAX_HUNGER = 20;
    private static final int HEALTH_ADD_AMOUNT = 6;

    @EventHandler(priority = EventPriority.LOW)
    public void onClick(PlayerInteractEvent evt) {
        if (!(evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        ItemStack itemStack = evt.getItem();
        if (itemStack == null || itemStack.getTypeId() != Material.MUSHROOM_SOUP.getId()) {
            return;
        }
        Player p = evt.getPlayer();
        if (p != null && p.hasPermission("fastsoup.use")) {
            boolean affected = false;
            {
                int food = p.getFoodLevel();
                if (food < MAX_HUNGER) {
                    p.setFoodLevel(MAX_HUNGER);
                    affected = true;
                } else {
                    int health = p.getHealth();
                    if (health < p.getMaxHealth()) {
                        affected = true;
                        if (health + HEALTH_ADD_AMOUNT >= p.getMaxHealth()) {
                            p.setHealth(p.getMaxHealth());
                        } else {
                            p.setHealth(health + HEALTH_ADD_AMOUNT);
                        }
                    }
                }
            }
            if (affected) {
                int soupAmount = itemStack.getAmount();
                if (soupAmount > 1) {
                    itemStack.setAmount(soupAmount - 1);
                    p.getInventory().addItem(new ItemStack(Material.BOWL));
                } else {
                    itemStack.setType(Material.BOWL);
                }
            }
        }
    }
}