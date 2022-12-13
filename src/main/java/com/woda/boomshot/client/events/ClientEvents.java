package com.woda.boomshot.client.events;

import com.woda.boomshot.Wodarmoury;
import com.woda.boomshot.registry.WAEntityRegister;
import com.woda.boomshot.registry.WAItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Wodarmoury.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
    public static final String[] HAND_MODEL_ITEMS = new String[]{"codgun"};

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void FOVModify(FOVModifierEvent event) {
        Player player = event.getEntity();
        if (player.getMainHandItem().is(WAItemRegister.CODGUN.get()) || player.getOffhandItem().is(WAItemRegister.CODGUN.get())) {
            event.setNewfov(Minecraft.getInstance().options.fovEffectScale - 0.3f);
        }
    }


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(WAEntityRegister.WATER_BULLET.get(),(i) -> {
            return new ThrownItemRenderer<>(i, 3.0F, true);
        });
    }
}
