package uk.co.davidvu.newsaggregator.technews.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.co.davidvu.newsaggregator.technews.dao.NewsArticleRepository;
import uk.co.davidvu.newsaggregator.technews.dao.NewsSourceRepository;
import uk.co.davidvu.newsaggregator.technews.model.Article;
import uk.co.davidvu.newsaggregator.technews.model.NewsApiResponse;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class NewsApiService {

    @Autowired
    @Qualifier("newsApiClient")
    private RestTemplate client;

    @Value("${newsapi.country}")
    private List<String> countries;

    @Autowired
    private NewsArticleRepository articleRepository;

    @Autowired
    private NewsSourceRepository sourceRepository;

    /**
     * public method that task will trigger
     */
    public void getTopHeadlines() {
        countries.parallelStream().forEach(this::getTopCountryHeadlines);
    }

    /**
     * Query newsapi for headlines using RestTemplate.getForObject
     *
     * @param country one of the country e.g. us, gb, de, etc.
     */
    private void getTopCountryHeadlines(String country) {
        log.debug("Getting top headline in " + country);
        NewsApiResponse response = client.getForObject("/top-headlines?country=" + country, NewsApiResponse.class);
        assert response != null;
        // save all articles into database
        log.debug("Saving articles to database");
        this.saveArticle(response.getArticles());
    }

    /**
     * Spawn a thread per each article to save in to database as a transaction
     *
     * @param articles list of articles
     */
    private void saveArticle(List<Article> articles) {
        articles.parallelStream().forEach(article -> new Thread(new SaveArticle(article)).start());
    }

    /**
     * Runnable class to save article in db as a transaction
     */
    private class SaveArticle implements Runnable {
        private Article article;

        SaveArticle(Article article) {
            this.article = article;
        }


        /**
         * Runnable run method that save source and article to the db
         */
        @Override
        @Transactional
        public void run() {
            log.debug("Saving source: " + this.article.getSource().getName());
            if (!sourceRepository.findAllByName(this.article.getSource().getName()).isEmpty()) {
                sourceRepository.save(this.article.getSource());
            }
            log.debug("Saving article: " + this.article.getTitle());
            articleRepository.save(this.article);
        }
    }
}
