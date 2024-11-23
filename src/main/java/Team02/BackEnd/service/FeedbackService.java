package Team02.BackEnd.service;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_HEADER_NAME;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackRequestDto.GetComponentToMakeFeedbackDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetRespondDto;
import Team02.BackEnd.repository.FeedbackRepository;
import java.util.List;
import java.util.Objects;
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

    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/records/feedbacks";
    private static final String FASTAPI_API_URL_LOCAL = "http://localhost:8000/api/fastapi/records/feedbacks";
    private static final int LIMIT_PAST_AUDIO_NUMBER = 5;

    private final RestTemplate restTemplate = new RestTemplate();
    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final AnswerService answerService;

    public void createFeedbackData(final String accessToken, final Long answerId) {
        Feedback feedback = getFeedbackByAnswerId(answerId);
        User user = userService.getUserByToken(accessToken);

        ResponseEntity<GetFeedbackToFastApiDto> response =
                getFeedbackFromFastApi(accessToken, feedback, user, answerId);
        validateFeedbackFromFastApi(response);

        feedback.updateFeedbackData(Objects.requireNonNull(response.getBody()));
        feedbackRepository.save(feedback);

        log.info("스피치 분석 데이터 생성, feedbackId : {}", feedback.getId());
    }

    public void saveBeforeAudioLink(final String accessToken, final GetRespondDto getRespondDto) {
        User user = userService.getUserByToken(accessToken);
        Answer answer = answerService.getAnswerByAnswerId(getRespondDto.getAnswerId());
        Feedback feedback = FeedbackConverter.toFeedback(getRespondDto.getBeforeAudioLink(), answer, user);

        feedbackRepository.save(feedback);

        log.info("피드백 받기 전 스피치 URL 저장, feedbackId : {}", feedback.getId());
    }

    public Feedback getFeedbackByAnswerId(final Long answerId) {
        Feedback feedback = feedbackRepository.findByAnswerId(answerId).orElse(null);
        validateFeedbackIsNotNull(feedback);
        return feedback;
    }

    private List<String> getPastAudioLinks(final User user) {
        PageRequest pageRequest = PageRequest.of(0, LIMIT_PAST_AUDIO_NUMBER, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Feedback> feedbackPageList = feedbackRepository.findByUserId(user.getId(), pageRequest);

        List<Feedback> feedbackList = feedbackPageList.getContent();
        if (feedbackPageList.isEmpty()) {
            feedbackList = feedbackRepository.findAllByUserId(user.getId());
        }
        return feedbackList.stream()
                .map(Feedback::getBeforeAudioLink)
                .toList();
    }

    private ResponseEntity<GetFeedbackToFastApiDto> getFeedbackFromFastApi(final String accessToken,
                                                                           final Feedback feedback,
                                                                           final User user,
                                                                           final Long answerId) {
        String beforeAudioLink = feedback.getBeforeAudioLink();
        List<String> pastAudioLinks = getPastAudioLinks(user);  // MAX 5개, 5개 이하면 다 가져옴

        GetComponentToMakeFeedbackDto getComponentToMakeFeedbackDto =
                FeedbackConverter.toGetComponentToMakeFeedback(beforeAudioLink, user, pastAudioLinks,
                        answerId);
        return makeApiCallToFastApi(accessToken, getComponentToMakeFeedbackDto);
    }

    private ResponseEntity<GetFeedbackToFastApiDto> makeApiCallToFastApi(final String accessToken,
                                                                         final GetComponentToMakeFeedbackDto getComponentToMakeFeedbackDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ACCESS_TOKEN_HEADER_NAME, ACCESS_TOKEN_PREFIX + accessToken);
        HttpEntity<GetComponentToMakeFeedbackDto> request = new HttpEntity<>(getComponentToMakeFeedbackDto,
                headers);
        return restTemplate.postForEntity(FASTAPI_API_URL, request, GetFeedbackToFastApiDto.class);
    }

    private void validateFeedbackIsNotNull(final Feedback feedback) {
        if (feedback == null) {
            throw new FeedbackHandler(ErrorStatus._FEEDBACK_NOT_FOUND);
        }
    }

    private void validateFeedbackFromFastApi(final ResponseEntity<GetFeedbackToFastApiDto> response) {
        if (response.getBody() == null) {
            throw new FeedbackHandler(ErrorStatus._FAST_API_FEEDBACK_NULL);
        }
    }
}
