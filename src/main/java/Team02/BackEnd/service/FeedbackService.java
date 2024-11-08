package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.FeedbackRequestDto.GetComponentToMakeFeedbackDto;
import Team02.BackEnd.dto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.RecordRequestDto.GetRespondDto;
import Team02.BackEnd.exception.validator.FeedbackValidator;
import Team02.BackEnd.repository.FeedbackRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {

    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/record/feedback";
    private static final String FASTAPI_API_URL_LOCAL = "http://localhost:8000/api/fastapi/record/feedback";
    private static final int LIMIT_PAST_AUDIO_NUMBER = 5;

    private final RestTemplate restTemplate = new RestTemplate();

    private final UserService userService;
    private final AnswerService answerService;

    private final FeedbackRepository feedbackRepository;

    public Feedback getFeedbackAndAudio(String accessToken, Long answerId) {
        Feedback feedback = this.getFeedbackByAnswerId(answerId);
        User user = userService.getUserByToken(accessToken);

        String beforeAudioLink = feedback.getBeforeAudioLink();
        String name = user.getName();
        String voiceUrl = user.getVoiceUrl();
        List<String> pastAudioLinks = getPastAudioLinks(user);  // MAX 5개, 5개 이하면 다 가져옴

        ResponseEntity<GetFeedbackToFastApiDto> response
                = getFeedbackToFastApi(accessToken, beforeAudioLink, name, voiceUrl, pastAudioLinks,
                answerId); // fast api로 피드백 받아오기 요청

        if (response.getBody() == null) {
            throw new FeedbackHandler(ErrorStatus._FAST_API_FEEDBACK_NULL);
        }

        // feedback.update(받아온 response);
        feedback.update(
                response.getBody().getBeforeScript(),
                response.getBody().getAfterAudioLink(),
                response.getBody().getAfterScript(),
                response.getBody().getFeedbackText()
        );

        feedbackRepository.save(feedback);

        return feedback;
    }

    public void getBeforeAudioLink(String accessToken, GetRespondDto getRespondDto) {
        System.out.println(getRespondDto.getAnswerId());
        User user = userService.getUserByToken(accessToken);
        Answer answer = answerService.getAnswerByAnswerId(getRespondDto.getAnswerId());
        Feedback feedback = FeedbackConverter.toFeedback(getRespondDto.getBeforeAudioLink(), answer, user);

        feedbackRepository.save(feedback);
    }

    public Feedback getFeedbackByAnswerId(Long answerId) {
        Feedback feedback = feedbackRepository.findByAnswerId(answerId).orElse(null);
        FeedbackValidator.validateFeedbackIsNotNull(feedback);

        return feedback;
    }

    private List<String> getPastAudioLinks(User user) {
        List<Feedback> feedbackList;
        answerService.getAnswerByUserId(user.getId());

        PageRequest pageRequest = PageRequest.of(0, LIMIT_PAST_AUDIO_NUMBER, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Feedback> feedbackPageList = feedbackRepository.findByUserId(user.getId(), pageRequest);

        if (feedbackPageList.isEmpty()) {
            feedbackList = feedbackRepository.findAllByUserId(user.getId());
        } else {
            feedbackList = feedbackPageList.getContent();
        }

        return feedbackList.stream().map(Feedback::getBeforeAudioLink).toList();
    }

    private ResponseEntity<GetFeedbackToFastApiDto> getFeedbackToFastApi(String accessToken, String beforeAudioLink,
                                                                         String name,
                                                                         String voiceUrl, List<String> pastAudioLinks,
                                                                         Long answerId) {
        GetComponentToMakeFeedbackDto getComponentToMakeFeedbackDto =
                FeedbackConverter.toGetComponentToMakeFeedback(beforeAudioLink, name, voiceUrl, pastAudioLinks,
                        answerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<GetComponentToMakeFeedbackDto> request = new HttpEntity<>(getComponentToMakeFeedbackDto,
                headers);

        return restTemplate.postForEntity(FASTAPI_API_URL, request, GetFeedbackToFastApiDto.class);
    }
}
