package org.acme.openapi.swaggerui;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@OpenAPIDefinition(
        info=@Info(
                title = "",
                version = ""
        ),
        security = @SecurityRequirement(name="bearerAuth",scopes={"read:pets", "write:pets"}),
        components = @Components(
                responses = {
                        @APIResponse(responseCode = "401",description = "Unauthorized"),
                        @APIResponse(responseCode = "403",description = "Forbidden")
                }
        )
)
public class OpenApiConfig extends Application { }
