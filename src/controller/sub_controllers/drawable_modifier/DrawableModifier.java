package controller.sub_controllers.drawable_modifier;

import controller.SubController;
import graphics.drawable.Drawable;

import java.awt.event.MouseEvent;

public abstract class DrawableModifier<T extends Drawable> extends SubController {
    private T modifiedDrawable;
    private final DrawableModifiedNotifier drawableModifiedNotifier;

    public DrawableModifier(DrawableModifiedNotifier drawableModifiedNotifier) {
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
        //TODO: what if the modifier produces more, than one new drawable
        void notify(Drawable... newDrawables);
    }
}
