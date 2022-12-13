package com.woda.boomshot.client.renderer;

import com.woda.boomshot.client.model.BoomShotModel;
import com.woda.boomshot.common.item.cannons.AbstractBoomShotItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BoomShotRenderer extends GeoItemRenderer<AbstractBoomShotItem>
{
    public BoomShotRenderer()
    {
        super(new BoomShotModel());
    }


}
