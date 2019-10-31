/**
 * 
 */
package org.cwit.questions;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author sane
 *
 */
public class TestUtil
{
    public static <T>  Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException 
    {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        return mapper.readValue(json, objectClass);
    }
}
