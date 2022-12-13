package com.woda.boomshot;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.woda.boomshot.registry.WAEntityRegister;
import com.woda.boomshot.registry.WAItemRegister;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("wodarmoury")
public class Wodarmoury {
    public static final String MOD_ID = "wodarmoury";
    private static final ResourceLocation CROSSHAIR_TEX = new ResourceLocation(Wodarmoury.MOD_ID, "textures/item/shellshot.png");

    public Wodarmoury() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        WAItemRegister.ITEMS.register(bus);
        WAEntityRegister.ENTITIES.register(bus);

       forgeBus.addListener(this::playerTick);
        GeckoLib.initialize();
    }

    private void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Vec3 pos = player.getEyePosition(1f);
        Random random = player.getRandom();
        if (player.isCreative() && player.getAbilities().flying) {
            player.removeTag("inAir");

        }
        if (player.getTags().contains("inAir") && player.isOnGround() && !player.isInWater()) {
            player.removeTag("inAir");
            player.fallDistance -= 5.5f;
            for (int i = 0; i < 55; ++i) {
                BlockState blockBeneath = player.level.getBlockState(new BlockPos(player.getBlockX(), player.getBlockY() - 2f, player.getBlockZ()));
                player.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockBeneath), player.getRandomX(2.5d), player.getY() + 0.5f, player.getRandomZ(2.5d), 0d, 0d, 0d);
            }
            for (int i = 0; i < 25; i++) {
                player.level.addParticle(ParticleTypes.POOF, player.getRandomX(2.5d), player.getY() + 0.5f, player.getRandomZ(2.5d), 0d, 0d, 0d);
            }
            for (LivingEntity livingentity : player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(3d, 0.2D, 3d))) {
                if (livingentity != player) {
                    livingentity.hurt(DamageSource.mobAttack(player), 5.0f);
                }
            }
        }
    }
}