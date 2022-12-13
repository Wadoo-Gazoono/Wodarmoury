package com.woda.boomshot.common.item.cannons;

import com.woda.boomshot.client.renderer.BoomShotRenderer;
import com.woda.boomshot.registry.WAItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BombBlasterItem extends AbstractBoomShotItem implements IAnimatable, ISyncable {
    public AnimationFactory factory = new AnimationFactory(this);
    public static final Predicate<ItemStack> BOOMSHOT_AMMO = (p_220002_0_) -> p_220002_0_.getItem() == (WAItemRegister.SHELLSHOT.get());
    public Boolean isUpgraded;
    private static final String CONTROLLER = "controller";
    private static final int ANIM_OPEN = 0;
    private int shotsFired = 0;
    public BombBlasterItem(Properties properties) {
        super(properties);
        GeckoLibNetwork.registerSyncable(this);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final BlockEntityWithoutLevelRenderer renderer = new BoomShotRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        Vec3 viewVec = player.getViewVector(1.0f);
        Vec3 pos = player.getEyePosition(1f);
        Random random = player.getRandom();
        ItemStack item = player.getItemInHand(hand);

        if(!getShellShot(item, player).is(WAItemRegister.SHELLSHOT.get()) && !player.isCreative()){
            return InteractionResultHolder.fail(item);
        }
        else {
            if(!player.isCreative()) {
                getShellShot(item, player).shrink(1);
            }
        }

        item.getOrCreateTag().putBoolean("isFired", true);
        for(LivingEntity livingentity : player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(3d, 0.2D, 3d))) {
            if(livingentity != player) {
                livingentity.hurt(DamageSource.mobAttack(player), 5.0f);
            }
        }
        player.setDeltaMovement(viewVec.multiply(-1.0f, -1.0f, -1.0f));
        for(int i = 0; i < 8 + random.nextInt(8); i++){
            level.addParticle(ParticleTypes.FLAME, pos.x() + random.nextFloat(), pos.y() + random.nextFloat(), pos.z() + random.nextFloat(), viewVec.x()/9, viewVec.y()/9, viewVec.z()/9);

            level.addParticle(ParticleTypes.SMOKE, pos.x() + random.nextFloat(), pos.y() + random.nextFloat(), pos.z() + random.nextFloat(), viewVec.x(), viewVec.y(), viewVec.z());
        }
        item.hurtAndBreak(1, player, (p_220009_1_) -> {
            p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
        });
        player.playSound(SoundEvents.GENERIC_EXPLODE, 2.0f, 0.3f);
        if (!level.isClientSide) {
            final ItemStack stack = player.getItemInHand(hand);
            final int id = GeckoLibUtil.guaranteeIDForStack(player.getItemInHand(hand), (ServerLevel) level);
            final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF
                    .with(() -> player);
            GeckoLibNetwork.syncAnimation(target, this, id, 1);
        }
        if(item.getOrCreateTag().contains("shotsFired")){
            player.getCooldowns().addCooldown(this, 35);
            item.getOrCreateTag().remove("shotsFired");
            return super.use(level, player, hand);
        }
        if(!item.getOrCreateTag().contains("shotsFired")){
            item.getOrCreateTag().putInt("shotsFired", 1);
            player.getCooldowns().addCooldown(this, 6);
            return super.use(level, player, hand);
        }
        return super.use(level, player, hand);

    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int i) {
        super.releaseUsing(itemStack, level, entity, i);
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, CONTROLLER, 1, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, CONTROLLER);

        if(state == 1) {
            controller.markNeedsReload();
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.boomshot.fire", false));
        }
    }

    @Override
    public ItemStack getShellShot(ItemStack itemStack, Player player) {
        return super.getShellShot(itemStack, player);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {
        tooltip.add(new TranslatableComponent("item.tooltip.boomshot").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(true)));
        tooltip.add(new TranslatableComponent("item.tooltip.bombblaster").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(true)));
    }
}
