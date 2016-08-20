import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.buyanov.experimental.jpa.e2.ApplicationJpaExperimentTwo;
import ru.buyanov.experimental.jpa.e2.domain.Answer;
import ru.buyanov.experimental.jpa.e2.domain.Checklist;
import ru.buyanov.experimental.jpa.e2.domain.Question;

import java.util.List;

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
        List<Answer> answers = answerRepository.findAllByChecklist_Id(1);
        for (Answer answer : answers) {
            Question question = answer.getQuestion();
            log.info(String.format("%s - %s - %f", question.getCategory().getName(), question.getName(), answer.getGrade()));
        }
    }
}
