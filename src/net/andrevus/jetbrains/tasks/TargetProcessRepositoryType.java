package net.andrevus.jetbrains.tasks;

import com.intellij.tasks.TaskRepository;
import com.intellij.tasks.impl.BaseRepositoryType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Andrzej Lewandowski
 */
public class TargetProcessRepositoryType extends BaseRepositoryType<TargetProcessRepository> {
    @NotNull
    @Override
    public String getName() {
        return "TargetProcess";
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return TargetProcessIcon.TargetProcess;
    }

    @NotNull
    @Override
    public TaskRepository createRepository() {
        return new TargetProcessRepository(this);
    }

    @Override
    public Class<TargetProcessRepository> getRepositoryClass() {
        return TargetProcessRepository.class;
    }
}
