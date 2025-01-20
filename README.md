## [2024-2] 중앙대학교 소프트웨어학부 캡스톤 디자인(1) 프로젝트
<img width="100%" alt="image" src="https://github.com/user-attachments/assets/942efb56-1186-416f-8436-81357411320f" />


## 👀 팀원

| 이름    | 신현우                                        | 이진경                            | 이은학                           |
| ------- | --------------------------------------------- | --------------------------------- | ------------------------------- |
| **git** | [Hyun0828](https://github.com/Hyun0828) | [yjg0815](https://github.com/yjg0815) | [2unhi](https://github.com/2unhi)   |


## 💡 기획

> **서비스 이름**

**복숭아멘토 (PeachMentor)**

중요한 회의에서 순간적으로 말문이 막혀 본 적이 있나요? 아니면 생각한 바를 말하다가, 하고자 했던 말이 길을 잃어버린 적은요? 이런 경험은 일상을 사는 누구나 겪을 수 있는 일입니다.

사람들은 누구나 말을 잘하기를 바랍니다. 하지만 말을 잘한다는 것은, 단순히 잘 준비된 발표나 면접에서 유창하게 말하는 것에 그치지 않습니다. **예상치 못한 상황에서 자기 생각을 논리적으로 정리해 침착하게 표현하는 것**이야말로 진정한 소통 능력일 것입니다. 그러나 많은 사람들이 이러한 상황에서 당황하거나 말이 막혀 어려움을 겪습니다.

사용자가 매일 다양한 주제로 1분 동안의 짧은 말하기 연습을 할 수 있게 도와주고, 사용자는 AI 분석을 통해 교정 및 피드백을 받을 수 있습니다.

**이를 통해 사용자가 자신의 언어 습관을 파악하고 개선하며, 일상 대화에서 자연스럽고 자신감 있게 소통할 수 있는 능력을 기를 수 있습니다.** 이러한 훈련은 면접, 발표, 회의 등 중요한 상황에서도 능력을 자연스럽게 발휘할 수 있도록 돕습니다. 또한, 언제 어디서나 쉽게 접근하여 연습할 수 있어 시간과 장소의 제약 없이 효과적인 말하기 연습이 가능합니다.

> **배포 링크**

[🍑 복숭아멘토 바로가기](https://peachmentor.com/)


## 🖥️ 개발

### 전체 페이지 구성도
<table>
  <tr>
    <td><img width="300" alt="로그인" src="https://github.com/user-attachments/assets/4201a4b4-190a-45ca-98c8-211111406915"/></td>
    <td><img width="300" alt="메인 홈" src="https://github.com/user-attachments/assets/9c1cf561-e8bf-48dd-895f-d3b0995db7d6"/></td>
    <td><img width="300" alt="스피치 준비" src="https://github.com/user-attachments/assets/21097fb6-c976-42d9-ae91-3bc0a2951c61"/></td>
    <td><img width="300" alt="오늘의 질문 확인" src="https://github.com/user-attachments/assets/c0f116a9-65f5-4a54-94d6-d99090e56fd0"/></td>
  </tr>
  <tr>
    <td align="center"><b>로그인</b></td>
    <td align="center"><b>메인 홈</b></td>
    <td align="center"><b>스피치 준비</b></td>
    <td align="center"><b>오늘의 질문 확인</b></td>
  </tr>
</table>

<table>
  <tr>
    <td><img width="300" alt="개인 스피치" src="https://github.com/user-attachments/assets/2e745417-75f2-4a65-99a7-ed93f7ce10cf" /></td>
    <td><img width="300" alt="스피치 분석" src="https://github.com/user-attachments/assets/734286be-5cda-42a8-b4d7-8f7e892e802b" /></td>
    <td><img width="300" alt="AI 답변" src="https://github.com/user-attachments/assets/459af5cc-187b-45b9-aae2-144caaa7754d" /></td>
    <td><img width="300" alt="피드백 확인" src="https://github.com/user-attachments/assets/5f03831f-5913-42f9-b380-78330313da4e" /></td>
  </tr>
  <tr>
    <td align="center"><b>개인 스피치</b></td>
    <td align="center"><b>스피치 분석</b></td>
    <td align="center"><b>AI 답변</b></td>
    <td align="center"><b>피드백 확인</b></td>
  </tr>
</table>

<table>
  <tr>
    <td><img width="300" alt="사용자 스크립트" src="https://github.com/user-attachments/assets/b0eb0ce7-b842-4114-bb41-151b13f24512" /></td>
    <td><img width="300" alt="AI 수정 스크립트" src="https://github.com/user-attachments/assets/0a542d74-7560-4f4f-9a28-2ddf478511b7" /></td>
    <td><img width="300" alt="셀프 피드백" src="https://github.com/user-attachments/assets/217e2ca3-8523-4cad-8537-acb01857d55a" /></td>
    <td><img width="300" alt="분석 리포트" src="https://github.com/user-attachments/assets/394e8435-3d0f-47fb-a117-04c549f29455" /></td>
  </tr>
  <tr>
    <td align="center"><b>사용자 스크립트</b></td>
    <td align="center"><b>AI 수정 스크립트</b></td>
    <td align="center"><b>셀프 피드백</b></td>
    <td align="center"><b>분석 리포트</b></td>
  </tr>
</table>

<table>
  <tr>
    <td><img width="200" alt="캘린더" src="https://github.com/user-attachments/assets/32d3506d-d1a5-4b54-b7da-57fc3b919051" /></td>
    <td><img width="200" alt="통계" src="https://github.com/user-attachments/assets/96e53f39-beac-48e4-bed9-ffb97e75cf01" /></td>
  </tr>
  <tr>
    <td align="center"><b>캘린더</b></td>
    <td align="center"><b>통계</b></td>
  </tr>
</table>

### 시스템 아키텍처
<img src="https://github.com/user-attachments/assets/b3f5a466-8449-44f8-8947-9e8a31ab8e94" width="700">

### ERD
![image](https://github.com/user-attachments/assets/709c8ac8-9e3e-4ef8-a476-8aeea136f31b)


### 기술  
<img src="https://img.shields.io/badge/React-61DAFB?style=flat&logo=React&logoColor=white"/> <img src="https://img.shields.io/badge/Tailwind CSS-06B6D4?style=flat&logo=TailwindCSS&logoColor=white"/> <img src="https://img.shields.io/badge/ESLint-4B32C3?style=flat&logo=ESLint&logoColor=white"/> <img src="https://img.shields.io/badge/Prettier-F7B93E?style=flat&logo=Prettier&logoColor=white"/> <img src="https://img.shields.io/badge/Axios-5A29E4?style=flat&logo=Axios&logoColor=white"/>

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white" />  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=SpringSecurity&logoColor=white" /> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat&logo=Spring Data JPA&logoColor=white" /> <img src="https://img.shields.io/badge/FastAPI-009688?style=flat&logo=FastAPI&logoColor=white" /> <img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=Swagger&logoColor=black" /> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white" /> <img src="https://img.shields.io/badge/Redis-FF4438?style=flat&logo=Redis&logoColor=white" /> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat&logo=Gradle&logoColor=white" /> <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white" /> <img src="https://img.shields.io/badge/GithubActions-2088FF?style=flat&logo=GithubActions&logoColor=white" /> <img src="https://img.shields.io/badge/NGINX-009639?style=flat&logo=NGINX&logoColor=white" /> <img src="https://img.shields.io/badge/OpenAI-412991?style=flat&logo=OpenAI&logoColor=white" />

<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat&logo=Amazon EC2&logoColor=white" />  <img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat&logo=Amazon S3&logoColor=white" />  <img src="https://img.shields.io/badge/Amazon Elastic Load Balancing-8C4FFF?style=flat&logo=Amazon Elastic Load Balancing&logoColor=white" /> <img src="https://img.shields.io/badge/Amazon Route 53-8C4FFF?style=flat&logo=Amazon Route 53&logoColor=white" /> 

### 협업  
[<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white" />](https://github.com/CAU-CSE-24-02-Capstone-Design)
<img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=Notion&logoColor=white" />

