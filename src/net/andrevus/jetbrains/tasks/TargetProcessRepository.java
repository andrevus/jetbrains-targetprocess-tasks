package net.andrevus.jetbrains.tasks;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskRepositoryType;
import com.intellij.tasks.impl.BaseRepository;
import com.intellij.tasks.impl.BaseRepositoryImpl;
import com.intellij.util.NullableFunction;
import com.intellij.util.containers.ContainerUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.util.List;

/**
 * @author Andrzej Lewandowski
 */
public class TargetProcessRepository extends BaseRepositoryImpl {


    private static final Logger LOG = Logger.getInstance(TargetProcessRepository.class);

    public TargetProcessRepository(TaskRepositoryType type) {
        super(type);
        setUseHttpAuthentication(true);
    }

    public TargetProcessRepository(TargetProcessRepository other) {
        super(other);
    }



    @Nullable
    @Override
    public Task findTask(@NotNull String id) throws Exception {

        return null;
    }

    @Override
    public Task[] getIssues(@Nullable String query, int offset, int limit, boolean withClosed, @NotNull ProgressIndicator cancelled) throws Exception {

        HttpClient client = this.getHttpClient();
        String requestUrl = getUrl() + "/api/v1/Tasks";

        if (StringUtil.isNotEmpty(query)) {
            requestUrl += "/?where=" + encodeUrl("(Name contains '" + query + "')");
        }

        GetMethod method = new GetMethod(requestUrl);
        client.executeMethod(method);

        try {
            Element element;
            try {
                InputStream stream = method.getResponseBodyAsStream();
                InputSource source = new InputSource(stream);
                source.setEncoding("UTF-8");
                element = new SAXBuilder(false).build(source).getRootElement();
            } catch (JDOMException e) {
                LOG.error("Can't parse TargetProcess response for " + requestUrl, e);
                throw e;
            }

            List<Element> children = element.getChildren("Task");

            final List<Task> tasks = ContainerUtil.mapNotNull(children, new NullableFunction<Element, Task>() {
                public Task fun(Element o) {
                    return new TargetProcessTask(o, getUrl());
                }
            });
            return tasks.toArray(new Task[tasks.size()]);
        } finally {
            method.releaseConnection();
        }
    }

    @NotNull
    @Override
    public BaseRepository clone() {
        return new TargetProcessRepository(this);
    }

    @Override
    protected int getFeatures() {
        return super.getFeatures() | NATIVE_SEARCH;
    }
}
