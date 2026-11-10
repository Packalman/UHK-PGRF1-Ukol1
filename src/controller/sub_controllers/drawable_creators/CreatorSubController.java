package controller.sub_controllers.drawable_creators;

import controller.Controller;
import controller.SubController;
import graphics.drawable.Drawable;
import graphics.rasterizer.RasterizerContext;

public abstract class CreatorSubController extends SubController {
    private final Controller.DrawableSubmitter submitter;

    public CreatorSubController(RasterizerContext context, Controller.DrawableSubmitter submitter) {
        super(context);
        this.submitter = submitter;
    }

    void submitDrawable(Drawable drawable){
        submitter.submit(drawable);
    }
}
