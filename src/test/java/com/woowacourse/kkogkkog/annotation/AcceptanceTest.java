package com.woowacourse.kkogkkog.annotation;

import com.woowacourse.kkogkkog.extension.DataCleanerExtension;
import com.woowacourse.kkogkkog.extension.RandomPortExtension;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Retention(RetentionPolicy.RUNTIME)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(RandomPortExtension.class)
@ExtendWith(DataCleanerExtension.class)
public @interface AcceptanceTest {

}
