package org.acme.openapi.swaggerui;

import io.quarkus.smallrye.openapi.OpenApiFilter;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.Components;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.responses.APIResponse;
import org.eclipse.microprofile.openapi.models.responses.APIResponses;

/**
 * Filter to add custom elements
 */
@OpenApiFilter(OpenApiFilter.RunStage.RUN)
public class MyRunTimeFilter implements OASFilter {

    @Override
    public void filterOpenAPI(OpenAPI openAPI) {
        // Create global responses
        APIResponse response401 = OASFactory.createAPIResponse().description("Unauthorized");
        APIResponse response403 = OASFactory.createAPIResponse().description("Forbidden");

        // Add responses to components
        Components components = openAPI.getComponents();
        if (components == null) {
            components = OASFactory.createComponents();
            openAPI.setComponents(components);
        }
        components.addResponse("401", response401);
        components.addResponse("403", response403);

        // Add responses to all operations using streams
        openAPI.getPaths().getPathItems().values().stream()
                .flatMap(pathItem -> pathItem.getOperations().values().stream())
                .forEach(operation -> {
                    APIResponses responses = operation.getResponses();
                    if (responses == null) {
                        responses = OASFactory.createAPIResponses();
                        operation.setResponses(responses);
                    }
                    responses.addAPIResponse("401", response401);
                    responses.addAPIResponse("403", response403);
                });
    }

}