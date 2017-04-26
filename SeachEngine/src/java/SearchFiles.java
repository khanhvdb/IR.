/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KhanhVDb
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Vector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/** Simple command-line based search demo. */
public class SearchFiles {

  SearchFiles() {}

  /** Simple command-line based search demo. */
  public static Vector Search(String value) throws IOException, ParseException {
    String usage =
      "Usage:\tjava org.apache.lucene.demo.SearchFiles [-index dir] [-field f] [-repeat n] [-queries file] [-query string] [-raw] [-paging hitsPerPage]\n\nSee http://lucene.apache.org/core/4_1_0/demo/ for details.";

    Vector vListPhim = new Vector();
    String index = "E:\\IR\\index";
    String field = "contents";
    String queries = null;
    int repeat = 0;
    boolean raw = false;
    // regular search
    String queryString = value;
    
    //wildcard query
//    String queryString = "te*t";
    
    //fuzzy query
//    String queryString = "roam~2";
    
   //phrase query 
//    String queryString = "\"apache lucene\"~5";

    //boolean search
//    String queryString = "\"networks\" AND \"protocol\"";


    //boosted search
    //String queryString = "computer^10 crime";
    
    int hitsPerPage = 100;
    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
    IndexSearcher searcher = new IndexSearcher(reader);
    Analyzer analyzer = new StandardAnalyzer();

    BufferedReader in = null;
    QueryParser parser = new QueryParser(field, analyzer);

      Query query = parser.parse(queryString);
      
      System.out.println("Searching for: " + query.toString(field));
      searcher.search(query, 20);
      vListPhim = doSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);    
      reader.close();
      return vListPhim;
  }

  /**
   * This demonstrates a typical paging search scenario, where the search engine presents 
   * pages of size n to the user. The user can then go to the next page if interested in
   * the next hits.
   * 
   * When the query is executed for the first time, then only enough results are collected
   * to fill 5 result pages. If the user wants to page beyond this limit, then the query
   * is executed another time and all hits are collected.
   * 
   */
  public static Vector doSearch(BufferedReader in, IndexSearcher searcher, Query query, 
                                     int hitsPerPage, boolean raw, boolean interactive) throws IOException {
 
    // Collect enough docs to show 5 pages
    Vector vPhim = new Vector();
    TopDocs results = searcher.search(query, 5 * hitsPerPage);
    ScoreDoc[] hits = results.scoreDocs;
    
    int numTotalHits = results.totalHits;
    System.out.println(numTotalHits + " total matching documents");

    int start = 0;
    int end = Math.min(numTotalHits, hitsPerPage);
      
      for (int i = start; i < end; i++) {
        Document doc = searcher.doc(hits[i].doc);
       String path = doc.get("path");
        if (path != null) {
          vPhim.add(path);
          System.out.println((i+1) + ". " + path);
          String title = doc.get("contents");
          if (title != null) {
            System.out.println("   Title: " + doc.get("contents"));
          }
        } else {
          System.out.println((i+1) + ". " + "No path for this document");
        }
                  
      }
      return vPhim;
    }
  }