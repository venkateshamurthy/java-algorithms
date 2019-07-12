package algos.utils;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Retention(SOURCE)
@Target(TYPE_USE)
public @interface DefaultLombok {
  Slf4j log() default @Slf4j;
  Data data() default @Data();
  FieldDefaults defaultFieldAccess() default @FieldDefaults(level=AccessLevel.PRIVATE,makeFinal=true);
  Accessors defaultGetMethodStyle() default @Accessors(fluent=true, chain=true);
  EqualsAndHashCode defaultEqualsAndHashCode() default @EqualsAndHashCode(exclude= {});
}