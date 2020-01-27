package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun.ExampleGun;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "example_gun")
public class ExampleGunCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public ExampleGun create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ExampleGun slotItem = new ExampleGun();
        ConfigurationSection configurationSection = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.HOTBAR_SLOTS, ID).getData();

        ItemStack activeIcon = new ItemStack(Material.getMaterial(configurationSection.get("active_icon").toString()));
        ItemStack unactiveIcon = new ItemStack(Material.getMaterial(configurationSection.get("unactive_icon").toString()));
        long cooldown = Long.parseLong(configurationSection.get("cooldown").toString());
        boolean infinite = Boolean.parseBoolean(configurationSection.get("infinite").toString());
        int amount = Integer.parseInt(configurationSection.get("amount").toString());

        slotItem.setActiveIcon(activeIcon);
        slotItem.setUnactiveIcon(unactiveIcon);
        slotItem.setCooldownTime(cooldown);
        slotItem.setInfiniteSlot(infinite);
        slotItem.setAmount(amount);

        return slotItem;
    }
}
