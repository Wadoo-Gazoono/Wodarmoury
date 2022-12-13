package com.woda.boomshot.common.entity;

import com.woda.boomshot.registry.WAItemRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class WaterBulletEntity extends ThrowableItemProjectile {

    public WaterBulletEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    protected Item getDefaultItem() {
        return WAItemRegister.WATER_BULLET.get();
    }
}
