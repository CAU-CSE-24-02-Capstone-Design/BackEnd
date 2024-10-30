package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AccessTokenHandler;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.FeedbackRequestDto;
import Team02.BackEnd.dto.RecordRequestDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.repository.FeedbackRepository;
import Team02.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private static final String FASTAPI_API_URL = "https://peachmentor.com:8000/api/record/feedback";

    private final JwtService jwtService;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public void saveFeedback(FeedbackRequestDto.GetFeedbackDto request) {

    }

    public Feedback getFeedback(String accessToken, Long answerId) {
        Feedback feedback = feedbackRepository.findByAnswerId(answerId).orElse(null);
        if (feedback == null)
            throw new FeedbackHandler(ErrorStatus._FEEDBACK_NOT_FOUND);
        User user = getUser(accessToken);

        // todo 피드백 받아오기
        String beforeAudioLink = feedback.getBeforeAudioLink();
        String name = user.getName();
        String voiceUrl = user.getVoiceUrl();
        List<String> pastAudioLinks = new ArrayList<>();


        // feedback.update(받아온 response);

        return feedback;
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
