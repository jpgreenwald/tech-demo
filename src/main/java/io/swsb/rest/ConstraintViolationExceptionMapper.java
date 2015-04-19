package io.swsb.rest;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swsb
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>
{
    @Override
    public Response toResponse(ConstraintViolationException exception)
    {
        List<Map<String, Object>> violations = new ArrayList<>();
        exception.getConstraintViolations().forEach(v->{
            Map<String,Object> map = new HashMap<>();
            if (v.getPropertyPath() != null && !"".equals(v.getPropertyPath().toString()))
            {
                map.put("path", v.getPropertyPath().toString());
            }
            map.put("message", v.getMessage());
            map.put("invalidValue", v.getInvalidValue());
            violations.add(map);
        });
        return Response.status(Response.Status.CONFLICT).type(MediaType.APPLICATION_JSON).entity(violations).build();
    }
}
