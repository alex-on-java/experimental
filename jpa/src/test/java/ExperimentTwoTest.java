import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.buyanov.experimental.jpa.e2.ApplicationJpaExperimentTwo;
import ru.buyanov.experimental.jpa.e2.domain.Category;
import ru.buyanov.experimental.jpa.e2.domain.Question;

/**
 * @author A.Buyanov 20.08.2016.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ApplicationJpaExperimentTwo.class})
@Transactional
public class ExperimentTwoTest extends ExperimentTwoTestBasic {
    private static final Logger log = LoggerFactory.getLogger(ExperimentTwoTest.class);


    @Test
    public void test() {
        Question question = questionRepository.findOne(1);
        Category category = question.getCategory();
        log.info(String.format("category's name = '%s'%n", category.getName()));
        log.info(String.format("category parent's name = '%s'%n", category.getParent().getName()));
        log.info(String.format("questions size = '%d'%n", category.getQuestions().size()));
    }
}
