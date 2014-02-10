package os.rabbit.components;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateComponent {

	String[] value();
}
