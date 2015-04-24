package net.andrevus.jetbrains.tasks;

import com.intellij.tasks.Comment;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskType;
import com.intellij.tasks.impl.LocalTaskImpl;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Date;

/**
 * @author Andrzej Lewandowski
 */
public class TargetProcessAssignable extends Task {

    private Element element;
    private String url;

    public TargetProcessAssignable(Element element, String url) {

        this.element = element;
        this.url = url;
    }

    @NotNull
    @Override
    public String getId() {
        return element.getAttributeValue("Id");
    }

    @NotNull
    @Override
    public String getSummary() {

        return element.getAttributeValue("Name");
    }

    @Nullable
    @Override
    public String getDescription() {

        return element.getChild("Description").getValue();
    }

    @NotNull
    @Override
    public Comment[] getComments() {
        return new Comment[0];
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return LocalTaskImpl.getIconFromType(getType(), isIssue());
    }

    @NotNull
    @Override
    public TaskType getType() {

        return TaskType.FEATURE;
    }

    @Nullable
    @Override
    public Date getUpdated() {
        return null;
    }

    @Nullable
    @Override
    public Date getCreated() {
        return null;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isIssue() {
        return true;
    }

    @Nullable
    @Override
    public String getIssueUrl() {
        return this.url + "/entity/" + this.getId();
    }

}
