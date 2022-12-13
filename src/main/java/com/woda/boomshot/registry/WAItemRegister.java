package com.woda.boomshot.registry;

import com.woda.boomshot.Wodarmoury;
import com.woda.boomshot.common.item.fishguns.AbstractFishGunItem;
import com.woda.boomshot.common.item.cannons.BombBlasterItem;
import com.woda.boomshot.common.item.cannons.BoomShotItem;
import com.woda.boomshot.common.item.fishguns.CodgunItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WAItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Wodarmoury.MOD_ID);
    //Cannons
    public static final RegistryObject<BoomShotItem> BOOMSHOT = ITEMS.register("boomshot", () -> new BoomShotItem(new Item.Properties().durability(70).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<BombBlasterItem> BOMBBLASTER = ITEMS.register("bombblaster", () -> new BombBlasterItem(new Item.Properties().durability(160).tab(CreativeModeTab.TAB_COMBAT)));
    //Fish Guns
    public static final RegistryObject<CodgunItem> CODGUN = ITEMS.register("codgun", () -> new CodgunItem(new Item.Properties().durability(70).tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> SHELLSHOT = ITEMS.register("shellshot", () -> new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> WATER_BULLET = ITEMS.register("water_bullet", () -> new Item(new Item.Properties().stacksTo(64)));

}
