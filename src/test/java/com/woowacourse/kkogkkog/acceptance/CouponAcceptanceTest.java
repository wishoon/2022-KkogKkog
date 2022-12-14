package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.fixture.CouponStepDefinition.조회_속성별_쿠폰_목록을_조회한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.CouponStepDefinition.쿠폰을_단건_조회한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.CouponStepDefinition.쿠폰을_발급한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.CouponStepDefinition.쿠폰의_상태를_변경한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.MemberStepDefinition.로그인을_한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.QuantityCouponStepDefinition.수량_쿠폰을_발급한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.QuantityCouponStepDefinition.수량_쿠폰의_재고를_감소한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.AcceptanceTest;
import com.woowacourse.kkogkkog.coupon.application.dto.response.CouponsResponse;
import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import java.util.List;
import org.junit.jupiter.api.Test;

@AcceptanceTest
public class CouponAcceptanceTest {

    @Test
    void 회원은_쿠폰을_발급_할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");

        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());
    }

    @Test
    void 회원은_발급한_쿠폰을_조회할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");
        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());

        Long actual = 쿠폰을_단건_조회한다(senderToken, 1L).getCouponId();

        assertThat(actual).isEqualTo(1L);
    }

    @Test
    void 회원은_발급받은_쿠폰을_조회할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");

        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());

        Long actual = 쿠폰을_단건_조회한다(receiverToken, 1L).getCouponId();
        assertThat(actual).isEqualTo(1L);
    }

    @Test
    void 수신자_회원은_수신한_쿠폰의_상태가_READY_일_때_쿠폰을_사용완료로_변경할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");
        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());

        쿠폰의_상태를_변경한다(receiverToken, 1L, Condition.FINISH.getValue());

        assertThat(쿠폰을_단건_조회한다(receiverToken, 1L).getCondition()).isEqualTo(Condition.FINISH.getValue());
    }

    @Test
    void 발신자_회원은_수신한_쿠폰의_상태가_READY_일_때_쿠폰을_사용완료로_변경할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");
        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());

        쿠폰의_상태를_변경한다(senderToken, 1L, Condition.FINISH.getValue());

        assertThat(쿠폰을_단건_조회한다(senderToken, 1L).getCondition()).isEqualTo(Condition.FINISH.getValue());
    }

    @Test
    void 수신자가_수량_쿠폰을_받을_경우_수량쿠폰의_재고가_감소하며_수신자에게_쿠폰이_생성된다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");
        Long quantityCouponId = 수량_쿠폰을_발급한다(senderToken, "수량 쿠폰의 내용", "00 이벤트 쿠폰", 10);

        수량_쿠폰의_재고를_감소한다(receiverToken, quantityCouponId);

        assertThat(쿠폰을_단건_조회한다(senderToken, 1L).getCouponId()).isEqualTo(1L);
    }

    @Test
    void 발신자_회원은_발급_쿠폰_조회_타입을_통해_발급한_쿠폰_목록을_조회할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");
        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());

        CouponsResponse actual = 조회_속성별_쿠폰_목록을_조회한다(senderToken, "sender");

        assertThat(actual.getData()).hasSize(1);
    }
}
