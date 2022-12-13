package com.woda.boomshot.client.model;

import com.woda.boomshot.Wodarmoury;
import com.woda.boomshot.common.item.fishguns.AbstractFishGunItem;
import com.woda.boomshot.common.item.fishguns.CodgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FishGunModel extends AnimatedGeoModel<CodgunItem> {
    @Override
    public ResourceLocation getModelLocation(CodgunItem object) {
        return new ResourceLocation(Wodarmoury.MOD_ID, "geo/codgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CodgunItem object) {
        return new ResourceLocation(Wodarmoury.MOD_ID, "textures/item/codgun.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CodgunItem animatable) {
        return new ResourceLocation(Wodarmoury.MOD_ID, "animations/codgun.animations.json");
    }
}
