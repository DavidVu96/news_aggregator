package uk.co.davidvu.newsaggregator.technews.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.co.davidvu.newsaggregator.technews.service.NewsApiService;
import uk.co.davidvu.newsaggregator.technews.utils.Constant;

import java.util.List;

@Component
public class NewsAPIScheduler {

    @Autowired
    private NewsApiService newsApiService;

    private List<String> countries = Constant.countries;

    /**
     * private scheduler that task will trigger every hour, update sources then update newest news from US and UK
     */
    @Scheduled(cron = "0 0 * * * *")
    private void getTopHeadlines() {
        newsApiService.updateSources();
        countries.parallelStream().forEach(country -> newsApiService.getTopCountryHeadlines(country));
    }
}
