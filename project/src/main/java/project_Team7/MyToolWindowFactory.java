package project_Team7;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.intellij.plugins.relaxNG.compact.RngCompactLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * A factory to create a Project Structure tool window.
 */
public class MyToolWindowFactory implements ToolWindowFactory {

    private static ToolWindow toolWindow;

    public static ToolWindow getToolWindow() {
        return toolWindow;
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ProjectStructureWindow psw = new ProjectStructureWindow();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        Content content = contentFactory.createContent(psw.getContent(), "", false);
        this.toolWindow = toolWindow;
        toolWindow.getContentManager().addContent(content);

    }
}
