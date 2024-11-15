Insert INTO question(question_index, description)
values (1, '자신을 한 단어로 표현한다면, 어떤 단어가 가장 적합하다고 생각하나요? 그 단어를 고른 이유를 1분 동안 말해보세요!');
Insert INTO question(question_index, description)
values (2, '당신은 어떤 색을 닮았다고 생각하나요? 그 색을 닮은 이유는 무엇인가요? 1분 동안 설명해보세요!');
Insert INTO question(question_index, description)
values (3, '자신의 성격을 동물로 비유한다면, 어떤 동물에 가까운가요? 그 동물을 닮은 이유를 1분 동안 말해보세요!');
Insert INTO question(question_index, description)
values (4, '당신이 가장 좋아하는 계절은 무엇인가요? 그 계절을 좋아하는 이유를 1분 동안 설명해보세요!');
Insert INTO question(question_index, description)
values (5, '자신의 인생을 영화로 만든다면, 어떤 장면이 가장 중요하다고 생각하나요? 그 장면을 선택한 이유를 1분 동안 말해보세요!');
Insert INTO question(question_index, description)
values (6, '당신은 어떤 도형을 닮은 것 같나요? 그 도형을 닮은 이유는 무엇인가요? 1분동안 말해보세요!');


INSERT INTO user(created_at, email, name, oauth_server_id, voice_url, oauth_server, role)
VALUES ('2024-11-14-00:01:04', 'test@example.com', 'test', '1', 'test voice url', 'KAKAO', 'USER');

INSERT INTO user(created_at, email, name, oauth_server_id, voice_url, oauth_server, role)
VALUES ('2024-11-13-00:01:04', 'test2@example.com', 'test2', '2', 'test voice url', 'KAKAO', 'USER');

INSERT INTO answer(created_at, user_id, evaluation)
VALUES ('2024-11-13-00:01:05', '2', 0);
