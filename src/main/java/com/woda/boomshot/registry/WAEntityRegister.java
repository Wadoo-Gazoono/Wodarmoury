package com.woda.boomshot.registry;

import com.woda.boomshot.Wodarmoury;
import com.woda.boomshot.common.entity.WaterBulletEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.nio.file.Watchable;

public class WAEntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Wodarmoury.MOD_ID);


    public static final RegistryObject<EntityType<WaterBulletEntity>> WATER_BULLET = create("water_bullet", EntityType.Builder.<WaterBulletEntity>of(WaterBulletEntity::new, MobCategory.MISC).sized(0.1f, 0.1f));


    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, () -> builder.build(Wodarmoury.MOD_ID + "." + name));
    }
}
