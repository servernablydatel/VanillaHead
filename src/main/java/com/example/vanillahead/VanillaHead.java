package com.example.vanillahead;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VanillaHead extends JavaPlugin {

    @Override
    public void onEnable() {
        // Получаем команду из plugin.yml
        PluginCommand cmd = getCommand("vanillahead");
        
        // Если команда прописана в plugin.yml, вешаем на неё обработчик
        if (cmd != null) {
            cmd.setExecutor((sender, command, label, args) -> {
                if (sender instanceof Player player) {
                    player.sendMessage(ChatColor.GREEN + "✅ VanillaHead успешно работает!");
                    return true;
                } else {
                    sender.sendMessage("Эту команду можно использовать только в игре.");
                    return true;
                }
            });
        } else {
            getLogger().warning("Команда 'vanillahead' не найдена в plugin.yml! Проверьте отступы.");
        }

        getLogger().info("🟩 VanillaHead plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("🟥 VanillaHead plugin disabled!");
    }
}
