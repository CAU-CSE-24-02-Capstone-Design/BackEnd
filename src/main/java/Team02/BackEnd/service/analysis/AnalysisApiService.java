package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_HEADER_NAME;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnalysisHandler;
import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.analysisDto.AnalysisRequestDto.GetComponentToMakeAnalysisDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisFromFastApiDto;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnalysisApiService {
    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/records/analyses";
    private static final String FASTAPI_API_URL_LOCAL = "http://localhost:8000/api/fastapi/records/analyses";
    private static final int NUMBER_OF_USER_SPEECH = 7;

    private final UserCheckService userCheckService;
    private final FeedbackCheckService feedbackCheckService;
    private final AnswerCheckService answerCheckService;
    private final RestTemplate restTemplate;
    private final AnalysisRepository analysisRepository;

    public void createAnalysis(String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);

        List<String> questions = answerCheckService.findQuestionDescriptionsByUser(user, NUMBER_OF_USER_SPEECH);
        List<String> beforeScripts = feedbackCheckService.findBeforeScriptByUser(user, NUMBER_OF_USER_SPEECH);

        GetAnalysisFromFastApiDto response = getAnalysisFromFastApi(accessToken, questions, beforeScripts);

        Analysis analysis = AnalysisConverter.toAnalysis(response.getAnalysisText(), user);
        analysisRepository.save(analysis);
    }

    public GetAnalysisFromFastApiDto getAnalysisFromFastApi(final String accessToken,
                                                            final List<String> questions,
                                                            final List<String> beforeScripts) {

        GetComponentToMakeAnalysisDto getComponentToMakeAnalysisDto =
                AnalysisConverter.toGetComponentToMakeAnalysisDto(questions, beforeScripts);
        ResponseEntity<GetAnalysisFromFastApiDto> response = makeApiCallToFastApi(accessToken,
                getComponentToMakeAnalysisDto);
        validateAnalysisFromFastApi(response);
        return response.getBody();
    }

    private ResponseEntity<GetAnalysisFromFastApiDto> makeApiCallToFastApi(final String accessToken,
                                                                           final GetComponentToMakeAnalysisDto componentToMakeAnalysisDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ACCESS_TOKEN_HEADER_NAME, ACCESS_TOKEN_PREFIX + accessToken);
        HttpEntity<GetComponentToMakeAnalysisDto> request = new HttpEntity<>(componentToMakeAnalysisDto,
                headers);
        return restTemplate.postForEntity(FASTAPI_API_URL, request, GetAnalysisFromFastApiDto.class);
    }

    private void validateAnalysisFromFastApi(final ResponseEntity<GetAnalysisFromFastApiDto> response) {
        if (response.getBody() == null) {
            throw new AnalysisHandler(ErrorStatus._FAST_API_ANALYSIS_NULL);
        }
    }
}
