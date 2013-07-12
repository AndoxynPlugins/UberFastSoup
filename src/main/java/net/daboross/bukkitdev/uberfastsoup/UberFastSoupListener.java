/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.uberfastsoup;

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
public class UberFastSoupListener implements Listener {

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
