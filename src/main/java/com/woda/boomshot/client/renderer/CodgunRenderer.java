package com.woda.boomshot.client.renderer;

import com.woda.boomshot.client.model.FishGunModel;
import com.woda.boomshot.common.item.fishguns.AbstractFishGunItem;
import com.woda.boomshot.common.item.fishguns.CodgunItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class CodgunRenderer extends GeoItemRenderer<CodgunItem>
{
    public CodgunRenderer()
    {
        super(new FishGunModel());
    }


}
