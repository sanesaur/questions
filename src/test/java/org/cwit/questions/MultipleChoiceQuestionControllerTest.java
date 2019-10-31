/**
 * 
 */
package org.cwit.questions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.spl.questions.controllers.MultipleChoiceQuestionController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


/**
 * @author sane
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MultipleChoiceQuestionController.class)
public class MultipleChoiceQuestionControllerTest
{
    private static final Logger logger = LoggerFactory.getLogger(MultipleChoiceQuestionControllerTest.class);

    
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void create() throws Exception
    {
        logger.info("*** Test create ***");
        mvc.perform(post("/mcqs")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("question", "Which is a reserved word in the Java programming language?")
                .param("subject", "java")
                .param("choices", "method","native"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.question").value("Which is a reserved word in the Java programming language?"))
            .andExpect(jsonPath("$.choices").isArray())
            .andExpect(jsonPath("$.choices[0]").value("method"));
        mvc.perform(delete("/mcqs/java"))
        .andExpect(status().isNoContent());
    }
    
    @Test
    public void listQuestions() throws Exception
    {
        logger.info("*** Test listQuestions ***");
        mvc.perform(post("/mcqs")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("question", "Which is a reserved word in the Java programming language?")
                .param("subject", "java")
                .param("choices", "method","native"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.question").value("Which is a reserved word in the Java programming language?"))
            .andExpect(jsonPath("$.choices").isArray())
            .andExpect(jsonPath("$.choices[0]").value("method"));
        mvc.perform(get("/mcqs/java"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.[0].question").value("Which is a reserved word in the Java programming language?"))
        .andExpect(jsonPath("$.[0].choices").isArray())
        .andExpect(jsonPath("$.[0].choices[0]").value("method"));
        mvc.perform(delete("/mcqs/java"))
        .andExpect(status().isNoContent());
    }
    
    @Test
    public void deleteQuestions() throws Exception
    {
        logger.info("*** Test deleteQuestions ***");
        mvc.perform(post("/mcqs")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("question", "Which is a reserved word in the Java programming language?")
                .param("subject", "java")
                .param("choices", "method","native"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.question").value("Which is a reserved word in the Java programming language?"))
            .andExpect(jsonPath("$.choices").isArray())
            .andExpect(jsonPath("$.choices[0]").value("method"));
        mvc.perform(delete("/mcqs/java"))
            .andExpect(status().isNoContent());
    }
    
    public void searchQuestionsContainingPhrase() throws Exception
    {
        mvc.perform(post("/mcqs")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("question", "Which is a reserved word in the Java programming language?")
                .param("subject", "java")
                .param("choices", "method","native"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.question").value("Which is a reserved word in the Java programming language?"))
            .andExpect(jsonPath("$.choices").isArray())
            .andExpect(jsonPath("$.choices[0]").value("method"));
        
        mvc.perform(get("/mcqs/java/question?phrase=Which is a reserved word in"))
            .andExpect(jsonPath("$.question").value("Which is a reserved word in the Java programming language?"))
            .andExpect(jsonPath("$.choices").isArray())
            .andExpect(jsonPath("$.choices[0]").value("method"));    
    }
    

}
