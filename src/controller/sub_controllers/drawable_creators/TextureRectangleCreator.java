package controller.sub_controllers.drawable_creators;

import controller.Controller;
import graphics.rasterizer.RasterizerContext;
import view.Sidebar;

public class TextureRectangleCreator extends CreatorSubController{
    public TextureRectangleCreator(RasterizerContext context, Controller.DrawableSubmitter submitter) {
        super(context, Sidebar.Setting.NONE, submitter);
    }

    @Override
    public void render() {

    }

    @Override
    public void clear() {

    }
}
