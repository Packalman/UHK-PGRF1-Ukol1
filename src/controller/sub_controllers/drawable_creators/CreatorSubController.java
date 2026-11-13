package controller.sub_controllers.drawable_creators;

import controller.Controller;
import controller.SubController;
import graphics.drawable.Drawable;
import graphics.rasterizer.RasterizerContext;
import view.Sidebar;

public abstract class CreatorSubController extends SubController {
    private final Controller.DrawableSubmitter submitter;

    public CreatorSubController(RasterizerContext context, Sidebar.Setting setting, Controller.DrawableSubmitter submitter) {
        super(context, setting);
        this.submitter = submitter;
    }

    void submitDrawable(Drawable drawable){
        submitter.submit(drawable);
    }
}
