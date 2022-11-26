package com.woowacourse.kkogkkog.coupon.application.dto.response;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponResponse {

    private Long couponId;
    private CouponMemberResponse sender;
    private CouponMemberResponse receiver;
    private String content;
    private String category;
    private String condition;
    private LocalDateTime createdTime;

    public static CouponResponse createResponse(final Coupon coupon, final Member sender, final Member receiver) {
        return new CouponResponse(
            coupon.getId(),
            new CouponMemberResponse(sender.getId(), sender.getUsername(), sender.getImageUrl()),
            new CouponMemberResponse(receiver.getId(), receiver.getUsername(), receiver.getImageUrl()),
            coupon.getContent(),
            coupon.getCategory().getValue(),
            coupon.getCondition().getValue(),
            coupon.getCreatedTime());
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    private static class CouponMemberResponse {

        private Long memberId;
        private String username;
        private String imageUrl;
    }
}