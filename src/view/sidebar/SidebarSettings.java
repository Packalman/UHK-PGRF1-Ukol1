package view.sidebar;

import view.sidebar.ui.SidebarComponent;


public abstract class SidebarSettings {
    public abstract SidebarComponent[] getComponents();
    private SidebarSettingRebuiltHandler settingRebuiltHandler;
    public final void setSidebarSettingModifiedHandler(SidebarSettingRebuiltHandler settingRebuiltHandler){
        this.settingRebuiltHandler = settingRebuiltHandler;
    }

    public final void settingsChangedNotify(){
        settingRebuiltHandler.settingsRebuiltNotify();
    }

    public interface SidebarSettingRebuiltHandler {
        void settingsRebuiltNotify();
    }
}
