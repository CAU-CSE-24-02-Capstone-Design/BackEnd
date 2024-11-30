# INSERT INTO question(question_index, description)
# VALUES (1, '만약 당신이 하루 동안 다른 사람의 삶을 살아볼 수 있다면, 누구의 삶을 살고 싶나요? 그 이유를 1분 동안 설명해보세요!');
# INSERT INTO question(question_index, description)
# VALUES (2, '만약 당신의 성격이 하나의 날씨라면, 어떤 날씨를 닮았다고 생각하나요? 그 날씨를 닮은 이유를 1분 동안 설명해보세요!');
# # INSERT INTO question(question_index, description)
# # VALUES (3, '자신의 성격을 동물로 비유한다면, 어떤 동물에 가까운가요? 그 동물을 닮은 이유를 1분 동안 말해보세요!');
# # INSERT INTO question(question_index, description)
# # VALUES (4, '당신이 가장 좋아하는 계절은 무엇인가요? 그 계절을 좋아하는 이유를 1분 동안 설명해보세요!');
# # INSERT INTO question(question_index, description)
# # VALUES (5, '자신의 인생을 영화로 만든다면, 어떤 장면이 가장 중요하다고 생각하나요? 그 장면을 선택한 이유를 1분 동안 말해보세요!');
# # INSERT INTO question(question_index, description)
# # VALUES (6, '당신은 어떤 도형을 닮은 것 같나요? 그 도형을 닮은 이유는 무엇인가요? 1분동안 말해보세요!');
# # INSERT INTO question(question_index, description)
# # VALUES (7, '자신을 한 단어로 표현한다면, 어떤 단어가 가장 적합하다고 생각하나요? 그 단어를 고른 이유를 1분 동안 말해보세요!');
#
#
# INSERT INTO user(created_at, email, name, oauth_server_id, voice_url, oauth_server, role)
# VALUES ('2024-11-14-00:01:04', 'test@example.com', 'test', '1', 'test voice url', 'KAKAO', 'USER');
#
# INSERT INTO user(created_at, email, name, oauth_server_id, voice_url, oauth_server, role)
# VALUES ('2024-11-13-00:01:04', 'test2@example.com', 'test2', '2', 'test voice url', 'KAKAO', 'USER');
#
# INSERT INTO answer(created_at, user_id, evaluation, question_id)
# VALUES ('2024-11-29-00:01:05', '2', 0, 1);
#
# INSERT INTO answer(created_at, user_id, evaluation, question_id)
# VALUES ('2024-11-30-00:01:05', '2', 0, 2);
#
#
# INSERT INTO feedback(user_id, answer_id, before_script)
# VALUES (2, 1,
#         '음, 만약 하루 동안 다른 사람의 삶을 살 수 있다면... 어, 저는 아마 여행을 많이 다니는 사람의 삶을 살고 싶어요. (1초..) 왜냐면, 음 뭐랄까 저는 여행을 좋아하는데, 사실, 뭐 시간도 잘 안 되고, 그럴 때가 많잖아요? (2초 ..) 그래서 그런 사람처럼 자유롭게 세계를 돌아다니고, 새로운 문화를 경험해보고 싶어요. (1초..) 아, 그리고 그런 사람은 매일매일 새로운 곳에서 음, 그런 신선한 기분을 느낄 수 있잖아요? (2초..) 그래서 그 사람의 삶을 살아보면 그, 뭐랄까, 뭔가 조금 더 자유롭고 신나는 하루를 보낼 수 있을 것 같아서 좋을 것 같아요.');
#
# INSERT INTO feedback(user_id, answer_id, before_script)
# VALUES (2, 2,
#         '음 아, 제 성격이 음, 날씨라면 뭐랄까, 비 오는 날씨인 것 같아요. (2초..) 왜냐면, 그 어, 저는 사실 좀 차분하고, 뭐랄까 혼자 있을 때 더 편안함을 느끼는 편이거든요. 아, 그래서 비 오는 날 그, 비가 내리면 뭔가 차분해지고, 생각도 깊어지고, 그러잖아요. (2초..) 아, 근데 또, 때때로 갑자기 막, 뭐랄까 흥분하거나, 기분이 확 변할 때도 있어서, 그런 점에서 뭐, 번개나 천둥처럼 좀 변할 때도 있죠. (1초..) 그, 뭐 그런 감정의 변화를 잘 다룬다고 생각하니까 비와 비슷한 것 같아요.');
#
