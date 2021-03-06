import HttpMethod.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Hol legyen elérhető
@Retention(RetentionPolicy.RUNTIME)
// Mire lehet rátenni
@Target(ElementType.METHOD)
public @interface WebRoute {
    HttpMethod method() default HttpMethod.GET;
    String path() default "";
}
