package oop.search.presentation;

import oop.search.application.NewsService;
import oop.search.infrastructure.GitHubNewsPublisher;
import oop.search.infrastructure.NaverNewsProvider;

public class GitHubNewsApp {
    private final NewsService newsService;

    public GitHubNewsApp(NewsService newsService) {
        this.newsService = newsService;
    }

    public void run() {
        String keyword = System.getenv("NEWS_QUERY");
        String displayEnv = System.getenv("NEWS_DISPLAY");
        
        if (keyword == null || keyword.trim().isEmpty()) {
            System.err.println("NEWS_QUERY environment variable is not set or empty.");
            return;
        }
        
        int limit = 10; // default value
        if (displayEnv != null && !displayEnv.trim().isEmpty()) {
            try {
                limit = Integer.parseInt(displayEnv.trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid NEWS_DISPLAY environment variable: " + displayEnv + ". Using default limit: 10");
            }
        }
        
        System.out.println("Searching news for keyword: " + keyword + " with limit: " + limit);
        newsService.search(keyword, limit);
        System.out.println("Search and publish completed.");
    }

    public static void main(String[] args) {
        NewsService newsService = new NewsService(
                new NaverNewsProvider(),
                new GitHubNewsPublisher()
        );
        GitHubNewsApp app = new GitHubNewsApp(newsService);
        app.run();
    }
}