package com.woda.boomshot.common.item.cannons;

import com.woda.boomshot.registry.WAItemRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Predicate;

public class AbstractBoomShotItem extends Item implements IAnimatable {

    public AbstractBoomShotItem(Properties properties) {
        super(properties);
    }


    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean b) {
        super.inventoryTick(itemStack, level, entity, i, b);
        Player player = (Player) entity;
        if(itemStack.getOrCreateTag().contains("isFired") && !player.isOnGround()){
            player.addTag("inAir");
            itemStack.getOrCreateTag().remove("isFired");
        }
    }

    public ItemStack getShellShot(ItemStack itemStack, Player player){
        final Predicate<ItemStack> predicate = (p_220002_0_) -> p_220002_0_.getItem() == (WAItemRegister.SHELLSHOT.get());
        for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack1 = player.getInventory().getItem(i);
            if (predicate.test(itemstack1)) {
                return itemstack1;
            }
        }

        return player.getAbilities().instabuild ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.MENDING || enchantment == Enchantments.UNBREAKING && super.canApplyAtEnchantingTable(stack, enchantment);
    }


    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
