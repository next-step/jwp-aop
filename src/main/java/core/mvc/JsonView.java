package core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.exception.ViewRenderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws ViewRenderException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Optional<Object> renderObject = toJsonObject(model);
        try {
            render(renderObject, response.getOutputStream());
        }
        catch (Exception e) {
            throw new ViewRenderException(e, this.getClass().getSimpleName());
        }
    }

    private void render(Optional<Object> renderObject, ServletOutputStream outputStream) throws ViewRenderException {
        if (renderObject.isPresent()) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                mapper.writeValue(outputStream, renderObject.get());
            }
            catch (Exception e) {
                throw new ViewRenderException(e, this.getClass().getSimpleName());
            }
        }
    }

    private Optional<Object> toJsonObject(Map<String, ?> model) {
        if (model == null || model.isEmpty()) {
            return Optional.empty();
        }

        if (model.size() == 1) {
            return Optional.of(model.values().stream().findFirst().get());
        }

        return Optional.of(model);
    }
}
