package me.clockclap.tct.item.items;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomSpecialItem;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TctItemDetectiveSword implements CustomSpecialItem {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private boolean attackable;
    private boolean quickchat;

    private final GameRole role;
    private final boolean isdefault;
    private final int index;

    public TctItemDetectiveSword() {
        this.index = ItemIndex.DETECTIVES_SHOP_ITEM_SLOT_0;
        this.isdefault = false;
        this.material = Material.IRON_SWORD;
        this.name = "DETECTIVE_SWORD";
        this.displayName = "Detective Sword";
        this.title = "Detective Sword";
        this.description = ChatColor.BLUE + "Detective Item";
        this.role = GameRoles.DETECTIVE;
        this.attackable = false;
        this.quickchat = false;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.item = item;
    }

    @Override
    public void onAttackPlayer(Player attacker, Player target) {
        if(target != null && attacker != null) {
            NanamiTct plugin = NanamiTct.plugin;
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(target.getName()));
            PlayerData data_ = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(attacker.getName()));
            if(!data.isSpectator()) {
                if (data_.getRole() == GameRoles.WOLF && data.getRole() == GameRoles.WOLF) {
                    attacker.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANNOT_ATTACK_WOLF);
                    return;
                }
                if (data_.getRole() == GameRoles.FOX) {
                    if (data.getRole() == GameRoles.FOX) {
                        attacker.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANNOT_ATTACK_FOX);
                        return;
                    }
                    if (data_.getWatcher() != null) {
                        if (data_.getWatcher().getCountFox() <= plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60)) {
                            if (data_.getWatcher().getCountFox() > 0) {
                                data_.getWatcher().setCountFox(plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60));
                            }
                        }
                    }
                }
                if (attacker.getInventory().getItemInMainHand() != null) {
                    for (int i = 0; i < attacker.getInventory().getSize(); i++) {
                        ItemStack item = attacker.getInventory().getItem(i);
                        if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(attacker.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {
                            int amt = item.getAmount() - 1;
                            item.setAmount(amt);
                            attacker.getInventory().setItem(i, amt > 0 ? item : null);
                            attacker.updateInventory();
                            break;
                        }
                    }
                    if (data.getRole() == GameRoles.WOLF || data.getRole() == GameRoles.FOX) {
                        target.damage(target.getHealth(), attacker);
                    } else {
                        attacker.damage(attacker.getHealth());
                    }
                }
            }
        }
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public ItemStack getItemStack() {
        return this.item;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public GameRole getRole() {
        return this.role;
    }

    @Override
    public boolean isQuickChatItem() {
        return this.quickchat;
    }

    @Override
    public boolean isDefault() {
        return this.isdefault;
    }

    @Override
    public boolean isAttackable() {
        return this.attackable;
    }

    @Override
    public void setAttackable(boolean value) {
        this.attackable = value;
    }

    @Override
    public void setItemStack(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
