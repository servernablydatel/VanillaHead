package com.example.vanillahead;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class VanillaHead extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getLogger().info("VanillaHead plugin enabled!");
        // Регистрируем команду /head и говорим, что её обрабатывает этот же класс
        getCommand("head").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("VanillaHead plugin disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            player.getInventory().addItem(skull);
            player.sendMessage("§6Ты получил голову!");
        } else {
            sender.sendMessage("Эту команду можно использовать только в игре.");
        }
        return true;
    }
}
