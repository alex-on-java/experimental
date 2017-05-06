import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.buyanov.experimental.jpa.e2.domain.*;
import ru.buyanov.experimental.jpa.e2.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

/**
 * @author A.Buyanov 20.08.2016.
 */
public abstract class ExperimentTwoTestBasic {
    public static final String BLOCK_NAME = "Block";
    public static final String PROCESS_NAME = "Process";
    public static final String PROCESS_ONE_NAME = PROCESS_NAME + " #1";
    public static final String PROCESS_TWO_NAME = PROCESS_NAME + " #2";
    public static final String PROCESS_THREE_NAME = PROCESS_NAME + " #3";
    public static final String TEMPLATE_NAME = "Template";


    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected CategoryGradeRepository gradeRepository;

    @Autowired
    protected QuestionRepository questionRepository;

    @Autowired
    protected TemplateRepository templateRepository;

    @Autowired
    protected ChecklistRepository checklistRepository;

    @Autowired
    protected AnswerRepository answerRepository;

    @PersistenceContext
    protected EntityManager em;


    @Before
    public void initData() {
        getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.WARN);
        getLogger("org.hibernate.SQL").setLevel(Level.WARN);
        Template template = templateRepository.save(new Template(TEMPLATE_NAME));
        Category group = categoryRepository.save(new Category(BLOCK_NAME));
        Category anotherGroup = categoryRepository.save(new Category(BLOCK_NAME + " #2"));
        Category processOne = categoryRepository.save(new Category(PROCESS_ONE_NAME, group));
        Category processTwo = categoryRepository.save(new Category(PROCESS_TWO_NAME, group));
        categoryRepository.save(new Category(PROCESS_THREE_NAME, anotherGroup));
        List<Question> questions = questionRepository.save(Arrays.asList(new Question("Q #1", processOne, template),
                                                                            new Question("Q #2", processOne, template),
                                                                            new Question("Q #3", processOne, template),
                                                                            new Question("Q #4", processTwo, template),
                                                                            new Question("Q #5", processTwo, template),
                                                                            new Question("Q #6", processTwo, template)));
        Checklist checklist = checklistRepository.save(new Checklist(4.0f, template));
        float g = 2.6f;
        for (Question question : questions) {
            answerRepository.save(new Answer(question, checklist, g += 0.4f));
        }
        gradeRepository.save(new CategoryGrade(checklist, processOne, 3.4f));
        gradeRepository.save(new CategoryGrade(checklist, processTwo, 4.6f));


        em.clear(); // clear cache
        System.out.println("\n\ntest begin\n");
        //getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.TRACE);
        getLogger("org.hibernate.SQL").setLevel(Level.DEBUG);
    }

    @After
    public void tearDown() {
        System.out.println("\n\ntest end\n");
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.type.descriptor.sql.BasicBinder")).setLevel(Level.WARN);
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.WARN);
    }

    protected Logger getLogger(String name) {
        return (Logger) LoggerFactory.getLogger(name);
    }
}
