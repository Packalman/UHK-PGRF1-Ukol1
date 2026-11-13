package controller.sub_controllers.drawable_modifier;

import controller.SubController;
import graphics.drawable.Drawable;
import graphics.rasterizer.RasterizerContext;
import view.Sidebar;

import java.awt.event.MouseEvent;

public abstract class DrawableModifier<T extends Drawable> extends SubController {
    private T modifiedDrawable;
    private final DrawableModifiedNotifier drawableModifiedNotifier;

    public DrawableModifier(RasterizerContext context, Sidebar.Setting setting, DrawableModifiedNotifier drawableModifiedNotifier) {
        super(context, setting);
        this.drawableModifiedNotifier = drawableModifiedNotifier;
    }

    public void setModifiedDrawable(T drawable, MouseEvent e){
        this.modifiedDrawable = drawable;
    }

    @SafeVarargs
    final void notifyDrawableModified(T... newDrawable){
        drawableModifiedNotifier.notify(newDrawable);
    }

    T getModifiedDrawableToModify(){
        return modifiedDrawable;
    }

    public interface DrawableModifiedNotifier {
        void notify(Drawable... newDrawables);
    }
}
