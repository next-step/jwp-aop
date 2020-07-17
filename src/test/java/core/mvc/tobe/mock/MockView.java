package core.mvc.tobe.mock;

import core.mvc.View;
import core.mvc.exception.ViewRenderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class MockView implements View {

    private String viewName;

    public MockView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        for (Map.Entry<String, ?> entry : model.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }

        if (response instanceof MockHttpServletResponse) {
            MockHttpServletResponse mockHttpServletResponse = (MockHttpServletResponse) response;
            final PrintWriter writer;

            try {
                writer = mockHttpServletResponse.getWriter();
                writer.println(viewName);
                writer.flush();
            }
            catch (Exception e) {
                throw new ViewRenderException(e, viewName);
            }
        }
    }
}
