package Team02.BackEnd.config;

import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.validator.AnalysisValidator;
import Team02.BackEnd.validator.FeedbackValidator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class TestConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AnalysisValidator analysisValidator() {
        return new AnalysisValidator();
    }

    @Bean
    public FeedbackValidator feedbackValidator() {
        return new FeedbackValidator();
    }
}
