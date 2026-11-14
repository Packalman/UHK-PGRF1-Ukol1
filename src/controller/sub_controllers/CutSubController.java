package controller.sub_controllers;

import controller.SubController;
import graphics.rasterizer.RasterizerContext;
import view.Sidebar;

public class CutSubController extends SubController {
    public CutSubController(RasterizerContext context) {
        super(context, Sidebar.Setting.CUT);
    }

    @Override
    public void render() {

    }

    @Override
    public void clear() {

    }
}
