package filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ApiLoggingFilter implements Filter {

    private static final Logger LOGGER =
            LogManager.getLogger(ApiLoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext context) {

        LOGGER.info("==================================================");
        LOGGER.info("API REQUEST");
        LOGGER.info("==================================================");

        LOGGER.info("Method        : {}", requestSpec.getMethod());
        LOGGER.info("URI           : {}", requestSpec.getURI());

        if (!requestSpec.getHeaders().asList().isEmpty()) {
            LOGGER.info("Headers       : {}", requestSpec.getHeaders());
        }

        if (!requestSpec.getQueryParams().isEmpty()) {
            LOGGER.info("Query Params  : {}", requestSpec.getQueryParams());
        }

        if (!requestSpec.getPathParams().isEmpty()) {
            LOGGER.info("Path Params   : {}", requestSpec.getPathParams());
        }

        if (requestSpec.getBody() != null) {
            LOGGER.info("Request Body :");
            LOGGER.info(requestSpec.getBody().toString());
        }

        long startTime = System.currentTimeMillis();

        Response response = context.next(requestSpec, responseSpec);

        long endTime = System.currentTimeMillis();

        LOGGER.info("==================================================");
        LOGGER.info("API RESPONSE");
        LOGGER.info("==================================================");

        LOGGER.info("Status Code   : {}", response.statusCode());
        LOGGER.info("Response Time : {} ms", (endTime - startTime));

        LOGGER.info("Response Body :");
        LOGGER.info(response.asPrettyString());

        LOGGER.info("==================================================");

        return response;
    }
}