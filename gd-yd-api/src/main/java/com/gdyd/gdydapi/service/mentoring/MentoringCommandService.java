package com.gdyd.gdydapi.service.mentoring;

import com.gdyd.gdydapi.request.mentoring.CreateHighSchoolStudentQuestionRequest;
import com.gdyd.gdydapi.request.mentoring.CreateUniversityStudentAnswerRequest;
import com.gdyd.gdydapi.request.mentoring.UpdateUniversityStudentAnswerRequest;
import com.gdyd.gdydapi.request.report.ReportRequest;
import com.gdyd.gdydapi.response.common.LikeListResponse;
import com.gdyd.gdydapi.response.common.ReportResponse;
import com.gdyd.gdydapi.response.common.ScrapListResponse;
import com.gdyd.gdydapi.response.mentoring.CreateHighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.CreateUniversityStudentAnswerResponse;
import com.gdyd.gdydapi.response.mentoring.DeleteUniversityStudentAnswerResponse;
import com.gdyd.gdydapi.response.mentoring.UpdateUniversityStudentAnswerResponse;
import com.gdyd.gdydapi.service.member.MemberQueryService;
import com.gdyd.gdydauth.jwt.JwtProvider;
import com.gdyd.gdydauth.jwt.Token;
import com.gdyd.gdydauth.jwt.UserAuthentication;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.member.*;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestionMedia;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import com.gdyd.gdydcore.domain.report.Report;
import com.gdyd.gdydcore.service.member.LikeListService;
import com.gdyd.gdydcore.service.member.MemberService;
import com.gdyd.gdydcore.service.member.ReportService;
import com.gdyd.gdydcore.service.member.ScrapListService;
import com.gdyd.gdydcore.service.mentoring.HighSchoolStudentQuestionMediaService;
import com.gdyd.gdydcore.service.mentoring.HighSchoolStudentQuestionService;
import com.gdyd.gdydcore.service.mentoring.UniversityStudentAnswerService;
import com.gdyd.gdydsupport.ai.AIRequestGenerator;
import com.gdyd.gdydsupport.ai.AutoAnswerRequest;
import com.gdyd.gdydsupport.ai.ProfanityFilteringRequest;
import com.gdyd.gdydsupport.ai.ProfanityFilteringResponse;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import com.gdyd.gdydsupport.webhook.DiscordMessageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MentoringCommandService {
    private final HighSchoolStudentQuestionService highSchoolStudentQuestionService;
    private final UniversityStudentAnswerService universityStudentAnswerService;
    private final HighSchoolStudentQuestionMediaService highSchoolStudentQuestionMediaService;
    private final MemberQueryService memberQueryService;
    private final MemberService memberService;
    private final LikeListService likeListService;
    private final ScrapListService scrapListService;
    private final ReportService reportService;
    private final DiscordMessageGenerator discordMessageGenerator;
    private final AIRequestGenerator aiBotRequestGenerator;
    private final JwtProvider jwtProvider;
    private final AIRequestGenerator aiRequestGenerator;

    /**
     * 고등학생 질문 생성
     * @param request 고등학생 질문 생성 요청
     */
    public CreateHighSchoolStudentQuestionResponse createHighSchoolStudentQuestion(CreateHighSchoolStudentQuestionRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        HighSchoolStudent highSchoolStudent = memberQueryService.getHighSchoolStudentByMemberId(memberId);

        ProfanityFilteringRequest aiFilteringRequest = ProfanityFilteringRequest.of(request.title(), request.question());
        ProfanityFilteringResponse aiFilteringResponse = aiRequestGenerator.sendAbuseFilteringRequest(aiFilteringRequest);
        if (Boolean.TRUE.equals(aiFilteringResponse.isProfanityDetected())) {
            throw new BusinessException(ErrorCode.CONTAINS_PROFANITY);
        }

        HighSchoolStudentQuestion question = CreateHighSchoolStudentQuestionRequest.toHighSchoolStudentQuestion(request, highSchoolStudent);
        highSchoolStudentQuestionService.save(question);

        if (request.highSchoolStudentQuestionMediaUrls() != null) {
            List<HighSchoolStudentQuestionMedia> medias = CreateHighSchoolStudentQuestionRequest.toHighSchoolStudentQuestionMedia(request, question);
            highSchoolStudentQuestionMediaService.saveAllHighSchoolStudentQuestionMedia(medias);
            question.updateHighSchoolStudentQuestionMedias(medias);
        }
        Member aiBot = memberQueryService.getUniversityStudentByMemberEmail(aiBotRequestGenerator.getAiBotEmail());
        Authentication authentication = new UserAuthentication(aiBot.getId(), null, null);
        Token generatedAccessToken = jwtProvider.generateAccessToken(authentication);
        AutoAnswerRequest autoAnswerRequest = AutoAnswerRequest.of(generatedAccessToken.getValue(), question.getId(), question.getQuestion());
        aiBotRequestGenerator.sendAutoAnswerRequest(autoAnswerRequest);
        return CreateHighSchoolStudentQuestionResponse.from(question);
    }

    /**
     * 대학생 답변 생성
     * @param highSchoolStudentQuestionId 고등학생 질문글 ID
     * @param request 대학생 답변 생성 요청
     */
    public CreateUniversityStudentAnswerResponse createUniversityStudentAnswer(Long highSchoolStudentQuestionId, CreateUniversityStudentAnswerRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        UniversityStudent universityStudent = memberQueryService.getUniversityStudentByMemberId(memberId);

        ProfanityFilteringRequest aiFilteringRequest = ProfanityFilteringRequest.from(request.answer());
        ProfanityFilteringResponse aiFilteringResponse = aiRequestGenerator.sendAbuseFilteringRequest(aiFilteringRequest);
        if (Boolean.TRUE.equals(aiFilteringResponse.isProfanityDetected())) {
            throw new BusinessException(ErrorCode.CONTAINS_PROFANITY);
        }

        HighSchoolStudentQuestion question = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);
        UniversityStudentAnswer answer = CreateUniversityStudentAnswerRequest.toUniversityStudentAnswer(request, universityStudent, question);

        universityStudentAnswerService.save(answer);
        question.increaseAnswerCount();
        return CreateUniversityStudentAnswerResponse.from(answer);
    }

    /**
     * 대학생 답변 수정
     */
    public UpdateUniversityStudentAnswerResponse updateUniversityStudentAnswer(Long universityStudentAnswerId,UpdateUniversityStudentAnswerRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        UniversityStudentAnswer answer = universityStudentAnswerService.getUniversityStudentAnswerById(universityStudentAnswerId);
        if (!answer.getUniversityStudent().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_REQUEST);
        }
        answer.updateAnswer(request.answer());
        return UpdateUniversityStudentAnswerResponse.from(answer);
    }

    /**
     * 대학생 답변 삭제
     */
    public DeleteUniversityStudentAnswerResponse deleteUniversityStudentAnswer(Long universityStudentAnswerId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        UniversityStudentAnswer answer = universityStudentAnswerService.getUniversityStudentAnswerById(universityStudentAnswerId);
        if (!answer.getUniversityStudent().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_REQUEST);
        }
        // 대학생 답변 삭제 후 고등학생 질문의 답변 수 감소
        universityStudentAnswerService.delete(answer);
        answer.getHighSchoolStudentQuestion().decreaseAnswerCount();
        return DeleteUniversityStudentAnswerResponse.from(answer);
    }

    /**
     * 고등학생 질문 좋아요 증가
     */
    public LikeListResponse likeHighSchoolStudentQuestion(Long highSchoolStudentQuestionId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        HighSchoolStudentQuestion question = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);

        if (likeListService.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }

        question.increaseLikeCount();
        LikeList likeList = LikeList.highSchoolStudentQuestionLikeBuilder()
                .member(member)
                .highSchoolStudentQuestion(question)
                .highSchoolStudentQuestionLikeBuild();
        likeListService.save(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 고등학생 질문 좋아요 감소
     */
    public LikeListResponse dislikeHighSchoolStudentQuestion(Long highSchoolStudentQuestionId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        HighSchoolStudentQuestion question = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);

        if (!likeListService.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId)) {
            throw new BusinessException(ErrorCode.ALREADY_UNLIKED);
        }

        question.decreaseLikeCount();
        LikeList likeList = member.getLikeLists().stream()
                .filter(like -> like.getHighSchoolStudentQuestion().getId().equals(highSchoolStudentQuestionId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ALREADY_UNLIKED));
        likeListService.delete(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 대학생 답변 좋아요 증가
     */
    public LikeListResponse likeUniversityStudentAnswer(Long universityStudentAnswerId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        UniversityStudentAnswer answer = universityStudentAnswerService.getUniversityStudentAnswerById(universityStudentAnswerId);

        if (likeListService.existsByMemberIdAndUniversityStudentAnswerId(memberId, universityStudentAnswerId)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }

        answer.increaseLikeCount();
        LikeList likeList = LikeList.universityStudentAnswerLikeBuilder()
                .member(member)
                .universityStudentAnswer(answer)
                .universityStudentAnswerLikeBuild();
        likeListService.save(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 대학생 답변 좋아요 감소
     */
    public LikeListResponse dislikeUniversityStudentAnswer(Long universityStudentAnswerId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        UniversityStudentAnswer answer = universityStudentAnswerService.getUniversityStudentAnswerById(universityStudentAnswerId);

        if (!likeListService.existsByMemberIdAndUniversityStudentAnswerId(memberId, universityStudentAnswerId)) {
            throw new BusinessException(ErrorCode.ALREADY_UNLIKED);
        }

        answer.decreaseLikeCount();
        LikeList likeList = member.getLikeLists().stream()
                .filter(like -> like.getUniversityStudentAnswer().getId().equals(universityStudentAnswerId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ALREADY_UNLIKED));
        likeListService.delete(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 고등학생 질문 스크랩
     */
    public ScrapListResponse scrapHighSchoolStudentQuestion(Long highSchoolStudentQuestionId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        HighSchoolStudentQuestion question = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);

        if (scrapListService.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId)) {
            throw new BusinessException(ErrorCode.ALREADY_SCRAPED);
        }

        ScrapList scrapList = ScrapList.highSchoolStudentQuestionScrapBuilder()
                .member(member)
                .highSchoolStudentQuestion(question)
                .highSchoolStudentQuestionScrapBuild();
        scrapListService.save(scrapList);
        return ScrapListResponse.from(scrapList);
    }

    /**
     * 고등학생 질문 스크랩 취소
     */
    public ScrapListResponse unscrapHighSchoolStudentQuestion(Long highSchoolStudentQuestionId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);

        if (!scrapListService.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId)) {
            throw new BusinessException(ErrorCode.ALREADY_UNSCRAPED);
        }

        ScrapList scrapList = member.getScrapLists().stream()
                .filter(scrap -> scrap.getHighSchoolStudentQuestion().getId().equals(highSchoolStudentQuestionId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ALREADY_UNSCRAPED));
        scrapListService.delete(scrapList);
        return ScrapListResponse.from(scrapList);
    }

    public ReportResponse reportHighSchoolStudentQuestion(Long highSchoolStudentQuestionId, ReportRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member reporter = memberService.getMemberById(memberId);
        HighSchoolStudentQuestion question = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);

        if (reportService.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId)) {
            throw new BusinessException(ErrorCode.ALREADY_REPORTED);
        }

        question.increaseReportCount();
        Report report = Report.questionReportBuilder()
                .reporter(reporter)
                .highSchoolStudentQuestion(question)
                .content(request.content())
                .questionReportBuild();
        reportService.save(report);
        String message = discordMessageGenerator.questionReportMessage(
                reporter.getEmail(),
                highSchoolStudentQuestionId,
                question.getTitle(),
                question.getQuestion(),
                request.content());
        discordMessageGenerator.sendReportMessage(message);
        return ReportResponse.from(report);
    }

    public ReportResponse reportUniversityStudentAnswer(Long universityStudentAnswerId, ReportRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member reporter = memberService.getMemberById(memberId);
        UniversityStudentAnswer answer = universityStudentAnswerService.getUniversityStudentAnswerById(universityStudentAnswerId);

        if (reportService.existsByMemberIdAndUniversityStudentAnswerId(memberId, universityStudentAnswerId)) {
            throw new BusinessException(ErrorCode.ALREADY_REPORTED);
        }

        answer.increaseReportCount();
        Report report = Report.answerReportBuilder()
                .reporter(reporter)
                .universityStudentAnswer(answer)
                .content(request.content())
                .answerReportBuild();
        reportService.save(report);
        String message = discordMessageGenerator.answerReportMessage(
                reporter.getEmail(),
                universityStudentAnswerId,
                answer.getAnswer(),
                request.content());
        discordMessageGenerator.sendReportMessage(message);
        return ReportResponse.from(report);
    }
}
