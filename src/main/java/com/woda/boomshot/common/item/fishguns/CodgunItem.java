package com.woda.boomshot.common.item.fishguns;

import com.woda.boomshot.client.renderer.CodgunRenderer;
import com.woda.boomshot.registry.WAItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class CodgunItem extends AbstractFishGunItem{
    private int cooldown;

    public CodgunItem(Properties properties) {
        super(properties);
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 8;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean bool) {
        super.inventoryTick(stack, level, entity, i, bool);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
            //player.playSound(SoundEvents.GENERIC_EXPLODE, 2.0f, 0.3f);
            if (!level.isClientSide) {
                final ItemStack stack = player.getItemInHand(hand);
                final int id = GeckoLibUtil.guaranteeIDForStack(player.getItemInHand(hand), (ServerLevel) level);
                final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF
                        .with(() -> player);
                GeckoLibNetwork.syncAnimation(target, this, id, 1);
            }
            item.hurtAndBreak(1, player, (p_220009_1_) -> {
                p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
            });
        return super.use(level, player, hand);


    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final BlockEntityWithoutLevelRenderer renderer = new CodgunRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void onAnimationSync(int id, int state) {
        final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, "controller");

        super.onAnimationSync(id, state);
        if(state == 1){
            controller.markNeedsReload();
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.codgun.shoot", false));
        }
    }


    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return super.getUseAnimation(p_41452_);
    }
}
