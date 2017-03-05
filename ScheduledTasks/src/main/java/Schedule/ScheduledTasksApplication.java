package Schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduledTasksApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ScheduledTasksApplication.class);
    }
}