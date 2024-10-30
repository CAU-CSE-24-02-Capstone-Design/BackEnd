package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AccessTokenHandler;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.FeedbackRequestDto;
import Team02.BackEnd.dto.FeedbackResponseDto;
import Team02.BackEnd.dto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.RecordRequestDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.repository.FeedbackRepository;
import Team02.BackEnd.repository.UserRepository;
import java.awt.print.Pageable;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private static final String FASTAPI_API_URL = "https://peachmentor.com:8000/api/record/feedback";
    private static final int LIMIT_PAST_AUDIO_NUMBER = 5;
    private final RestTemplate restTemplate = new RestTemplate();

    private final JwtService jwtService;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public Feedback getFeedback(String accessToken, Long answerId) {
        Feedback feedback = feedbackRepository.findByAnswerId(answerId).orElse(null);
        if (feedback == null)
            throw new FeedbackHandler(ErrorStatus._FEEDBACK_NOT_FOUND);
        User user = getUser(accessToken);

        String beforeAudioLink = feedback.getBeforeAudioLink();
        String name = user.getName();
        String voiceUrl = user.getVoiceUrl();

        // MAX 5개, 5개 이하면 다 가져옴
        List<String> pastAudioLinks = getPastAudioLinks(user);

        ResponseEntity<FeedbackResponseDto.GetFeedbackToFastApiDto> response
                = getFeedbackToFastApi(beforeAudioLink,name,voiceUrl,pastAudioLinks);

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

        return feedback;
    }

    private List<String> getPastAudioLinks(User user) {
        List<Feedback> feedbackList;
        if(answerRepository.findByUserId(user.getId()) == null){
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
        PageRequest pageRequest = PageRequest.of(0, LIMIT_PAST_AUDIO_NUMBER, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Feedback> feedbackPageList = feedbackRepository.findByUserId(user.getId(), pageRequest);

        if (feedbackPageList.isEmpty()) {
            feedbackList = feedbackRepository.findAllByUserId(user.getId());
        }else{
            feedbackList = feedbackPageList.getContent();
        }

        return feedbackList.stream().map(Feedback::getBeforeAudioLink).toList();
    }

    private ResponseEntity<GetFeedbackToFastApiDto> getFeedbackToFastApi(String beforeAudioLink, String name, String voiceUrl, List<String> pastAudioLinks) {
        // fast api로 보낼 request dto
        FeedbackRequestDto.GetComponentToMakeFeedback getComponentToMakeFeedback =
                FeedbackConverter.toGetComponentToMakeFeedback(beforeAudioLink, name, voiceUrl, pastAudioLinks);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FeedbackRequestDto.GetComponentToMakeFeedback> request = new HttpEntity<>(getComponentToMakeFeedback, headers);

        ResponseEntity<FeedbackResponseDto.GetFeedbackToFastApiDto> response =
                restTemplate.postForEntity(FASTAPI_API_URL, request, FeedbackResponseDto.GetFeedbackToFastApiDto.class);

        return response;
    }

    public void setBeforeAudioLink(RecordRequestDto.GetRespondDto getRespondDto) {
        Answer answer = answerRepository.findById(getRespondDto.getAnswerId()).orElse(null);
        if(answer == null){
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
        Feedback feedback = FeedbackConverter.toFeedback(getRespondDto.getBeforeAudioLink(), answer);
        feedbackRepository.save(feedback);
    }

    private User getUser(String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        if (email == null)
            throw new AccessTokenHandler(ErrorStatus._ACCESSTOKEN_NOT_VALID);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null)
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);

        return user;
    }
}
