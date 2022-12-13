package com.woda.boomshot.client.model;

import com.woda.boomshot.Wodarmoury;
import com.woda.boomshot.common.item.cannons.AbstractBoomShotItem;
import com.woda.boomshot.common.item.cannons.BombBlasterItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BoomShotModel extends AnimatedGeoModel<AbstractBoomShotItem> {
    @Override
    public ResourceLocation getModelLocation(AbstractBoomShotItem object) {
        return new ResourceLocation(Wodarmoury.MOD_ID, "geo/boom_shot.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(AbstractBoomShotItem object) {
        if(object instanceof BombBlasterItem){
            return new ResourceLocation(Wodarmoury.MOD_ID, "textures/item/bomb_blaster.png");
        }
        return new ResourceLocation(Wodarmoury.MOD_ID, "textures/item/boom_shot.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AbstractBoomShotItem animatable) {
        return new ResourceLocation(Wodarmoury.MOD_ID, "animations/boom_shot.animations.json");
    }

}
