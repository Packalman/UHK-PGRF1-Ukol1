package controller.sub_controllers.drawable_creators;

import controller.Controller;
import controller.SubController;
import graphics.drawable.Drawable;

public abstract class CreatorSubController extends SubController {
    private final Controller.DrawableSubmitter submitter;

    public CreatorSubController(Controller.DrawableSubmitter submitter) {
        this.submitter = submitter;
    }

    void submitDrawable(Drawable drawable){
        submitter.submit(drawable);
    }
}
