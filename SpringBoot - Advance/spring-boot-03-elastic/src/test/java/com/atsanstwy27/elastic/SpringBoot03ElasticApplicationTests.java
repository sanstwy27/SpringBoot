package com.atkjs927.elastic;

import com.atkjs927.elastic.bean.Article;
import com.atkjs927.elastic.bean.Book;
import com.atkjs927.elastic.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringBoot03ElasticApplicationTests {

    @Autowired
    RestHighLevelClient restClient;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void test02() {
//        Book book = new Book();
//		book.setId(1);
//		book.setBookName("西遊記");
//		book.setAuthor("吳承恩");
//        bookRepository.save(book);

        for (Book book : bookRepository.findByBookNameLike("游")) {
            System.out.println(book);
        };
    }

    @Test
    void contextLoads() throws IOException {
        Article article = new Article();
        article.setId(1);
        article.setTitle("GoodNews");
        article.setAuthor("abc");
        article.setContent("Hello World");

        //String json = new ObjectMapper().writeValueAsString(article);
        IndexRequest indexRequest = new IndexRequest("atkjs927","elasticTest");
        indexRequest.source(new ObjectMapper().writeValueAsString(article), XContentType.JSON);
        try {
            IndexResponse indexResponse = restClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void search() throws IOException {
        SearchRequest request = new SearchRequest("atkjs927");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("content", "hello"));
        // builder.from(0).size(2); // 分頁
        request.source(builder);

        SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);
        for (SearchHit documentFields : response.getHits()) {
            System.out.printf("result: %s, code: %d, status: %s", documentFields.toString(), response.status().getStatus(), response.status().name());
        }
    }
}
