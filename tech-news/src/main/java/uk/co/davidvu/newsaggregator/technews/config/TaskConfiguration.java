package uk.co.davidvu.newsaggregator.technews.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.davidvu.newsaggregator.technews.service.NewsApiService;

@Configuration
@EnableTask
public class TaskConfiguration {

    @Autowired
    private NewsApiService service;

    @Bean
    public NewsApiRunner newsApiRunner(){
        return new NewsApiRunner(service);
    }

    public static class NewsApiRunner implements ApplicationRunner {

        private NewsApiService service;

        NewsApiRunner(NewsApiService service){
            this.service = service;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            service.getTopHeadlines();
        }
    }
}
