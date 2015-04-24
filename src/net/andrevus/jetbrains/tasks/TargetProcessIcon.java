package net.andrevus.jetbrains.tasks;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author Andrzej Lewandowski
 */
public class TargetProcessIcon {

    public static final Icon TargetProcess = load("/icons/targetprocess.png");

    private static Icon load(String path) {
        return IconLoader.getIcon(path, TargetProcessIcon.class);
    }

}
