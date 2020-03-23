package uk.co.davidvu.newsaggregator.technews.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.co.davidvu.newsaggregator.technews.dao.ArticleRepository;
import uk.co.davidvu.newsaggregator.technews.dao.SourceRepository;
import uk.co.davidvu.newsaggregator.technews.model.Article;
import uk.co.davidvu.newsaggregator.technews.model.NewsApiArticleResponse;
import uk.co.davidvu.newsaggregator.technews.model.NewsApiSourceResponse;
import uk.co.davidvu.newsaggregator.technews.utils.Constant;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@PropertySource("classpath:application.yml")
public class NewsApiService {

    @Autowired
    @Qualifier("newsApiClient")
    private RestTemplate client;


    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SourceRepository sourceRepository;


    public void updateSources(){
        log.debug("Updating latest sources");
        NewsApiSourceResponse response = client.getForObject("/sources", NewsApiSourceResponse.class);
        log.debug("Updating sources");
        assert response != null;
        response.getSources().parallelStream().forEach(source -> {
            try{
                log.debug("Saving source " + source.getName());
                sourceRepository.save(source);
            } catch (DataIntegrityViolationException exception){
                log.error("cant save source " + source.getName());
            }
        });
    }

    /**
     * Query newsapi for headlines using RestTemplate.getForObject
     *
     * @param country one of the country e.g. us, gb, de, etc.
     */
    public void getTopCountryHeadlines(String country) {
        log.debug("Getting top headline in " + country);
        NewsApiArticleResponse response = client.getForObject("/top-headlines?country=" + country, NewsApiArticleResponse.class);
        assert response != null;
        // save all articles into database
        log.debug("Saving articles to database");
        saveArticle(response.getArticles());
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
            log.debug("Finding source: " + this.article.getSource().getName());
            this.article.setSource(sourceRepository.findByName(this.article.getSource().getName()));
            log.debug("Saving article: " + this.article.getTitle());
            articleRepository.save(this.article);
        }
    }
}
